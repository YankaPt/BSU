#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"
#include <sys/timeb.h>
#include <string.h>


double realtime();

long* merge(const long* left_arr, const long* right_arr, long left_n,long right_n){
    long* out = (long*)malloc((left_n+right_n)* sizeof(long));
    long i,j,k;
    i = j = k = 0;
    while(i < left_n && j < right_n){
        if(left_arr[i] < right_arr[j]){
            out[k++] = left_arr[i++];
        }else {
            out[k++] = right_arr[j++];
        }
    }
    while (i < left_n) {
        out[k++] = left_arr[i++];
    }
    while (j < right_n) {
        out[k++] = right_arr[j++];
    }
    return out;
}



int compare_ints(const void *p, const void *q) {
    long x = *(const long *)p;
    long y = *(const long *)q;

    if (x < y)
        return -1;
    else if (x > y)
        return 1;
    return 0;
}
int main(int argc, char ** argv)
{
    int myrank, ranksize;
    double sendtime[2],recvtime[2];
    int i;
    MPI_Status status;
    double t1,t2;
    double t3, t4, t5, t6,t7;
    long current_n;

    t1=realtime();
    MPI_Init (&argc, &argv);
    t2=realtime();
    MPI_Comm_rank (MPI_COMM_WORLD, &myrank);
    MPI_Comm_size (MPI_COMM_WORLD, &ranksize);
    long local_n;
    long* initial_arr = NULL;
    long *sort_arr = NULL;
    if (myrank == 0)
    {
        char* filename = argv[1];
        long n = atol(argv[2]);
        initial_arr = (long*)malloc(n* sizeof(long));
        sort_arr = (long*)malloc(n* sizeof(long));
        printf("filename %s, n %li\n",filename,n);
        printf("Parallel merge sort\n");
        local_n  = n/ranksize;
        if (local_n*ranksize != n){
            long new_size = (local_n+1) * ranksize;
            local_n++;
            long* new_arr = (long*)(new_size * sizeof(long));
            long diff = new_size - n;
            memset(new_arr,-1,diff*sizeof(long));
            memcpy(new_arr+diff,initial_arr, n*sizeof(long));
            initial_arr = new_arr;
            n = new_size;
        }
        printf("\n\nNew n = %li\nlocal_n = %li\n",n,local_n);
        FILE* inf;
        inf = fopen(filename, "r+");
        for(int j = 0;j < n;j++ ){
            fscanf(inf,"%li",&initial_arr[j]);
        }

    }

    long* current_arr;
    MPI_Barrier (MPI_COMM_WORLD);
    if (myrank == 0)
    {
        t3 = MPI_Wtime();
        for (i = 1; i < ranksize; i++)
        {
            MPI_Send (&local_n, 1, MPI_LONG, i, 98, MPI_COMM_WORLD);
        }
    }
    else{
        MPI_Recv (&local_n, 1, MPI_LONG, 0, 98, MPI_COMM_WORLD, &status);
    }
    current_arr = (long*)malloc(local_n* sizeof(long));

    MPI_Scatter(initial_arr,local_n,MPI_LONG,current_arr,local_n,MPI_LONG,0,MPI_COMM_WORLD);

    t4 = MPI_Wtime();
    qsort(current_arr,local_n, sizeof(long),&compare_ints);
    t5 = MPI_Wtime();
    MPI_Barrier (MPI_COMM_WORLD);
    t6 = MPI_Wtime();

    MPI_Gather(current_arr,local_n,MPI_LONG,sort_arr,local_n,MPI_LONG,0,MPI_COMM_WORLD);

    if (myrank == 0)
    {
        current_n = local_n;
        long* merge_arr = (long*)malloc(current_n* sizeof(long));
        memcpy(merge_arr,sort_arr,current_n* sizeof(long));
        for (i = 1; i < ranksize; i++)
        {
            long* temp;
            temp = merge(merge_arr,(sort_arr+current_n),current_n,local_n);
            merge_arr = temp;
            current_n += local_n;
            printf("merge of %d\n",i);
        }
        printf("end merge\n");
        t7 = MPI_Wtime();
        printf ("Master: Has collected sum from MPI-Processes \n");
        printf ("%ld tasks used - Execution time: %.3lf sec\n",ranksize, t7 -t3);
        printf("\nStatistics:\n");
        printf("Master: startup: %.0lf msec\n",t2-t1);
        printf("Master: time to send # of intervals:%.3lf sec\n",t4-t3);
        printf("Master: waiting time for sincro after calculation:%.2lf sec\n",t6-t5);
        printf("Master: time to collect: %.3lf sec\n",t7-t6);
        printf("Master: calculation time:%.3lf sec\n",t5-t4);

        MPI_Barrier (MPI_COMM_WORLD);
        for (i = 1; i < ranksize; i++){
            MPI_Recv (recvtime, 2, MPI_DOUBLE, i, 100, MPI_COMM_WORLD, &status);
            printf("process %d: calculation time: %.3lf sec\twaiting time for sincro.: %.3lf sec\n",i,recvtime[0],recvtime[1]);
        }

        FILE* of;
        printf("current_n = %li",current_n);
        of = fopen("output.txt","w");
        for(i = 0;i < current_n;i++){
            fprintf(of,"%li ", merge_arr[i]);
        }
    }
    else{
        MPI_Barrier (MPI_COMM_WORLD);
        sendtime[0]=t5-t4;
        sendtime[1]=t6-t5;
        MPI_Send (sendtime, 2, MPI_DOUBLE, 0, 100, MPI_COMM_WORLD);

    }
    MPI_Finalize ();
}
double realtime()
{
    struct timeb tp;
    ftime(&tp);
    return((double)(tp.time)*1000+(double)(tp.millitm));
}
