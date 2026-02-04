package src;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OwnerDAO  {
    public void viewMySite(int ownerId) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT site_number,property_type,site_status,length,width " +
                    "FROM sites WHERE owner_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ownerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        "Site: " + rs.getInt("site_number") +
                                "\nType: " + rs.getString("property_type") +
                                "\nStatus: " + rs.getString("site_status") +
                                "\nSize: " + rs.getInt("length") + "x" + rs.getInt("width"));
            } else {
                System.out.println("No site assigned!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMyMaintenance(int ownerId) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT m.total_amount,m.paid_amount,m.pending_amount,m.status " +
                    "FROM maintenance m JOIN sites s ON m.site_id=s.site_id " +
                    "WHERE s.owner_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ownerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        "Total: " + rs.getDouble("total_amount") +
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

    public void requestSiteUpdate(int ownerId, String status) {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "INSERT INTO site_update_requests(site_id,requested_status) " +
                    "SELECT site_id,? FROM sites WHERE owner_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, ownerId);

            ps.executeUpdate();

            System.out.println("Request sent to admin!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMyRequests(int ownerId) {

        try (Connection con = DBUtil.getConnection()) {

            // SITE REQUESTS
            System.out.println("\n=== My Site Requests ===");

            String siteSql = "SELECT r.req_id,s.site_number,r.requested_status,r.status " +
                    "FROM site_update_requests r " +
                    "JOIN sites s ON r.site_id=s.site_id " +
                    "WHERE s.owner_id=?";

            PreparedStatement ps = con.prepareStatement(siteSql);
            ps.setInt(1, ownerId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        "ReqID: " + rs.getInt("req_id") +
                                " | Site: " + rs.getInt("site_number") +
                                " | Requested: " + rs.getString("requested_status") +
                                " | Status: " + rs.getString("status"));
            }

            if (!found)
                System.out.println("No site requests.");

            // PASSWORD REQUESTS
            System.out.println("\n=== My Password Requests ===");

            String passSql = "SELECT req_id,new_password,status " +
                    "FROM password_requests WHERE user_id=?";

            PreparedStatement ps2 = con.prepareStatement(passSql);
            ps2.setInt(1, ownerId);

            ResultSet prs = ps2.executeQuery();

            found = false;

            while (prs.next()) {
                found = true;
                System.out.println(
                        "ReqID: " + prs.getInt("req_id") +
                                " | Status: " + prs.getString("status"));
            }

            if (!found)
                System.out.println("No password requests.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int ownerId, String oldPass, String newPass) {

        try (Connection con = DBUtil.getConnection()) {

            // verify old password
            String check = "SELECT * FROM users WHERE user_id=? AND password=?";

            PreparedStatement ps = con.prepareStatement(check);
            ps.setInt(1, ownerId);
            ps.setString(2, oldPass);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Old password incorrect!");
                return;
            }

            // update new password
            String upd = "UPDATE users SET password=? WHERE user_id=?";

            PreparedStatement ups = con.prepareStatement(upd);
            ups.setString(1, newPass);
            ups.setInt(2, ownerId);

            ups.executeUpdate();

            System.out.println("Password changed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAvailableSites() {

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT site_number,length,width FROM sites WHERE owner_id IS NULL";

            Statement st = con.createStatement();
            ResultSet rs =  st.executeQuery(sql);

            System.out.println("\nAvailable Sites:");

            while (rs.next()) {
                System.out.println(
                        "Site " + rs.getInt("site_number") +
                                " | " + rs.getInt("length") + "x" + rs.getInt("width"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerOwner(String u, String p, int siteNo, int typeChoice) {

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

            con.setAutoCommit(false);

            // create owner
            String userSql = "INSERT INTO users(username,password,role) VALUES(?,?,'OWNER') RETURNING user_id";

            PreparedStatement ps = con.prepareStatement(userSql);

            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Signup failed!");
                return;
            }

            int ownerId = rs.getInt(1);

            // assign site if available
            String upd = "UPDATE sites SET owner_id=?,property_type=?,site_status='OCCUPIED' " +
                    "WHERE site_number=? AND owner_id IS NULL";

            PreparedStatement ups = con.prepareStatement(upd);

            ups.setInt(1, ownerId);
            ups.setString(2, type);
            ups.setInt(3, siteNo);

            if (ups.executeUpdate() == 0) {
                System.out.println("Site already taken!");
                con.rollback();
                return;
            }

            con.commit();

            System.out.println("Signup successful & site assigned!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
