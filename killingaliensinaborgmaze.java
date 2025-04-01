import java.util.*;

public class killingaliensinaborgmaze {

    public static void main(String[] args) {
        new killingaliensinaborgmaze().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        int nbrOfTestCases = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nbrOfTestCases; i++) {
            int nbrOfColumns = scanner.nextInt();
            int nbrOfRows = scanner.nextInt();
            scanner.nextLine();

            char[][] maze = new char[nbrOfRows][nbrOfColumns];
            ArrayList<List<Integer>> alienPositions = new ArrayList<>();
            ArrayList<List<Integer>> validPositions = new ArrayList<>();
            HashMap<List<Integer>, Integer> positionsToIndex = new HashMap<>();
            int index = 0;

            for (int j = 0; j < nbrOfRows; j++) {
                String s = scanner.nextLine();
                for (int k = 0; k < s.length(); k++) {
                    char c = s.charAt(k);
                    List<Integer> vertex = Arrays.asList(j, k);
                    if (c == 'A' || c == 'S') {
                        validPositions.add(vertex);
                        positionsToIndex.put(vertex, index++);
                        alienPositions.add(vertex);
                    } else if (c == ' ') {
                        validPositions.add(vertex);
                        positionsToIndex.put(vertex, index++);
                    }
                    maze[j][k] = c;
                }
            }

            int nbrOfValidPositions = validPositions.size();
            int[][] adjacencyMatrix = new int[nbrOfValidPositions][nbrOfValidPositions];
            ArrayList<Integer>[] adjacencyList = new ArrayList[nbrOfValidPositions];
            for (int j = 0; j < nbrOfValidPositions; j++) {
                adjacencyList[j] = new ArrayList<>();
            }

            for (int j = 0; j < nbrOfValidPositions; j++) {
                int k = validPositions.get(j).get(0);
                int l = validPositions.get(j).get(1);
                Integer neighborIndex;

                if (l - 1 >= 0 && maze[k][l - 1] != '#') {
                    neighborIndex = positionsToIndex.get(Arrays.asList(k, l - 1));
                    if (neighborIndex != null) {
                        adjacencyMatrix[j][neighborIndex] = adjacencyMatrix[neighborIndex][j] = 1;
                        adjacencyList[j].add(neighborIndex);
                    }
                }
                if (l + 1 < nbrOfColumns && maze[k][l + 1] != '#') {
                    neighborIndex = positionsToIndex.get(Arrays.asList(k, l + 1));
                    if (neighborIndex != null) {
                        adjacencyMatrix[j][neighborIndex] = adjacencyMatrix[neighborIndex][j] = 1;
                        adjacencyList[j].add(neighborIndex);
                    }
                }
                if (k - 1 >= 0 && maze[k - 1][l] != '#') {
                    neighborIndex = positionsToIndex.get(Arrays.asList(k - 1, l));
                    if (neighborIndex != null) {
                        adjacencyMatrix[j][neighborIndex] = adjacencyMatrix[neighborIndex][j] = 1;
                        adjacencyList[j].add(neighborIndex);
                    }
                }
                if (k + 1 < nbrOfRows && maze[k + 1][l] != '#') {
                    neighborIndex = positionsToIndex.get(Arrays.asList(k + 1, l));
                    if (neighborIndex != null) {
                        adjacencyMatrix[j][neighborIndex] = adjacencyMatrix[neighborIndex][j] = 1;
                        adjacencyList[j].add(neighborIndex);
                    }
                }
            }

            int INF = 10000;
            int[][] distances = new int[nbrOfValidPositions][nbrOfValidPositions];

            for (int j = 0; j < nbrOfValidPositions; j++) {
                Arrays.fill(distances[j], INF);
            }

            int nbrOfAliens = alienPositions.size();

            for (int j = 0; j < nbrOfAliens; j++) {
                int s = positionsToIndex.get(alienPositions.get(j));
                PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(u -> distances[s][u]));
                queue.add(s);
                distances[s][s] = 0;
                while (!queue.isEmpty()) {
                    int u = queue.poll();
                    for (int k = 0; k < adjacencyList[u].size(); k++) {
                        int v = adjacencyList[u].get(k);
                        if (distances[s][u] + 1 < distances[s][v]) {
                            distances[s][v] = distances[s][u] + 1;
                            queue.add(v);
                        }
                    }
                }
            }

            ArrayList<Edge>[] graph = new ArrayList[nbrOfAliens];
            for (int j = 0; j < nbrOfAliens; j++) {
                graph[j] = new ArrayList<>();
            }
            for (int j = 0; j < nbrOfAliens; j++) {
                for (int k = j + 1; k < nbrOfAliens; k++) {
                    Integer ii = positionsToIndex.get(alienPositions.get(j));
                    Integer jj = positionsToIndex.get(alienPositions.get(k));
                    if (ii != null && jj != null) {
                        graph[j].add(new Edge(j, k, distances[ii][jj]));
                        graph[k].add(new Edge(k, j, distances[ii][jj]));
                    }
                }
            }

            // Prims algorithm for MST
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
            ArrayList<Edge> mst = new ArrayList<>();
            boolean[] visited = new boolean[nbrOfValidPositions];
            long totalDistance = 0;
            visited[0] = true;

            for (Edge edge : graph[0]) {
                priorityQueue.add(edge);
            }

            while (mst.size() < nbrOfValidPositions - 1 && !priorityQueue.isEmpty()) {
                Edge e = priorityQueue.poll();
                int u = e.u;
                int v = e.v;
                int w = e.w;
                if (visited[u] && visited[v]) continue;
                if (visited[u]) {
                    for (Edge edge : graph[v]) {
                        priorityQueue.add(edge);
                    }
                }
                if (visited[v]) {
                    for (Edge edge : graph[u]) {
                        priorityQueue.add(edge);
                    }
                }
                visited[u] = visited[v] = true;
                mst.add(e);
                totalDistance += w;
            }
            System.out.println(totalDistance);
        }
    }
}

class Edge implements Comparable<Edge> {
    int u, v, w;

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge e) {
        return Integer.compare(w, e.w);
    }


}