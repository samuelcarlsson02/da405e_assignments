import java.util.*;

public class snakesandmasters {
    public static void main(String[] args) {
        new snakesandmasters().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int mod = 1000000;
        int[] dp = new int[N + 1];
        
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= N; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % mod;
        }

        System.out.println(dp[N]);
    }
}

