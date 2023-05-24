import java.util.Scanner;

public class Bully {
    static boolean[] state = new boolean[5];
    public static int coordinator = 4;

    public static void getStatus() {
        System.out.println("\n+------Current System State-----+");
        for (int i = 0; i < state.length; i++) {
            System.out.println("| P" + (i + 1) + ":\t" + (state[i] ? "UP" : "DOWN") +
                    (coordinator == i ? "\t<-- COORDINATOR\t|" : "\t\t\t|"));
        }
        System.out.println("+-------------------------------+");
    }

    public static void up(int up) {
        if (state[up - 1]) {
            System.out.println("Process " + up + " is already up");
        } else {
            state[up - 1] = true;
            System.out.println("--------Process " + up + " held election-------");
            for (int i = up; i < state.length; ++i) {
                System.out.println("Election message sent from process " + up + " to process " + (i + 1));
            }
            for (int i = state.length - 1; i >= 0; --i) {
                if (state[i]) {
                    coordinator = i;
                    break;
                }
            }
        }
    }

    public static void down(int down) {
        if (!state[down - 1]) {
            System.out.println("Process " + down + " is already down.");
        } else {
            state[down - 1] = false;
            if (coordinator == down - 1) {
                setCoordinator();
            }
        }
    }

    public static void mess(int mess) {
        if (state[mess - 1]) {
            if (state[coordinator]) {
                System.out.println("Message Sent: Coordinator is alive");
            } else {
                System.out.println("Coordinator is down");
                System.out.println("Process " + mess + " initiated election");
                for (int i = mess; i < state.length; ++i) {
                    System.out.println("Election sent from process " + mess + " to process " + (i + 1));
                }
                setCoordinator();
            }
        } else {
            System.out.println("Process " + mess + " is down");
        }
    }

    public static void setCoordinator() {
        for (int i = state.length - 1; i >= 0; i--) {
            if (state[i]) {
                coordinator = i;
                break;
            }
        }
    }

    public static void main(String[] args) {
        int choice;
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < state.length; ++i) {
            state[i] = true;
        }
        getStatus();
        do {
            System.out.println("+........MENU........+");
            System.out.println("1. Activate a process.");
            System.out.println("2. Deactivate a process.");
            System.out.println("3. Send a message.");
            System.out.println("4. Exit.");
            System.out.println("+....................+");
            choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("Activate process:");
                    int up = sc.nextInt();
                    if (up == 5) {
                        System.out.println("Process 5 is the coordinator");
                        state[4] = true;
                        coordinator = 4;
                        break;
                    }
                    up(up);
                    break;
                }
                case 2: {
                    System.out.println("Deactivate process:");
                    int down = sc.nextInt();
                    down(down);
                    break;
                }
                case 3: {
                    System.out.println("Send message from process:");
                    int mess = sc.nextInt();
                    mess(mess);
                    break;
                }
            }
            getStatus();
        } while (choice != 4);
        sc.close();
    }
}

