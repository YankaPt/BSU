/*
import java.io.*;
import java.util.StringTokenizer;
class Node{

    int size;
    private int capacity;
    int[] nodes;
    int[] weights;
    Node()
    {
        size = 0;
        nodes = new int[5];
        weights = new int[5];
        capacity = 5;
    }
    void addNeighbor(int j, int w)
    {
        if (size>=capacity)
        enlarge();
        nodes[size] = j;
        weights[size] = w;
        size++;

    }
    private void enlarge() {
        int newNodes[] = new int[capacity*2];
        int newWeights[] = new int[capacity*2];
        for (int i=0;i<capacity;i++)
        {
            newNodes[i] = nodes[i];
            newWeights[i] = weights[i];
        }

        capacity*=2;
        nodes = newNodes;
        weights = newWeights;
    }
}
class HeapElement {
    int node;
    long d;
    HeapElement(int node, long d) {
        this.node = node;
        this.d = d;
    }

}
class BinaryHeap {
    HeapElement a[];
    int last;
    BinaryHeap(int n) {
        a = new HeapElement[n];
        last = 0;
    }
    private void swap(int x,int y)
    {
        HeapElement temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }
    void add(HeapElement x) {
        a[last] = x;
        int i = last;
        while (i!=0)
        {
            int j = (i-1)/2;
            if (a[i].d < a[j].d)
            {
                swap(i,j);
                i=j;
            }
            else
                break;
        }
        last++;
    }
    HeapElement poll() {
        if (isEmpty())
            return new HeapElement(0,0);
        if (last == 1)
        {
            last = 0;
            return a[0];

        }
        swap(0, last -1);
        last--;
        int i=0;
        while (2*i+1 < last)
        {
            int j;
            if (2*i+2 < last)
            {
                if (a[2*i+1].d < a[2*i+2].d)
                    j = 2*i+1;
                else
                    j = 2*i+2;
            }
            else
                j = 2*i+1;
            if (a[i].d > a[j].d)
            {
                swap(i,j);
                i=j;
            }
            else
                break;
        }
        return a[last];
    }
    boolean isEmpty() {
        return last ==0;
    }

}

public class TaskLen implements Runnable {
    static int n;
    static int m;
    static Node array[];
    static boolean blocked[];
    static BinaryHeap heap;
    static long result[];

    static void toFile(String fileName,long rez) throws IOException
    {
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.println(String.valueOf(rez));

        printWriter.close();

    }
    public void run()
    {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
            String line = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line," ");
            n = Integer.parseInt(stringTokenizer.nextToken());
            m = Integer.parseInt(stringTokenizer.nextToken());
            bufferedReader.readLine();
            if (n==1)
            {
                toFile("output.txt",0);
                return;
            }
            array = new Node[n];
            for (int i=0;i<n;i++)
            {
                array[i] = new Node();
            }
            int i , j, w;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer stringTokenizer1 = new StringTokenizer(line," ");
                i = Integer.parseInt(stringTokenizer1.nextToken())-1;
                j = Integer.parseInt(stringTokenizer1.nextToken())-1;
                w = Integer.parseInt(stringTokenizer1.nextToken());
                array[i].addNeighbor(j,w);
                array[j].addNeighbor(i,w);

            }

            blocked = new boolean[n];
            for (int k=0;k<n;k++)
                blocked[k] = false;
            heap = new BinaryHeap(m);
            heap.add(new HeapElement(0,0));
            HeapElement temp;
            result = new long[n];
            while (!heap.isEmpty())
            {
                temp = heap.poll();
                int ind =temp.node;

                if (!blocked[ind])
                {
                    result[ind] = temp.d;
                    blocked[ind] = true;
                    for (int k=0; k < array[ind].size; k++) {
                        if (!blocked[array[ind].nodes[k]]) {
                            heap.add(new HeapElement(array[ind].nodes[k],array[ind].weights[k]+temp.d));
                        }
                    }
                }

            }
            toFile("output.txt", result[n-1]);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static void main(String[] args) {
        new Thread(null,new TaskLen(),"",64*1024*1024).start();
    }
}
*/
