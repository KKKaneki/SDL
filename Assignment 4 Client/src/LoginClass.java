import java.io.Serializable;

class LoginUser implements Serializable {
    String USERNAME;
    String PASSWORD; 
    LoginUser(){}
}

public class LoginClass {
    public LoginUser user;

    LoginClass(String username,String password) {
        user = new LoginUser();
        user.USERNAME = username;
        user.PASSWORD = password;
    }

    public void attemptLogin(){

        // GET THE LOGIN
        Login.getLoginMenu(user);
        System.out.println(user.PASSWORD + " " + user.USERNAME);
        if(Login.isLoggedIn) {
            if(Login.whoIsLogged.get(0)) {
                System.out.println("ADMIN has logged in.\n");
            } else {
                System.out.println("Staff has logged in.\n");
            }
        } else {
            System.out.println("Login Unsuccessfull\n");
        }
    }
}
