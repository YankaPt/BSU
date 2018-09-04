import java.io.*;

public class Task71 {
    public static void main(String[] args) {
        int n=-1;
        int m=-1;
        int tempV1, tempV2, tempV3;
        Node[] a = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
            String[] line = bufferedReader.readLine().split(" ");
            n = Integer.parseInt(line[0]);
            m = Integer.parseInt(line[1]);
            a = new Node[n];
            for (int i=0; i<n; i++) {
                a[i] = new Node();
            }


            for (int i=0; i<m; i++) {
                line = bufferedReader.readLine().split(" ");
                tempV1 = Integer.parseInt(line[0])-1;
                tempV2 = Integer.parseInt(line[1])-1;
                tempV3 = Integer.parseInt(line[2]);
                if (tempV1!=tempV2) {
                    a[tempV1].addNeighbor(tempV2, tempV3);
                    a[tempV2].addNeighbor(tempV1, tempV3);
                    //УРААААААААААААААААААААААААА
                }

            }
        } catch(Exception ex) {}
        try {
            PrintWriter printWriter = new PrintWriter(new File("output.txt"));
            printWriter.print(dejkstra(n, m, a)+"");
            printWriter.close();
        } catch(Exception ex) {ex.printStackTrace();}
    }

    static long dejkstra(int n, int m, Node[] a) {
        if (n==1)
            return 0;
        long[] result = new long[n];
        BinaryHeap heap = new BinaryHeap(m);
        boolean[] blocked;
        blocked = new boolean[n];
        for (int i=0; i<n; i++)
            blocked[i] = false;
        heap.add(new HeapElement(0,0));
        HeapElement currentElement;
        while (!heap.isEmpty()) {
            currentElement = heap.poll();
            int currentNode = currentElement.node;

            if (!blocked[currentNode]) {
                result[currentNode] = currentElement.d;
                blocked[currentNode] = true;
                for (int i=0; i < a[currentNode].size; i++) {
                    if (!blocked[a[currentNode].nodes[i]]) {
                        heap.add(new HeapElement(a[currentNode].nodes[i],a[currentNode].weights[i]+currentElement.d));
                    }
                }
            }

        }
        return result[n-1];
    }
}
class Node{
    int size;
    private int capacity;
    int[] nodes;
    int[] weights;

    Node() {
        size = 0;
        nodes = new int[5];
        weights = new int[5];
        capacity = 5;
    }

    void addNeighbor(int j, int w) {
        if (size>=capacity)
            enlarge();
        nodes[size] = j;
        weights[size] = w;
        size++;

    }
    private void enlarge() {
        int newNodes[] = new int[capacity*2];
        int newWeights[] = new int[capacity*2];
        for (int i=0;i<capacity;i++) {
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
    private HeapElement a[];
    private int last;
    BinaryHeap(int n) {
        a = new HeapElement[n];
        last = 0;
    }
    private void swap(int x,int y) {
        HeapElement t = a[x];
        a[x] = a[y];
        a[y] = t;
    }
    void add(HeapElement x) {
        a[last] = x;
        int i = last;
        while (i!=0) {
            int j = (i-1)/2;
            if (a[i].d < a[j].d) {
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
        if (last == 1) {
            last = 0;
            return a[0];
        }
        swap(0, last -1);
        last--;
        int i=0;
        while (2*i+1 < last) {
            int j;
            if (2*i+2 < last) {
                if (a[2*i+1].d < a[2*i+2].d)
                    j = 2*i+1;
                else
                    j = 2*i+2;
            }
            else
                j = 2*i+1;
            if (a[i].d > a[j].d) {
                swap(i,j);
                i=j;
            }
            else
                break;
        }
        return a[last];
    }
    boolean isEmpty() {
        return last == 0;
    }
}