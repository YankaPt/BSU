import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jan on 10.2.18.
 */
public class Task9 implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Task9(), "", 64 * 1024 * 1024).start();
    }
    public void run() {
        Tree tree = new Tree();
        try {
            FileReader fileReader = new FileReader("in.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line!=null) {
                tree.add(Long.parseLong(line));
                line = bufferedReader.readLine();
            }
        } catch(Exception ex) {}
        ArrayList<Long> arrayList = tree.getNodesOfMSList();
        Collections.sort(arrayList);
        tree.remove(arrayList.get(1));
        try {
            FileWriter fileWriter = new FileWriter("out.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(long i: tree.getPreOrderedList())
                bufferedWriter.write(i+"\n");
            bufferedWriter.flush();
            fileWriter.close();
        } catch(Exception ex) {}
    }
}
class Tree {

    class Node {
        long value;
        Node left;
        Node right;
        Node parent;
        int height;
        int msl=0;
        int h1;

        Node(long value) {this.value=value;}
    }

    private Node rootNode;
    private ArrayList<Long> preOrderedList = new ArrayList<>();
    private ArrayList<Long> nodesOfMSList = new ArrayList<>();
    private int msl=0;

    Tree(){}

   public void addNode(Node newNode) {
        if(getNodeOf(newNode.value) == null) {
            if (rootNode == null)
                rootNode = newNode;
            else {
                Node currentNode = rootNode;
                Node parentNode = null;
                while (currentNode != null) {
                    parentNode = currentNode;
                    if (newNode.value >= currentNode.value) {  //выправіць >= на >
                        currentNode = currentNode.right;
                    } else {
                        currentNode = currentNode.left;
                    }
                }
                if (newNode.value >= parentNode.value) {
                    parentNode.right = newNode;
                    parentNode.right.parent = parentNode;
                } else {
                    parentNode.left = newNode;
                    parentNode.left.parent = parentNode;
                }
            }
        }
    }

    public void add(long x) {
        Node additionalNode = new Node(x);
        this.addNode(additionalNode);
    }

    private boolean find(long x) {
        if (this.getNodeOf(x) != null)
            return  true;
        else
            return false;
    }
    private Node getNodeOf(long x) {
        if (rootNode == null)
            return null;
        if(x==rootNode.value)
            return rootNode;
        Node currentNode = rootNode;
        Node parentNode = null;
        while (currentNode != null) {
            parentNode = currentNode;
            if (x > currentNode.value) {
                currentNode = currentNode.right;
            } else if(x < currentNode.value) {
                currentNode = currentNode.left;
            } else {
                return parentNode;
            }
        }
        return  null;
    }

    public ArrayList<Long> getPreOrderedList() {
        preOrder(rootNode);
        return preOrderedList;
    }
    public ArrayList<Long> getNodesOfMSList() {
        algPostOrder(rootNode);
        algPreOrder(rootNode);
        return nodesOfMSList;
    }

    private void preOrder(Node node) {
        preOrderedList.add(node.value);
        if(node.left!=null) preOrder(node.left);
        if (node.right!=null) preOrder(node.right);
    }
    private void algPostOrder(Node node) {
        if(node.left!=null) algPostOrder(node.left);
        if (node.right!=null) algPostOrder(node.right);
        if(node.left==null && node.right==null)
            node.height = 0;
        else if(node.left==null ^ node.right==null) {
            if(node.left == null) {
                node.height = node.right.height + 1;
                node.msl = node.right.height + 1;
            } else {
                node.height = node.left.height + 1;
                node.msl = node.left.height + 1;
            }
        }
        else {
            node.height = Math.max(node.left.height, node.right.height) + 1;
            node.msl = node.left.height + node.right.height + 2;
        }
        msl = Math.max(msl, node.msl);
    }
    private void algPreOrder(Node node) {

        if(node==rootNode)
            node.h1=0;
            if(node.left==null ^ node.right==null) {
                if(node.left == null) {
                    node.right.h1 = node.h1 + 1;
                } else {
                    node.left.h1 = node.h1 + 1;
                }
            }
            else if(node.left!=null && node.right!=null) {
                if(node.h1 <= node.right.height+1) {
                    node.left.h1 = node.right.height + 2;
                } else {
                    node.left.h1 = node.h1 + 1;
                }
                if(node.h1 <= node.left.height+1) {
                    node.right.h1 = node.left.height + 2;
                } else {
                    node.right.h1 = node.h1 + 1;
                }
            }
        if(node.msl==msl || (node.h1+node.height==msl))
            nodesOfMSList.add(node.value);
        if(node.left!=null) algPreOrder(node.left);
        if (node.right!=null) algPreOrder(node.right);
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
