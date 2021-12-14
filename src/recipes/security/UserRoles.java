package recipes.security;

public enum UserRoles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String text;

    UserRoles(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}