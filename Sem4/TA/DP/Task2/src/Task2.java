import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class Task2 implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Task2(), "", 64 * 1024 * 1024).start();
    }

    public void run() {
        int n=0, l=0, k=0, q=0;
        int m=0;
        int tempV1, tempV2, tempV3;
        Node[] a = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.in"));
            String[] line = bufferedReader.readLine().split(" ");
            n = Integer.parseInt(line[0]);
            l = Integer.parseInt(line[1])-1;
            k = Integer.parseInt(line[2])-1;
            q = Integer.parseInt(line[3])-1;
            a = new Node[n];
            for (int i=0; i<n; i++) {
                a[i] = new Node();
            }

            line = bufferedReader.readLine().split(" ");
            for (int i = 0; i<n; i++) {
                a[i].color = Integer.parseInt(line[i]);
            }

            m = Integer.parseInt(bufferedReader.readLine());
            for (int i=0; i<m; i++) {
                line = bufferedReader.readLine().split(" ");
                tempV1 = Integer.parseInt(line[0])-1;
                tempV2 = Integer.parseInt(line[1])-1;
                tempV3 = Integer.parseInt(line[2]);
                a[tempV1].addNeighbor(tempV2, tempV3);
            }
        } catch(Exception ex) {}
        int result = getSolution(n, l, k, q, a);
        try {
            PrintWriter printWriter = new PrintWriter(new File("output.out"));
            if (result!=Integer.MAX_VALUE) {
                printWriter.println("Yes");
                printWriter.print(result+"");
            } else
                printWriter.print("No");
            printWriter.close();
        } catch(Exception ex) {}
    }
    static int getSolution(int n, int l, int k, int q, Node[] a) {
        int[][] r = new int[n][n];
        r[l][k] = -1;
        Queue<MyElement> queue = new ArrayDeque<>();
        queue.add(new MyElement(l, k, 0));
        MyElement currentElement;
        while (!queue.isEmpty()) {
            currentElement = queue.poll();
            for(int i=0; i<a[currentElement.x].size; i++) {
                if ((r[a[currentElement.x].nodes[i]][currentElement.y]==0) && (a[currentElement.x].colors[i]==a[currentElement.y].color)&&(a[currentElement.x].nodes[i] != currentElement.y)) {
                    queue.add(new MyElement(a[currentElement.x].nodes[i], currentElement.y, currentElement.value+1));
                    r[a[currentElement.x].nodes[i]][currentElement.y] = currentElement.value + 1;
                }
            }
            for(int i=0; i<a[currentElement.y].size; i++) {
                if ((r[currentElement.x][a[currentElement.y].nodes[i]]==0) && (a[currentElement.y].colors[i]==a[currentElement.x].color)&&(currentElement.x != a[currentElement.y].nodes[i])) {
                    queue.add(new MyElement(currentElement.x, a[currentElement.y].nodes[i], currentElement.value+1));
                    r[currentElement.x][a[currentElement.y].nodes[i]] = currentElement.value + 1;
                }
            }
        }
        ////////////////////////i->j AND j<-i DOIT!
        int result = Integer.MAX_VALUE;
        for (int i=0; i<n; i++) {
            if (r[i][q]!=0 && r[i][q]<result)
                result = r[i][q];
        }
        for (int i=0; i<n; i++) {
            if (r[q][i]!=0 && r[q][i]<result)
                result = r[q][i];
        }
        return result;
    }

}
class MyElement {
    int x;
    int y;
    int value;

    MyElement(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
class Node{
    int size;
    private int capacity;
    int color;
    int[] nodes;
    int[] colors;

    Node() {
        size = 0;
        nodes = new int[5];
        colors = new int[5];
        capacity = 5;
    }

    void addNeighbor(int j, int c) {
        if (size>=capacity)
            enlarge();
        nodes[size] = j;
        colors[size] = c;
        size++;

    }
    private void enlarge() {
        int newNodes[] = new int[capacity*2];
        int newWeights[] = new int[capacity*2];
        for (int i=0;i<capacity;i++) {
            newNodes[i] = nodes[i];
            newWeights[i] = colors[i];
        }
        capacity*=2;
        nodes = newNodes;
        colors = newWeights;
    }
}
