package src;



import java.sql.*;

public class UserDAO {

    public User login(String username,String password){

        try(Connection con = DBUtil.getConnection()){

            String sql =
              "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("role")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
