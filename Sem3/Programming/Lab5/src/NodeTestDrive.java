/**
 * Created by jan on 9.11.17.
 */
public class NodeTestDrive {
    public static void main(String[] args) {
        Node<Integer> myTree = new Node<Integer>(10);
        Node<Integer> node = new Node<Integer>(36);
            myTree.add(5);
            myTree.add(35);
            myTree.add(1);
            myTree.add(7);
            myTree.add(6);
            myTree.add(8);
            myTree.add(37);
            myTree.add(node);
            System.out.println(myTree.find(1));
            myTree.remove(5);
            myTree.remove(35);
            myTree.recPreOrder();
            System.out.println();
            myTree.recInOrder();
            System.out.println();
            myTree.recPostOrder();

            Node<Double> myDoubleTree = new Node<Double>(10.1);
            Node<Double> doubleNode = new Node<Double>(36.1);
            myDoubleTree.add(5.1);
            myDoubleTree.add(35.1);
            myDoubleTree.add(1.1);
            myDoubleTree.add(7.1);
            myDoubleTree.add(6.1);
            myDoubleTree.add(8.1);
            myDoubleTree.add(37.1);
            myDoubleTree.add(doubleNode);
            System.out.println(myDoubleTree.find(1.2));
            myDoubleTree.remove(5.1);
            myDoubleTree.remove(10.1);
            myDoubleTree.remove(35.1);
            myDoubleTree.recPreOrder();
            System.out.println();
            myDoubleTree.recInOrder();
            System.out.println();
            myDoubleTree.recPostOrder();
    }
}
