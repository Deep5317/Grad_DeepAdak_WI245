package src;

import java.util.Scanner;

public class OwnerService {

    public static void ownerMenu(int ownerId) {

        Scanner sc = new Scanner(System.in);
        OwnerDAO dao = new OwnerDAO();

        while (true) {

            System.out.println("\n=== OWNER MENU ===");
            System.out.println("1. View My Site");
            System.out.println("2. View My Maintenance");
            System.out.println("3. Request Site Status Change");
            System.out.println("4. Password Change");
            System.out.println("5. View My Requests");
            System.out.println("6. Exit");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    dao.viewMySite(ownerId);
                    break;

                case 2:
                    dao.viewMyMaintenance(ownerId);
                    break;

                case 3:
                    System.out.println("Enter new status (OPEN/UNDER_CONSTRUCTION/OCCUPIED):");
                    dao.requestSiteUpdate(ownerId, sc.next());
                    break;

                case 4:
                    System.out.print("Enter Old Password: ");
                    String oldp = sc.next();

                    System.out.print("Enter New Password: ");
                    String newp = sc.next();

                    dao.changePassword(ownerId, oldp, newp);
                    break;

                case 5:
                    dao.viewMyRequests(ownerId);
                    break;

                case 6:
                    return;

            }
        }
    }

    

}
