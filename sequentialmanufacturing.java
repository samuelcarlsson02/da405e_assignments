import java.util.*;

public class sequentialmanufacturing {
    public static void main(String[] args) {
        new sequentialmanufacturing().mainMethod();
    }

    public void mainMethod() {
        Scanner scanner = new Scanner(System.in);
        int nbrOfMachines = scanner.nextInt();
        long nbrOfProducts = scanner.nextLong();
        Machine[] machines = new Machine[nbrOfMachines];
        long manufacturingTime = 0;
        int slowestMachineIndex = 0;

        for (int i = 0; i < nbrOfMachines; i++) {
            machines[i] = new Machine(scanner.nextLong(), nbrOfProducts);
         }

        for (int i = 0; i < nbrOfMachines; i++) {
            //logik för att hitta den långsammaste maskinen
            Machine currentMachine = machines[i];
            if(i > 0 && currentMachine.processingTime >= machines[slowestMachineIndex].processingTime){
                slowestMachineIndex = i;
            }
        }

        long timeUntilStart = 0;
        long timeToAddBecauseOfOverflow = 0;
        for (int i = 0; i < nbrOfMachines; i++) {
            if (i == slowestMachineIndex){
                continue;
            } else {
                timeUntilStart += machines[i].processingTime;
            }
        }

        manufacturingTime = machines[slowestMachineIndex].processingTime * nbrOfProducts + timeUntilStart + timeToAddBecauseOfOverflow;
        System.out.println(manufacturingTime);
    }
}

class Machine {
    long processingTime;
    long productsToProcess;

    public Machine(long processingTime, long productsToProcess) {
        this.processingTime = processingTime;
        this.productsToProcess = productsToProcess;
    }
}
