
#include <iostream>
#include <cmath>
#include <algorithm>
#include <fstream>
#include <mpi.h>

using namespace std;

/*
 P = 1, N = 5e2, EPS = 1e-4, T = 10.0813
 P = 2, N = 5e2, EPS = 1e-4, T = 5.04953
 P = 4, N = 5e2, EPS = 1e-4, T = 2.90243
 
 P = 1, N = 1e3, EPS = 1e-4, T = 45.204
 P = 2, N = 1e3, EPS = 1e-4, T = 22.9335
 P = 4, N = 1e3, EPS = 1e-4, T = 12.6857
 
 P = 4, N = 1e3, EPS = 1e-5, T = 100.722
 */

const int MYTAG = 99;

const double EPS = 1e-9;
const int N = 7e1;
const double h = 1.0 / (N + 1);

double f1(double y) { return -y * y; }
double f2(double y) { return 1 - y * y; }
double f3(double x) { return x * x; }
double f4(double x) { return x * x - 1; }
double F(double x, double y) { return 0; }

    // u(x, y) = x*x - y*y


int main() {
    MPI_Init(NULL, NULL);
    int threads;
    MPI_Comm_size(MPI_COMM_WORLD, &threads);
    int rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    
    double progStart = MPI_Wtime();
    int len = N / threads;
    int blockStart = rank * len;
    if (rank == threads - 1) {
        len += N % threads;
    }
    
    double** u = new double*[len + 2];
    for (int i = 0; i < len + 2; i++) {
        u[i] = new double[N + 2];
    }
    for (int i = 1; i <= len; i++) {
        for (int j = 1; j <= N; j++) {
            u[i][j] = 0;
        }
    }
    for (int i = 1; i <= len; i++) {
        u[i][0] = f3((i + blockStart) * h);
        u[i][N + 1] = f4((i + blockStart) * h);
    }
    if (rank == 0) {
        for (int i = 0; i <= N + 1; i++) {
            u[0][i] = f1(i * h);
        }
    }
    if (rank == threads - 1) {
        for (int i = 0; i <= N + 1; i++) {
            u[len + 1][i] = f2(i * h);
        }
    }
    
    double maxDelta;
    int iters = 0;
    do {
        MPI_Sendrecv(u[len], N + 2, MPI_DOUBLE, rank + 1 < threads ? rank + 1 : MPI_PROC_NULL, MYTAG, u[0], N + 2, MPI_DOUBLE, rank - 1 >= 0 ? rank - 1 : MPI_PROC_NULL, MYTAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Sendrecv(u[1], N + 2, MPI_DOUBLE, rank - 1 >= 0 ? rank - 1 : MPI_PROC_NULL, MYTAG, u[len + 1], N + 2, MPI_DOUBLE, rank + 1 < threads ? rank + 1 : MPI_PROC_NULL, MYTAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        
        maxDelta = -1;
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= N; j++) {
                double val = u[i][j];
                u[i][j] = 0.25 * (u[i - 1][j] + u[i][j - 1] + u[i + 1][j] + u[i][j + 1] - h * h * F((i + blockStart) * h, j * h));
                maxDelta = max(maxDelta, fabs(val - u[i][j]));
            }
        }
        MPI_Allreduce(&maxDelta, &maxDelta, 1, MPI_DOUBLE, MPI_MAX, MPI_COMM_WORLD);
        iters++;
    } while (maxDelta > EPS);
    
    if (rank == 0) {
        cout << "Time = " << MPI_Wtime() - progStart << endl;
        cerr << iters << ' ' << maxDelta << endl;
    }
    
    if (rank == 0) {
        ofstream fout("output.txt");
        for (int j = 0; j <= N + 1; ++j) {
            fout << 0 << ' ' << j * h << ' ' << u[0][j] << endl;
        }
        fout.close();
    }
    for (int r = 0; r < threads; ++r) {
        if (rank == r) {
            ofstream fout("output.txt", std::ofstream::app);
            for (int i = 1; i <= len; i++) {
                for (int j = 0; j <= N + 1; ++j) {
                    fout << (i + blockStart) * h << ' ' << j * h << ' ' << u[i][j] << endl;
                }
            }
            fout.close();
        }
        MPI_Barrier(MPI_COMM_WORLD);
    }
    if (rank == threads - 1) {
        ofstream fout("output.txt", std::ofstream::app);
        for (int j = 0; j <= N + 1; ++j) {
            fout << 1 << ' ' << j * h << ' ' << u[len + 1][j] << endl;
        }
        fout.close();
    }
    
    MPI_Finalize();
    
    return 0;
}
