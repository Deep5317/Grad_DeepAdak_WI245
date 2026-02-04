package src;

import java.sql.*;
import java.util.Scanner;

public class AdminDAO {
    public void viewAllOwners(){

    try(Connection con = DBUtil.getConnection()){

        String sql =
        "SELECT user_id,username FROM users WHERE role='OWNER'";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("\n=== OWNERS ===");

        while(rs.next()){
            System.out.println(
              "ID: "+rs.getInt("user_id")+
              " | Username: "+rs.getString("username")
            );
        }

    }catch(Exception e){
        e.printStackTrace();
    }
}

    // ADD OWNER
    public void addOwner(String username, String password) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "INSERT INTO users(username,password,role) VALUES(?,?, 'OWNER')";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            System.out.println("Owner added!");

        } catch (Exception e) {
            System.out.println("Username already exists!");
        }
    }

    // VIEW AVAILABLE SITES
    public void viewAvailableSites() {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT site_number,length,width FROM sites WHERE owner_id IS NULL";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nAvailable Sites:");

            while (rs.next()) {
                System.out.println(
                        "Site " + rs.getInt("site_number") +
                                " | Size: " +
                                rs.getInt("length") + "x" + rs.getInt("width"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyAdminPassword(String password) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT * FROM users WHERE role='ADMIN' AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void editOwner(int id, String username, String password) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "UPDATE users SET username=?,password=? WHERE user_id=? AND role='OWNER'";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Owner updated!");
            else
                System.out.println("Owner not found!");

        } catch (Exception e) {
            System.out.println("Username already exists!");
        }
    }

    public void removeOwner(int ownerId) {

        try (Connection con = DBUtil.getConnection()) {

            // free site first
            String freeSite = "UPDATE sites SET owner_id=NULL, site_status='OPEN' WHERE owner_id=?";

            PreparedStatement ps1 = con.prepareStatement(freeSite);
            ps1.setInt(1, ownerId);
            ps1.executeUpdate();

            // delete owner
            String del = "DELETE FROM users WHERE user_id=? AND role='OWNER'";

            PreparedStatement ps2 = con.prepareStatement(del);
            ps2.setInt(1, ownerId);

            int rows = ps2.executeUpdate();

            if (rows > 0)
                System.out.println("Owner removed & site freed!");
            else
                System.out.println("Owner not found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignSite(int ownerId, int siteNo, int typeChoice) {

        String type = "OpenSite";

        switch (typeChoice) {
            case 1:
                type = "Villa";
                break;
            case 2:
                type = "Apartment";
                break;
            case 3:
                type = "Independent";
                break;
        }

        try (Connection con = DBUtil.getConnection()) {

            // check site availability
            String check = "SELECT * FROM sites WHERE site_number=? AND owner_id IS NULL";

            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setInt(1, siteNo);

            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                System.out.println("Site not available!");
                return;
            }

            // assign
            String sql = "UPDATE sites SET owner_id=?, property_type=?, site_status='OCCUPIED' WHERE site_number=?";

            PreparedStatement ps2 = con.prepareStatement(sql);

            ps2.setInt(1, ownerId);
            ps2.setString(2, type);
            ps2.setInt(3, siteNo);

            ps2.executeUpdate();

            System.out.println("Site assigned successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collectMaintenance(int siteNo, double payAmount) {

        try (Connection con = DBUtil.getConnection()) {

            // Get site info
            String siteSql = "SELECT site_id,length,width,site_status FROM sites WHERE site_number=?";

            PreparedStatement ps = con.prepareStatement(siteSql);
            ps.setInt(1, siteNo);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid site!");
                return;
            }

            int siteId = rs.getInt("site_id");
            int area = rs.getInt("length") * rs.getInt("width");

            String status = rs.getString("site_status");

            int rate = status.equals("OPEN") ? 6 : 9;

            double total = area * rate;

            // Check existing maintenance
            String check = "SELECT * FROM maintenance WHERE site_id=?";

            PreparedStatement ps2 = con.prepareStatement(check);
            ps2.setInt(1, siteId);

            ResultSet mrs = ps2.executeQuery();

            if (mrs.next()) {

                double paid = mrs.getDouble("paid_amount");
                double newPaid = paid + payAmount;

                if (newPaid > total) {
                    System.out.println("Overpayment not allowed!");
                    return;
                }
                double pending = total - newPaid;
               

                String mStatus = "PARTIAL";
                if (newPaid == 0)
                    mStatus = "NOT PAID";
                else if (pending == 0)
                    mStatus = "FULLY PAID";

                String upd = "UPDATE maintenance SET paid_amount=?,status=? WHERE site_id=?";

                PreparedStatement ups = con.prepareStatement(upd);

                ups.setDouble(1, newPaid);
                ups.setString(2, mStatus);
                ups.setInt(3, siteId);

                ups.executeUpdate();

            } else {

                double pending = total - payAmount;

                String mStatus = "PARTIAL";
                if (payAmount == 0)
                    mStatus = "NOT PAID";
                else if (pending == 0)
                    mStatus = "FULLY PAID";

                String ins = "INSERT INTO maintenance(site_id,total_amount,paid_amount,status) VALUES(?,?,?,?)";

                PreparedStatement ips = con.prepareStatement(ins);

                ips.setInt(1, siteId);
                ips.setDouble(2, total);
                ips.setDouble(3, payAmount);
                
                ips.setString(4, mStatus);

                ips.executeUpdate();
            }

            System.out.println("Maintenance updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllMaintenance() {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT s.site_number,m.total_amount,m.paid_amount,m.pending_amount,m.status " +
                    "FROM maintenance m JOIN sites s ON m.site_id=s.site_id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n=== Maintenance Details ===");

            while (rs.next()) {
                System.out.println(
                        "Site " + rs.getInt("site_number") +
                                " | Total: " + rs.getDouble("total_amount") +
                                " | Paid: " + rs.getDouble("paid_amount") +
                                " | Pending: " + rs.getDouble("pending_amount") +
                                " | Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMaintenanceBySite(int siteNo) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT s.site_number,m.total_amount,m.paid_amount,m.pending_amount,m.status " +
                    "FROM maintenance m JOIN sites s ON m.site_id=s.site_id " +
                    "WHERE s.site_number=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, siteNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        "Site " + rs.getInt("site_number") +
                                "\nTotal: " + rs.getDouble("total_amount") +
                                "\nPaid: " + rs.getDouble("paid_amount") +
                                "\nPending: " + rs.getDouble("pending_amount") +
                                "\nStatus: " + rs.getString("status"));
            } else {
                System.out.println("No maintenance record!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSiteRequests() {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT r.req_id,s.site_number,r.requested_status " +
                    "FROM site_update_requests r " +
                    "JOIN sites s ON r.site_id=s.site_id " +
                    "WHERE r.status='PENDING'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nPending Site Requests:");

            while (rs.next()) {
                System.out.println(
                        "ReqID: " + rs.getInt("req_id") +
                                " | Site: " + rs.getInt("site_number") +
                                " | New Status: " + rs.getString("requested_status"));
            }

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Request ID: ");
            int rid = sc.nextInt();

            System.out.print("1 Approve / 2 Reject: ");
            int ch = sc.nextInt();

            if (ch == 1) {

                String updSite = "UPDATE sites SET site_status=(" +
                        "SELECT requested_status FROM site_update_requests WHERE req_id=?) " +
                        "WHERE site_id=(" +
                        "SELECT site_id FROM site_update_requests WHERE req_id=?)";

                PreparedStatement ps = con.prepareStatement(updSite);
                ps.setInt(1, rid);
                ps.setInt(2, rid);
                ps.executeUpdate();

                PreparedStatement ps2 = con
                        .prepareStatement("UPDATE site_update_requests SET status='APPROVED' WHERE req_id=?");
                ps2.setInt(1, rid);
                ps2.executeUpdate();

                System.out.println("Approved!");

            } else {

                PreparedStatement ps = con
                        .prepareStatement("UPDATE site_update_requests SET status='REJECTED' WHERE req_id=?");
                ps.setInt(1, rid);
                ps.executeUpdate();

                System.out.println("Rejected!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void viewPendingRequests() {

        try (Connection con = DBUtil.getConnection()) {

            System.out.println("\n=== SITE UPDATE REQUESTS ===");

            String siteSql = "SELECT r.req_id,s.site_number,r.requested_status " +
                    "FROM site_update_requests r " +
                    "JOIN sites s ON r.site_id=s.site_id " +
                    "WHERE r.status='PENDING'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(siteSql);

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        "ReqID: " + rs.getInt("req_id") +
                                " | Site: " + rs.getInt("site_number") +
                                " | Requested: " + rs.getString("requested_status"));
            }

            if (!found)
                System.out.println("No pending site requests.");

            // PASSWORD REQUESTS
            System.out.println("\n=== PASSWORD REQUESTS ===");

            String passSql = "SELECT req_id,user_id FROM password_requests WHERE status='PENDING'";

            ResultSet prs = st.executeQuery(passSql);

            found = false;

            while (prs.next()) {
                found = true;
                System.out.println(
                        "ReqID: " + prs.getInt("req_id") +
                                " | UserID: " + prs.getInt("user_id"));
            }

            if (!found)
                System.out.println("No pending password requests.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
