package app.entities;

public class User {
    private int userId;
    private String userName;
    private String password;
    private int role;
    private String email;
    private String phonenumber;

    private int balance;

    public User(int userId, String userName, String password, int role, String email, String phonenumber, int balance) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phonenumber = phonenumber;
        this.balance = balance;
    }
    public User(int userId, String userName, String password, int role, String email, String phonenumber) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phonenumber = phonenumber;
        this.balance = 0;
    }
    public User(int id, String username, String password, int role, String email) {
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
