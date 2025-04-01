import java.util.*;

public class socialdistancing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> occupiedSeats = new ArrayList<>();
        ArrayList<String> emptySeats = new ArrayList<>();
        int nbrOfSeats = sc.nextInt();
        int nbrOfPeopleSeated = sc.nextInt();
        int output = 0;

        for (int i = 0; i < nbrOfPeopleSeated; i++) {
            occupiedSeats.add(sc.nextInt());
        }

        for (int i = 0; i < nbrOfSeats; i++) {
            if (occupiedSeats.contains(i + 1)) {
                emptySeats.add("no");
            } else {
                emptySeats.add("yes");
            }
        }

        for (int i = 0; i < emptySeats.size(); i++) {
            if (emptySeats.get(i).equals("yes")) {
                if (i > 0 && i < emptySeats.size() - 1) {
                    if (
                        emptySeats.get(i - 1).equals("yes") &&
                        emptySeats.get(i + 1).equals("yes")
                    ) {
                        emptySeats.set(i, "no");
                        output++;
                    }
                } else if (i == 0) {
                    if (
                        emptySeats.get(i + 1).equals("yes") &&
                        emptySeats.get(emptySeats.size() - 1).equals("yes")
                    ) {
                        emptySeats.set(i, "no");
                        output++;
                    }
                } else if (i == emptySeats.size() - 1) {
                    if (
                        emptySeats.get(i - 1).equals("yes") &&
                        emptySeats.get(0).equals("yes")
                    ) {
                        emptySeats.set(i, "no");
                        output++;
                    }
                }
            }
        }
        System.out.println(output);
    }
}
