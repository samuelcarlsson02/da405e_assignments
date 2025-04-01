import java.util.*;

public class cheatingstudents {
    public static void main(String[] args) {
        new cheatingstudents().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        int nbrOfStudents = scanner.nextInt();
        int[][] studentPositions = new int[nbrOfStudents][2];
        for (int i = 0; i < nbrOfStudents; i++) {
            studentPositions[i][0] = scanner.nextInt();
            studentPositions[i][1] = scanner.nextInt();
        }
        
        buildGraph(studentPositions);
        System.out.println(2*primMST(buildGraph(studentPositions), nbrOfStudents)); //students need to go back to their original positions
    }

    public int calculateManhattanDistance(int[] student1, int[] student2) {
        return Math.abs(student1[0] - student2[0]) + Math.abs(student1[1] - student2[1]);
    }

    public List<List<Edge>> buildGraph(int[][] studentPositions) {
        int n = studentPositions.length;
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int weight = calculateManhattanDistance(studentPositions[i], studentPositions[j]);
                graph.get(i).add(new Edge(j, weight));
                graph.get(j).add(new Edge(i, weight));
            }
        }

        return graph;
    }

    public int primMST(List<List<Edge>> graph, int nbrOfVertices) {
        boolean visited[] = new boolean[nbrOfVertices];
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        int totalWeight = 0;

        // Start from node 0
        minHeap.add(new Edge(0, 0));

        while (!minHeap.isEmpty()) {
            Edge current = minHeap.poll();
            int node = current.target;

            if (visited[node]) {
                continue;
            }

            visited[node] = true;
            totalWeight += current.weight;

            for (Edge edge : graph.get(node)) {
                if (!visited[edge.target]) {
                    minHeap.add(edge);
                }
            }
        }
        return totalWeight;
    }

    class Edge {
        int target;
        int weight;
    
        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }
    
    class Node {
        int x;
        int y;
        Edge edge;
    
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
