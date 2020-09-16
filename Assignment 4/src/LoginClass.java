import java.io.Serializable;

class LoginUser implements Serializable {
    String USERNAME;
    String PASSWORD; 
    LoginUser(){}
}

public class LoginClass {
    public String username,password;

    LoginClass(String username,String password) {
        this.username = username;
        this.password = password;
        System.out.println(this.username + " " + this.password);
    }

    public void attemptLogin(){
        if(this.username.equals("admin") && this.password.equals("password")) {
            System.out.println("Congrats you have logged in.\n");
        } else {
            System.out.println("Login Unsuccessfull\n");
        }
    }
}
