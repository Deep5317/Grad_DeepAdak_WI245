package src;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginService ls = new LoginService();

        while (true) {

            System.out.println("\n=== LAYOUT MANAGEMENT ===");
            System.out.println("1. Login");
            // System.out.println("2. Create Owner");
            System.out.println("2. Exit");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    System.out.print("Username: ");
                    String u = sc.next();

                    System.out.print("Password: ");
                    String p = sc.next();

                    User user = ls.login(u, p);

                    if (user == null) {
                        System.out.println("Invalid login!");
                        break;
                    }

                    if (user.getRole().equals("ADMIN"))
                        AdminService.adminMenu();
                    else{
                        System.out.println("Welcome, " + user.getUsername() + "!"  +"Your ID is: " + user.getId()   );
                        OwnerService.ownerMenu(user.getId());
                    }
                    break;

                case 2:
                    // OwnerService.signup();
                    return;

                
            }
        }
    }
}
