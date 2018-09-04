import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(1);
        heap.add(2);
        heap.add(3);
        heap.add(8);
        heap.add(8);
        heap.add(4);
        heap.add(6);
        heap.add(5);
        for(int i=0; i< 8; i++) {
            System.out.println(heap.poll());
        }
    }
}
