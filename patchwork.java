import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class patchwork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int R = scanner.nextInt();
        int C = scanner.nextInt();
        String[][] patchwork = new String[R][C];
        HashMap<Integer, String[][]> patches = new HashMap<>();

        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            String[][] patch = new String[r][c];

            for (int j = 0; j < r; j++) {
                String line = scanner.next();
                for (int k = 0; k < c; k++) {
                    patch[j][k] = String.valueOf(line.charAt(k));
                }
            }
            patches.put(i, patch);
        }

        int M = scanner.nextInt();
        for (int i = 0; i < M; i++) {
            int q = scanner.nextInt();
            int t = scanner.nextInt();
            int p = scanner.nextInt();

            int patchIndex = p - 1;
            String[][] patch = patches.get(patchIndex);
            
            for (int j = 0; j < patch.length; j++) {
                for (int k = 0; k < patch[0].length; k++) {
                    if (q + j < R && t + k < C) {
                        patchwork[q + j][t + k] = patch[j][k];
                    }
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (patchwork[i][j] == null) {
                    patchwork[i][j] = ".";
                }
                System.out.print(patchwork[i][j]);
            }
            System.out.println();
        }
    }
}