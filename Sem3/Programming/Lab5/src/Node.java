import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * Created by jan on 9.11.17.
 */
public class Node <T extends Number> {
    private T value;
    private Node left;
    private Node right;
    private Node parent;
    private NumberComparator numberComparator = new NumberComparator();

    Node(T val) {
        value = val;
    }

    void add (T x){
        Node additionalNode = new Node(x);
        this.add(additionalNode);
    }
    void add (Node newNode){
        Node currentNode = this;
        Node rootNode = null;
        while(currentNode != null) {
            rootNode = currentNode;
            if (numberComparator.compare(newNode.value, currentNode.value) >=0) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }
        if (numberComparator.compare(newNode.value, rootNode.value)>=0) {
            rootNode.right = newNode;
            rootNode.right.parent = rootNode;
        } else {
            rootNode.left = newNode;
            rootNode.left.parent = rootNode;
        }
    }
    boolean find(T x) {
        if (this.getNodeOf(x) != null)
            return  true;
        else
            return false;
    }
    Node getNodeOf(T x) {
        if(numberComparator.compare(x, this.value)==0)
            return this;
        Node currentNode = this;
        Node rootNode = null;
        while (currentNode != null) {
            rootNode = currentNode;
            if (numberComparator.compare(x, currentNode.value) >0) {
                currentNode = currentNode.right;
            } else if(numberComparator.compare(x, currentNode.value) < 0) {
                currentNode = currentNode.left;
            } else {
                return rootNode;
            }
        }
        return  null;
    }
    void recPreOrder() {
        System.out.print(value + " ");
        if(left!=null) left.recPreOrder();
        if (right!=null) right.recPreOrder();
    }
    void recInOrder() {
        if(left!=null) left.recInOrder();
        System.out.print(value + " ");
        if (right!=null) right.recInOrder();
    }
    void recPostOrder() {
        if(left!=null) left.recPostOrder();
        if (right!=null) right.recPostOrder();
        System.out.print(value + " ");
    }
    void remove(T x) {
        Node currentNode = this.getNodeOf(x);
        if (currentNode!=null) {
            if(currentNode.left == null ^ currentNode.right == null) {
                if (currentNode == currentNode.parent.left) {
                    if (currentNode.left == null)
                        currentNode.parent.left = currentNode.right;
                    if (currentNode.right == null)
                        currentNode.parent.left = currentNode.left;
                }
                else if(currentNode == currentNode.parent.right) {
                    if (currentNode.left == null)
                        currentNode.parent.right = currentNode.right;
                    if (currentNode.right == null)
                        currentNode.parent.right = currentNode.left;
                }
            }

            if(currentNode.left == null && currentNode.right == null) {
                if (currentNode == currentNode.parent.left)
                    currentNode.parent.left = null;
                else if (currentNode == currentNode.parent.right)
                    currentNode.parent.right = null;
                /*else
                    this = null; */
            }

            if(currentNode.left != null && currentNode.right != null) {
                currentNode.right.add(currentNode.left);
                if(currentNode.parent == null) {
                    currentNode.right.parent = null;
                    currentNode.value = currentNode.right.value;
                    if (currentNode.right.left!=null)
                        currentNode.right.left.parent = currentNode;
                    if (currentNode.right.right!=null)
                        currentNode.right.right.parent = currentNode;
                    currentNode.left = currentNode.right.left;
                    currentNode.right = currentNode.right.right;
                }
                else {
                    if (currentNode == currentNode.parent.left) {
                        currentNode.right.parent = currentNode.parent;
                        currentNode.parent.left = currentNode.right;
                    } else if (currentNode == currentNode.parent.right) {
                        currentNode.right.parent = currentNode.parent;
                        currentNode.parent.right = currentNode.right;
                    }
                }
            }
        }
    }
}
class MyNumber implements Comparable<MyNumber> {
    private Double value;

    MyNumber(Double x) {value=x;};

    public Double getValue() {
        return this.value;
    }

    public int compareTo(MyNumber secondNumber) {
        return this.value.compareTo(secondNumber.value);
    }
}
class NumberComparator implements Comparator<Number> {
    public int compare (Number a, Number b) {
        if (a instanceof Integer)
            return ((Integer) a).compareTo((Integer) b);
        if (a instanceof Double)
            return ((Double) a).compareTo((Double) b);
        if (a instanceof BigInteger)
            return ((BigInteger) a).compareTo((BigInteger) b);
        if (a instanceof BigDecimal)
            return ((BigDecimal) a).compareTo((BigDecimal) b);
        return 0;
    }
}