import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class dormroomdivide {
    public static void main(String[] args) throws NumberFormatException, IOException {
        new dormroomdivide().mainMethod();
    }

    public void mainMethod() throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int nbrOfPoints = Integer.parseInt(br.readLine());
        Point[] polygon = new Point[nbrOfPoints];

        for (int i = 0; i < nbrOfPoints; i++) {
            String[] coordinate = br.readLine().split(" ");
            polygon[i] = new Point(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]));
        }

        Point door = polygon[0];
        double totalArea = calculateTotalArea(door, polygon);
        Point divisionPoint = findDivisionPoint(door, polygon, totalArea);

        System.out.printf("%.6f %.6f%n", divisionPoint.x, divisionPoint.y);
    }

    // Calculates the total area of the polygon by summing up triangles formed by the door and each edge
    public double calculateTotalArea(Point door, Point[] polygon) {
        int n = polygon.length;
        double totalArea = 0.0;

        for (int i = 0; i < n; i++) {
            totalArea += triangleArea(door, polygon[i], polygon[(i + 1) % n]);
        }

        return totalArea;
    }

    // Finds the division point along the polygon where the area is divided in half
    public Point findDivisionPoint(Point door, Point[] polygon, double totalArea) {
        int n = polygon.length;
        double halfArea = totalArea / 2.0;
        double[] area = new double[n];
        double[] cumulativeArea = new double[n + 1];
        cumulativeArea[0] = 0.0;

        // Calculate the area of each triangle and the cumulative areas
        for (int i = 0; i < n; i++) {
            area[i] = triangleArea(door, polygon[i], polygon[(i + 1) % n]);
            cumulativeArea[i + 1] = cumulativeArea[i] + area[i];
        }

        // Find the edge where the cumulative area crosses half of the total area
        int k = 0;
        while (k < n && cumulativeArea[k + 1] < halfArea - 1e-9) {
            k++;
        }

        // Calculate the proportion along the edge where the division point is located
        double remainingArea = halfArea - cumulativeArea[k];
        double t = remainingArea / area[k];

        // Ensure t is within [0,1]
        t = Math.max(0.0, Math.min(1.0, t));

        // Calculate the division point using linear proportion
        Point p1 = polygon[k];
        Point p2 = polygon[(k + 1) % n];
        Point divisionPoint = new Point(
            p1.x + t * (p2.x - p1.x),
            p1.y + t * (p2.y - p1.y)
        );

        return divisionPoint;
    }

    // Calculates the area of a triangle given three points
    public double triangleArea(Point a, Point b, Point c) {
        return 0.5 * Math.abs(
            a.x * (b.y - c.y) +
            b.x * (c.y - a.y) +
            c.x * (a.y - b.y)
        );
    }

    class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}