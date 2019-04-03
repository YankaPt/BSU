#include <mpi.h>
#include <stdio.h>
#include <fstream>
#include <limits>


void skip_lines(std::ifstream& fin, int n) {
    for (int p = 0; p < n; p++)
        fin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
}


int main(int argc, char** argv)
{
    int size, rank;
    double **matrix, *vector;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    char* filename = argv[1];
    int n = atoi(argv[2]);
    std::ifstream fin(filename);


    if (n % size != 0) {
        if(rank == 0)
            printf("Error: N = %d must be divisible by number of processes (%d)\n", n, size);
        MPI_Barrier(MPI_COMM_WORLD);
        MPI_Finalize();
        return -1;
    }

    matrix = new double*[n/size];
    vector = new double[n << 1];
    for (int i = 0; i < n/size; i++) {
        matrix[i] = new double[n << 1];
    }

    skip_lines(fin, rank);

    for (int i = 0; i < n/size; i++) {
        for (int j = 0; j < n+n; j++) {
            if (j < n)
                fin >> matrix[i][j];
            else
                matrix[i][j] = (j-n == rank + i*size) ? 1 : 0;
        }
        if (i != n-1)
            skip_lines(fin, size);
    }

    MPI_Barrier(MPI_COMM_WORLD);

    double time = MPI_Wtime();

    for (int k = 0; k < n/size; k++) {
        for (int p = 0; p < size; p++) {
            if(rank == p) {
                double MAD = 1.0 / matrix[k][size*k+p];
                for (int j = (n << 1) -1; j >= size*k+p; j--)
                    matrix[k][j] *= MAD;

                MPI_Bcast(matrix[k], (n << 1), MPI_DOUBLE, p, MPI_COMM_WORLD);

                for (int i = k+1; i < n/size; i++) {
                    for (int j = (n << 1)-1; j >= size*k+p; j--)
                        matrix[i][j] -= matrix[i][size*k+p]*matrix[k][j];
                }
            }
            else {
                MPI_Bcast(vector, (n << 1), MPI_DOUBLE, p, MPI_COMM_WORLD);

                for (int i = k+(rank<p)?1:0; i < n/size; i++) {
                    for (int j = (n << 1)-1; j >= size*k+p; j--)
                        matrix[i][j] -= matrix[i][size*k+p]*vector[j];
                }
            }
        }
    }
    MPI_Barrier(MPI_COMM_WORLD);
    for (int k = n/size-1; k >= 0; k--) {
        for (int p = size-1; p >= 0; p--) {
            if(rank == p) {
                MPI_Bcast(matrix[k], (n << 1), MPI_DOUBLE, p, MPI_COMM_WORLD);
                for (int i = k-1; i >= 0; i--)
                    for (int j = (n << 1)-1; j >= size*k+p; j--)
                        matrix[i][j] -= matrix[i][size*k+p]*matrix[k][j];
            }
            else {
                MPI_Bcast(vector, (n << 1), MPI_DOUBLE, p, MPI_COMM_WORLD);
                for (int i = k+(rank<p)?0:-1; i >= 0; i--)
                    for (int j = (n << 1)-1; j >= size*k+p; j--)
                        matrix[i][j] -= matrix[i][size*k+p]*vector[j];
            }
        }
    }

    MPI_Barrier(MPI_COMM_WORLD);
    double time_end = MPI_Wtime();
    std::ofstream fout;
    if (rank == 0) {
        printf("Calculation time: %.3lf sec\n", time_end-time);
        fout = std::ofstream("output.txt");
    }

    MPI_Status status;
    for (int k = 0; k < n/size; k++)
        for (int p = 0; p < size; p++) {
            if (rank == p && p != 0) {
                MPI_Send(matrix[k]+n,n,MPI_DOUBLE,0,42,MPI_COMM_WORLD);
            }
            if (rank == 0 && p == 0){
                for (int i = n; i < (n << 1); i++)
                    fout << matrix[k][i] << " ";
                fout << '\n';
            }
            if (rank != p && rank == 0){
                auto *vv = new double[n];
                MPI_Recv(vv,n,MPI_DOUBLE,p,42,MPI_COMM_WORLD,&status);
                for (int i = 0; i < n; i++)
                    fout << vv[i] << " ";
                fout << '\n';
            }
            MPI_Barrier(MPI_COMM_WORLD);
        }

    if (rank == 0) fout.close();
    MPI_Finalize();
}