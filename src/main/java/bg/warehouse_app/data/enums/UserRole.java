package bg.warehouse_app.data.enums;

public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MODERATOR("ROLE_MODERATOR");

    private final String role;
    UserRole(String role) {
        this.role = role;
    }
    public String getRoleToString() {
        return role;
    }
}
