import java.util.*;

public class equilibriummobile {
    private int nbrOfChangedWeights = 0;
    
    public static void main(String[] args) {
        new equilibriummobile().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        int nbrOfTestCases = 0;
        nbrOfTestCases = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nbrOfTestCases; i++) {
            String line = "";
            line = scanner.nextLine();

            if (line.charAt(0) != '[') {
                System.out.println("0");
            } else {
                nbrOfChangedWeights = 0;
                Node node = new Node(-1);
                boolean isRight = false;

                for (int j = 1; j < line.length()-1; j++) {
                    char ch = line.charAt(j);
                    if (ch == '[') {
                        if (node.left == null) {
                            node.left = new Node(-1);
                            node.left.parent = node;
                        }
                        if (node.right == null) {
                            node.right = new Node(-1);
                            node.right.parent = node;
                        }
                        if (isRight) {
                            node = node.right;
                        } else {
                            node = node.left;
                        }
                        isRight = false;
                    } else if (ch == ']') {
                        if (node.parent != null) {
                            node.weight = node.left.weight + node.right.weight;
                            node = node.parent;
                        }
                    } else if (ch == ',') {
                        isRight = true;
                    } else {
                        int idx = j;
                        while (j < line.length() && Character.isDigit(line.charAt(j))) {
                            j++;
                        }
                        long weight = Long.parseLong(line.substring(idx, j)); 
                        j--;

                        if (isRight) {
                            node.right = new Node(weight);
                            node.right.parent = node;
                        } else {
                            node.left = new Node(weight);
                            node.left.parent = node;
                        }
                    }
                }

                node.weight = node.left.weight + node.right.weight;

                Node node1 = copyTree(node);
                Node node2 = copyTree(node);
                traversePostOrder(node1, true);
                int nbrOfChangedWeights1 = nbrOfChangedWeights;
                nbrOfChangedWeights = 0;
                traversePostOrder(node2, false);
                int nbrOfChangedWeights2 = nbrOfChangedWeights;

                System.out.println(Math.min(nbrOfChangedWeights1, nbrOfChangedWeights2));
            }
        }
    }

    public void traversePostOrder(Node node, boolean biggest) {
        if (node != null) {
            try {
                traversePostOrder(node.left, biggest);
                traversePostOrder(node.right, biggest);
    
                if (!node.isLeaf()) {
                    if (node.left.weight != node.right.weight) {
                        nbrOfChangedWeights++;
                        long newWeight = 0;
                        if (biggest) {
                            newWeight = Math.max(node.left.weight, node.right.weight);
                        } else {
                            newWeight = Math.min(node.left.weight, node.right.weight);
                        }
                        node.left.weight = newWeight;
                        node.right.weight = newWeight;
                        node.weight = node.left.weight + node.right.weight;

                        Node current = node;
                        while (current.parent != null) {
                            current.parent.weight = current.parent.left.weight + current.parent.right.weight;
                            current = current.parent;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Node copyTree(Node node) {
        if (node == null) {
            return null;
        }
    
        Node newNode = new Node(node.weight);
        newNode.left = copyTree(node.left);
        newNode.right = copyTree(node.right);
    
        if (newNode.left != null) {
            newNode.left.parent = newNode;
        }
        if (newNode.right != null) {
            newNode.right.parent = newNode;
        }
    
        return newNode;
    }
}

class Node {
    long weight;
    Node left;
    Node right;
    Node parent;

    public Node(long weight) {
        this.weight = weight;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}