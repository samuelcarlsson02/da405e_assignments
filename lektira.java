import java.util.*;

public class lektira {
    public static void main(String[] args) {
        new lektira().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(getAllSplits(input));
    }

    public String getAllSplits(String word) {
        List<String> possibleWords = new ArrayList<>();
        for (int i = 1; i < word.length() - 1; i++) {
            for (int j = i + 1; j < word.length(); j++) {
                char[] first = word.substring(0, i).toCharArray();
                char[] second = word.substring(i, j).toCharArray();
                char[] third = word.substring(j).toCharArray();

                reverseArray(first);
                reverseArray(second);
                reverseArray(third);

                char[] chars = new char[word.length()];
                int index = 0;
                for (char c : first) {
                    chars[index++] = c;
                }
                for (char c : second) {
                    chars[index++] = c;
                }
                for (char c : third) {
                    chars[index++] = c;
                }
                
                possibleWords.add(new String(chars));
            }
        }
        Collections.sort(possibleWords);
        return possibleWords.get(0);
    }

    private void reverseArray(char[] arr) {
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
