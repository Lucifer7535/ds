import java.util.Scanner;

public class TokenRingMutualExclusion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of nodes in the ring: ");
        int n = sc.nextInt();
        int[] nodeIds = new int[n];
        for (int i = 0; i < n; i++) {
            nodeIds[i] = i + 1;
        }
        System.out.println("\nRing formed is as below: ");
        for (int nodeId : nodeIds) {
            System.out.print(nodeId + " ");
        }
        System.out.println("");
        int token = 1; // The token initially starts with node 1
        boolean[] usingResource = new boolean[n]; // Initially, no node is using the shared resource
        int choice;

        do {
            System.out.print("\nEnter node id that wants to access shared resource: ");
            int requestingNode = sc.nextInt() - 1; // Subtract 1 to get the index of the node in the array

            // Wait until the token arrives at the requestingNode
            while (token != requestingNode + 1) {
                System.out.println("\nToken passing from node " + token + " to node " + ((token % n) + 1));
                token = (token % n) + 1;
            }

            // Request access to the shared resource by setting the corresponding flag to true
            usingResource[requestingNode] = true;
            System.out.println("\nNode " + (requestingNode + 1) + " is requesting access to shared resource");

            // Check if any other node is currently using the shared resource
            boolean isUsingResource = false;
            for (boolean flag : usingResource) {
                if (flag) {
                    isUsingResource = true;
                    break;
                }
            }

            // If no other node is using the shared resource, grant access to the requesting node
            if (!isUsingResource) {
                System.out.println("\nNode " + (requestingNode + 1) + " has been granted access to shared resource");
                usingResource[requestingNode] = false; // Release the shared resource
                token = (token % n) + 1; // Pass the token to the next node in the ring
            } else {
                System.out.println("\nNode " + (requestingNode + 1) + " is waiting for other nodes to release shared resource");
            }

            System.out.print("\nDo you want to continue? Enter 1 for Yes, 0 for No: ");
            choice = sc.nextInt();
        } while (choice == 1);
        sc.close();
    }
}

