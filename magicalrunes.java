import java.util.*;

public class magicalrunes {
    public static void main(String[] args) {
        new magicalrunes().mainMethod();
    }
    
    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        String S = scanner.next();
        long D = scanner.nextLong();

        int N = S.length();
        StringBuilder sbInitial = new StringBuilder(S);
        long[] nbrOfFlips = new long[N];
        long[] nbrOfBToA = new long[N];
        StringBuilder sbFinal = new StringBuilder(S);
        
        nbrOfFlips[0] = D;

        for (int i = 0; i < N; i++) {
            if (sbInitial.charAt(i) == 'B') {
                nbrOfBToA[i] = (nbrOfFlips[i] + 1) / 2;
            } else {
                nbrOfBToA[i] = nbrOfFlips[i] / 2;
            }

            if (nbrOfFlips[i] % 2 == 0) {
                sbFinal.setCharAt(i, sbInitial.charAt(i));
            } else {
                sbFinal.setCharAt(i, flip(sbInitial.charAt(i)));
            }

            if (i + 1 < N) {
                nbrOfFlips[i + 1] = nbrOfBToA[i];
            }
        }

        System.out.println(sbFinal.toString());
    }

    public char flip(char c) {
        return c == 'A' ? 'B' : 'A';
    }
}