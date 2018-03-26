import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by jan on 8.2.18.
 */
public class Task2 {
    public static void main(String[] args) {
        ArrayList<Long> arrayList = new ArrayList<>();
        Node node = null;
        long removableElement=0;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            removableElement = Long.parseLong(bufferedReader.readLine());
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            node = new Node(Long.parseLong(line));
            while(line!=null) {
                line = bufferedReader.readLine();
                node.add(Long.parseLong(line));
            }
        } catch(Exception ex) {}
        node.remove(removableElement);
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
    void remove(long x) {
        Node currentNode = this.getNodeOf(x);
        if (currentNode!=null) {
            if(currentNode.left == null ^ currentNode.right == null) {
                if(currentNode.parent==null) {
                    if (currentNode.left == null) {
                        currentNode.value = currentNode.right.value;
                        currentNode.left = currentNode.right.left;
                        if(currentNode.left!=null)
                            currentNode.left.parent = currentNode;
                        currentNode.right = currentNode.right.right;
                        if(currentNode.right!=null)
                            currentNode.right.parent = currentNode;
                    }
                    else if (currentNode.right == null) {
                        currentNode.value = currentNode.left.value;
                        currentNode.right = currentNode.left.right;
                        if(currentNode.right!=null)
                            currentNode.right.parent = currentNode;
                        currentNode.left = currentNode.left.left;
                        if(currentNode.left!=null)
                            currentNode.left.parent = currentNode;
                    }
                }
                else if (currentNode == currentNode.parent.left) {
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

            else if(currentNode.left == null && currentNode.right == null) {
                if (currentNode == currentNode.parent.left)
                    currentNode.parent.left = null;//калі ўсё будзе фігова, разгледзіць выпадак, калі ўсё дрэва складаецца з аднаго вузла
                else if (currentNode == currentNode.parent.right)
                    currentNode.parent.right = null;
            }

            else if(currentNode.left != null && currentNode.right != null) {
                Node newRootNode = currentNode.right;
                if (newRootNode.left == null) {
                    if(newRootNode.right!=null)
                        newRootNode.parent.right=newRootNode.right;
                    else newRootNode.parent.right = null;
                    currentNode.value = newRootNode.value;
                } else {
                    while (newRootNode.left != null)
                        newRootNode = newRootNode.left;
                    if(newRootNode.right!=null)
                        newRootNode.parent.left=newRootNode.right;
                    else newRootNode.parent.left = null;
                    currentNode.value = newRootNode.value;
                }
            }
        }
    }
}