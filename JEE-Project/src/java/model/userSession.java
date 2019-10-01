package model;
public class userSession {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String Login) {
        this.login = Login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }
    
    public boolean equals(userSession u)
    {
        return this.login.equals(u.login) && this.password.equals(u.password);
    }
}
