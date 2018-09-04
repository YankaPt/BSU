import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class Task3 {
    public static void main(String[] args) {
        int n=0, m=0, i0=0, j0=0;
        long v=0;
        String[] form = null;
        try {
            FileReader fileReader = new FileReader("in.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] line = bufferedReader.readLine().split(" ");
            m = Integer.parseInt(line[0]);
            n = Integer.parseInt(line[1]);
            line = bufferedReader.readLine().split(" ");
            form = new String[m];
            i0 = Integer.parseInt(line[0]);
            j0 = Integer.parseInt(line[1]);
            v = Integer.parseInt(line[2]);
            for (int i=0; i<m;i++)
                form[i] = bufferedReader.readLine();
        } catch(Exception ex) {ex.printStackTrace();}
        try {
            FileWriter fileWriter = new FileWriter("out.txt");
            fileWriter.write(getWaterResidue(m, n, i0, j0, v, form) + "");
            fileWriter.close();
        } catch(Exception ex) {ex.printStackTrace();}
    }


    public static long getWaterResidue(int m, int n, int i0, int j0, long v, String[] form) {
        i0--;
        j0--;
        int maxHeight = 0;
        int currentHeight;
        String[] row;
        MyElement[][] elements = new MyElement[m][n];
        PriorityQueue<MyElement> heap = new PriorityQueue<>();

        for (int i = 0; i < m; i++) {
            row = form[i].split(" ");
            for (int j = 0; j < n; j++) {
                currentHeight = Integer.parseInt(row[j]);
                elements[i][j] = new MyElement(currentHeight, currentHeight, i, j);
                if (maxHeight < currentHeight)
                    maxHeight = currentHeight;
                if (i == 0 ^ i == m-1 ^ j == 0 ^ j == n-1)
                    heap.add(elements[i][j]);
            }
        }
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                elements[i][j].waterline = maxHeight;
            }
        }
        MyElement currentElement;
        MyElement adjacentElement=null;
        MyElement adjacentElement1=null, adjacentElement2=null, adjacentElement3=null;
        while (!heap.isEmpty()) {
            currentElement = heap.poll();
            //System.out.println(currentElement.x + " " + currentElement.y+ " " + currentElement.height + " " + currentElement.waterline);
            if (!currentElement.isBlocked) {
                if (currentElement.y == 0 || currentElement.y == n-1 || currentElement.x == 0 || currentElement.x == m-1) {
                    if (currentElement.x == 0)
                        adjacentElement = elements[currentElement.x+1][currentElement.y];
                    if (currentElement.x == m-1)
                        adjacentElement = elements[currentElement.x-1][currentElement.y];
                    if (currentElement.y == 0)
                        adjacentElement = elements[currentElement.x][currentElement.y+1];
                    if (currentElement.y == n-1)
                        adjacentElement = elements[currentElement.x][currentElement.y-1];

                    if (adjacentElement.waterline > currentElement.waterline) {
                        adjacentElement.waterline = Math.max(currentElement.waterline, adjacentElement.height);
                        heap.add(adjacentElement);
                    }
                } else {
                    adjacentElement = elements[currentElement.x+1][currentElement.y];
                    adjacentElement1 = elements[currentElement.x-1][currentElement.y];
                    adjacentElement2 = elements[currentElement.x][currentElement.y+1];
                    adjacentElement3 = elements[currentElement.x][currentElement.y-1];
                    if (!(adjacentElement.y == 0 || adjacentElement.y == n-1 || adjacentElement.x == 0 || adjacentElement.x == m-1)&&adjacentElement.waterline>currentElement.waterline) {
                        adjacentElement.waterline = Math.max(currentElement.waterline, adjacentElement.height);
                        heap.add(adjacentElement);
                    }
                    if (!(adjacentElement1.y == 0 || adjacentElement1.y == n-1 || adjacentElement1.x == 0 || adjacentElement1.x == m-1)&&adjacentElement1.waterline>currentElement.waterline) {
                        adjacentElement1.waterline = Math.max(currentElement.waterline, adjacentElement1.height);
                        heap.add(adjacentElement1);
                    }
                    if (!(adjacentElement2.y == 0 || adjacentElement2.y == n-1 || adjacentElement2.x == 0 || adjacentElement2.x == m-1)&&adjacentElement2.waterline>currentElement.waterline) {
                        adjacentElement2.waterline = Math.max(currentElement.waterline, adjacentElement2.height);
                        heap.add(adjacentElement2);
                    }
                    if (!(adjacentElement3.y == 0 || adjacentElement3.y == n-1 || adjacentElement3.x == 0 || adjacentElement3.x == m-1)&&adjacentElement3.waterline>currentElement.waterline) {
                        adjacentElement3.waterline = Math.max(currentElement.waterline, adjacentElement3.height);
                        heap.add(adjacentElement3);
                    }
                }
                currentElement.reverseBlock();
            }
        }
        for (MyElement[] i: elements)
            for (MyElement j: i)
                j.reverseBlock();


        long waterResidue = 0;
        Queue<MyElement> queue = new ArrayDeque<>();
        queue.add(elements[i0][j0]);
        while (!queue.isEmpty()) {
            currentElement = queue.poll();
            if (!currentElement.isBlocked) {
                if (currentElement.height< currentElement.waterline)
                    waterResidue += currentElement.waterline - currentElement.height;
                adjacentElement = elements[currentElement.x+1][currentElement.y];
                adjacentElement1 = elements[currentElement.x-1][currentElement.y];
                adjacentElement2 = elements[currentElement.x][currentElement.y+1];
                adjacentElement3 = elements[currentElement.x][currentElement.y-1];
                if (!(adjacentElement.y == 0 || adjacentElement.y == n-1 || adjacentElement.x == 0 || adjacentElement.x == m-1)&&adjacentElement.waterline<=currentElement.waterline) {
                    queue.add(adjacentElement);
                }
                if (!(adjacentElement1.y == 0 || adjacentElement1.y == n-1 || adjacentElement1.x == 0 || adjacentElement1.x == m-1)&&adjacentElement1.waterline<=currentElement.waterline) {
                    queue.add(adjacentElement1);
                }
                if (!(adjacentElement2.y == 0 || adjacentElement2.y == n-1 || adjacentElement2.x == 0 || adjacentElement2.x == m-1)&&adjacentElement2.waterline<=currentElement.waterline) {
                    queue.add(adjacentElement2);
                }
                if (!(adjacentElement3.y == 0 || adjacentElement3.y == n-1 || adjacentElement3.x == 0 || adjacentElement3.x == m-1)&&adjacentElement3.waterline<=currentElement.waterline) {
                    queue.add(adjacentElement3);
                }
                currentElement.reverseBlock();
            }
        }
        return Math.min(waterResidue, v);
    }
}
class MyElement implements Comparable<MyElement> {
    int height;
    int waterline;
    int x;
    int y;
    boolean isBlocked = false;

    private MyElement() {};

    public MyElement(int height, int waterline, int x, int y) {
        this.height = height;
        this.waterline = waterline;
        this.x = x;
        this.y = y;
    }

    public void reverseBlock() {
        isBlocked = !isBlocked;
    }

    public int compareTo(MyElement secondElement) {
        return Integer.compare(this.waterline, secondElement.waterline);

    }
}