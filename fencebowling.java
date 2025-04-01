import java.util.*;

public class fencebowling {
    public static void main(String[] args) {
        new fencebowling().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        double w = scanner.nextDouble();
        double l = scanner.nextDouble();

        // Calculate the sum = Σ (dx_n * 2^n)
        double sum = 0.0;
        for (int n = 0; n <= k; n++) {
            double dx;
            // Ball starts in the middle and ends in the middle = half the width
            if (n == 0 || n == k) {
                dx = w / 2.0;
            } else {
                dx = w;
            }
            // To account for doubling the angle's tangent after each bounce
            sum += dx * Math.pow(2.0, n);
        }

        double tanBeta = l / sum;
        double betaRadians = Math.atan(tanBeta);
        double betaDegrees = Math.toDegrees(betaRadians);

        System.out.printf("%.7f\n", betaDegrees);
    }
}