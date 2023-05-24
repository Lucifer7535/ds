import java.util.Scanner;

public class Ring {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int num = in.nextInt();

        Rr[] proc = new Rr[num];

        // Initialize processes
        for (int i = 0; i < num; i++) {
            proc[i] = new Rr();
            proc[i].index = i;
            System.out.println("Enter the ID of process " + (i + 1) + ": ");
            proc[i].id = in.nextInt();
            proc[i].state = "active";
            proc[i].f = 0;
        }

        // Sort processes based on ID
        for (int i = 0; i < num - 1; i++) {
            for (int j = 0; j < num - 1; j++) {
                if (proc[j].id > proc[j + 1].id) {
                    Rr temp = proc[j];
                    proc[j] = proc[j + 1];
                    proc[j + 1] = temp;
                }
            }
        }

        // Print the sorted processes
        for (int i = 0; i < num; i++) {
            System.out.print("[" + i + "] " + proc[i].id + " ");
        }

        // Select last process as coordinator
        proc[num - 1].state = "inactive";
        System.out.println("\nProcess " + proc[num - 1].id + " selected as coordinator");

        while (true) {
            System.out.println("\n1. Election\n2. Quit");
            int ch = in.nextInt();

            // Reset flags
            for (int i = 0; i < num; i++) {
                proc[i].f = 0;
            }

            switch (ch) {
                case 1:
                    System.out.println("Enter the process number that initializes the election: ");
                    int init = in.nextInt();
                    int temp2 = init;
                    int temp1 = init + 1;
                    int i = 0;

                    while (temp2 != temp1) {
                        if (temp1 == num) {
                            temp1 = 0;
                        }
                        if ("active".equals(proc[temp1].state) && proc[temp1].f == 0) {
                            System.out.println("Process " + proc[init].id + " sends a message to Process " + proc[temp1].id);
                            proc[temp1].f = 1;
                            init = temp1;
                            i++;
                        }
                        temp1++;
                    }

                    System.out.println("Process " + proc[init].id + " sends a message to Process " + proc[temp1].id);
                    int max = -1;

                    // Find maximum ID for coordinator selection
                    for (int j = 0; j < i; j++) {
                        if (max < proc[j].id) {
                            max = proc[j].id;
                        }
                    }

                    // Select coordinator and update states
                    System.out.println("Process " + max + " selected as coordinator");
                    for (int k = 0; k < num; k++) {
                        if (proc[k].id == max) {
                            proc[k].state = "inactive";
                        }
                    }
                    break;
                case 2:
                    System.out.println("Program terminated.");
                    in.close();
                    return;
                default:
                    System.out.println("Invalid response.");
                    break;
            }
        }
    }
}

class Rr {
    public int index; // To store the index of the process
    public int id; // To store the ID/name of the process
    public int f;
    public String state; // Indicates whether the process is active or inactive
}
