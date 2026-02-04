package src;


public class LoginService {

    UserDAO dao = new UserDAO();

    public User login(String u,String p){
        return dao.login(u,p);
    }
}
