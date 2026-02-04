package src;

import java.util.Scanner;

public class AdminService {

    static Scanner sc = new Scanner(System.in);

    public static void adminMenu() {

        AdminDAO dao = new AdminDAO();

        while (true) {

            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add Owner");
            System.out.println("2. Edit Owner");
            System.out.println("3. Remove Owner");  
            System.out.println("5. Assign Site");
            System.out.println("6. Collect Maintenance");
            System.out.println("7. View Maintenance");
            System.out.println("8. Approve Site Requests");
            System.out.println("9. View Pending Requests");
            System.out.println("10. View Available Sites");
            System.out.println("11. View All Owners");
            System.out.println("12. Exit");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    System.out.print("Username: ");
                    String u = sc.next();

                    System.out.print("Password: ");
                    String p = sc.next();

                    dao.addOwner(u, p);
                    break;

                case 2:
                    if (verifyAdmin(dao)) {
                        System.out.print("Owner ID: ");
                        int id = sc.nextInt();

                        System.out.print("New Username: ");
                        String nu = sc.next();

                        System.out.print("New Password: ");
                        String np = sc.next();

                        dao.editOwner(id, nu, np);
                    }
                    break;

                case 3:
                    if (verifyAdmin(dao)) {
                        System.out.print("Owner ID: ");
                        dao.removeOwner(sc.nextInt());
                    }
                    break;

                case 4:
                    dao.viewAvailableSites();
                    break;

                case 5:
                    if (verifyAdmin(dao)) {
                        System.out.print("Owner ID: ");
                        int ownerId = sc.nextInt();

                        dao.viewAvailableSites();

                        System.out.print("Select Site Number: ");
                        int siteNo = sc.nextInt();

                        System.out.println("Property Type:");
                        System.out.println("1. Villa");
                        System.out.println("2. Apartment");
                        System.out.println("3. Independent");
                        System.out.println("4. OpenSite");

                        int type = sc.nextInt();

                        dao.assignSite(ownerId, siteNo, type);
                    }
                    break;

                case 6:
                    if (verifyAdmin(dao)) {
                        System.out.print("Site Number: ");
                        int s = sc.nextInt();

                        System.out.print("Amount Paid: ");
                        double amt = sc.nextDouble();

                        dao.collectMaintenance(s, amt);
                    }
                    break;

                case 7:
                    System.out.println("1. All Sites");
                    System.out.println("2. Specific Site");

                    int opt = sc.nextInt();

                    if (opt == 1)
                        dao.viewAllMaintenance();
                    else {
                        System.out.print("Enter Site Number: ");
                        dao.viewMaintenanceBySite(sc.nextInt());
                    }
                    break;

                case 8:
                    if (verifyAdmin(dao)) {
                        dao.handleSiteRequests();
                    }
                    break;
                case 9:
                    if (verifyAdmin(dao)) {
                        dao.viewPendingRequests();
                    }
                    break;
                case 10:
                    dao.viewAvailableSites();
                    break;
                case 11:
                    dao.viewAllOwners();
                    break;
                case 12:
                    return;

            }
        }
    }

    // 🔐 VERIFY ADMIN PASSWORD
    private static boolean verifyAdmin(AdminDAO dao) {

        System.out.print("Re-enter admin password: ");
        String pass = sc.next();

        if (dao.verifyAdminPassword(pass)) {
            return true;
        }

        System.out.println("Wrong password!");
        return false;
    }

    public static void createOwnerDirect() {

        Scanner sc = new Scanner(System.in);
        AdminDAO dao = new AdminDAO();

        System.out.print("Enter Admin Password: ");
        String pass = sc.next();

        if (!dao.verifyAdminPassword(pass)) {
            System.out.println("Wrong admin password!");
            return;
        }

        System.out.print("New Owner Username: ");
        String u = sc.next();

        System.out.print("New Owner Password: ");
        String p = sc.next();

        dao.addOwner(u, p);
    }

}
