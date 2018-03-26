import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by jan on 8.2.18.
 */
public class Task1 {
    public static void main(String[] args) {
        ArrayList<Long> arrayList = new ArrayList<>();
        Node node = null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            node = new Node(Long.parseLong(line));
            while(line!=null) {
                line = bufferedReader.readLine();
                node.add(Long.parseLong(line));
            }
        } catch(Exception ex) {}
        preOrder(node, arrayList);
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(long i: arrayList)
                bufferedWriter.write(i+"\n");
            bufferedWriter.flush();
            fileWriter.close();
        } catch(Exception ex) {}
    }
    public static void preOrder(Node node, ArrayList<Long> arrayList) {
        arrayList.add(node.value);
        if(node.left!=null) preOrder(node.left, arrayList);
        if (node.right!=null) preOrder(node.right, arrayList);
    }
}
class Node {
    long value;
    Node left;
    Node right;
    Node parent;

    Node(long val) {
        value = val;
    }

    void add(long x) {
        Node additionalNode = new Node(x);
        this.add(additionalNode);
    }

    void add(Node newNode) {
        if(this.getNodeOf(newNode.value)== null) {
            Node currentNode = this;
            Node rootNode = null;
            while (currentNode != null) {
                rootNode = currentNode;
                if (newNode.value > currentNode.value) {
                    currentNode = currentNode.right;
                } else {
                    currentNode = currentNode.left;
                }
            }
            if (newNode.value > rootNode.value) {
                rootNode.right = newNode;
                rootNode.right.parent = rootNode;
            } else {
                rootNode.left = newNode;
                rootNode.left.parent = rootNode;
            }
        }
    }
    Node getNodeOf(long x) {
        if(x==this.value)
            return this;
        Node currentNode = this;
        Node rootNode = null;
        while (currentNode != null) {
            rootNode = currentNode;
            if (x>currentNode.value) {
                currentNode = currentNode.right;
            } else if(x<currentNode.value) {
                currentNode = currentNode.left;
            } else {
                return rootNode;
            }
        }
        return  null;
    }
}
