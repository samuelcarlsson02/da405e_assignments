import java.util.*;

public class decisionsdecisions {
    private Node root;
    private Node node;
    private int nbrOfNodes = 0;
    private Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        new decisionsdecisions().mainMethod();
    }

    public void mainMethod() {
        int n = scanner.nextInt();
        scanner.nextLine();
        root = new Node(-1);
        nbrOfNodes = (int) Math.pow(2, n) * 2 - 1;
        buildTree(n);
        buildMinimalBDD(root);
        System.out.println(nbrOfNodes);
    }

    public void buildMinimalBDD(Node root) {
        Map<Node, Node> parentMap = new HashMap<>();
        populateParentMap(root, null, parentMap);

        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (!node.isLeaf() && node.left != null && node.right != null) {
                if (node.left.value == node.right.value && node.left.isLeaf() && node.right.isLeaf()) {
                    node.value = node.left.value;
                    nbrOfNodes -= 2;

                    Node parent = parentMap.get(node);
                    if (parent != null) {
                        stack.push(parent);
                    }
                } else {
                    stack.push(node.left);
                    stack.push(node.right);
                }
            }
        }
    }

    private void populateParentMap(Node node, Node parent, Map<Node, Node> parentMap) {
        if (node == null) {
            return;
        }
        parentMap.put(node, parent);
        populateParentMap(node.left, node, parentMap);
        populateParentMap(node.right, node, parentMap);
    }

    public void buildTree(int n) {
        int halfOfLeaves = (int) Math.pow(2, n) / 2;
        node = root;
        String inputLine = scanner.nextLine();
        String inputWithoutSpaces = inputLine.replaceAll("\\s+", "");
        Node parent = null;
        int bit = 0;

        for (int i = 0; i < halfOfLeaves; i++) {
            node = root;

            for (int j = 0; j < n; j++) {
                bit = (i >> j) & 1;
                if (bit == 0) {
                    if (node.left == null) {
                        node.left = new Node(-1);
                    }
                    parent = node;
                    node = node.left;
                } else {
                    if (node.right == null) {
                        node.right = new Node(-1);
                    }
                    parent = node;
                    node = node.right;
                }
            }

            if (bit == 0) {
                parent.left = new Node(inputWithoutSpaces.charAt(i) - '0');
                parent.right = new Node(inputWithoutSpaces.charAt(i + halfOfLeaves) - '0');
            } else {
                parent.right = new Node(inputWithoutSpaces.charAt(i) - '0');
                parent.left = new Node(inputWithoutSpaces.charAt(i + halfOfLeaves) - '0');
            }

            if (parent.left.value == parent.right.value) {
                parent.value = parent.left.value;
                parent.left = null;
                parent.right = null;
                nbrOfNodes -= 2;
            }
        }
    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.value = -1;
    }

    public boolean isLeaf() {
        return value != -1;
    }
}
