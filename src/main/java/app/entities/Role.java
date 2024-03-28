package app.entities;

public class Role {
    private int roleId;
    private String rolename;

    public Role(int roleId, String rolename) {
        this.roleId = roleId;
        this.rolename = rolename;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRolename() {
        return rolename;
    }
}
