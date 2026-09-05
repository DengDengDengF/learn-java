package model;

/** 保存在 HttpSession 中的当前用户信息。 */
public class User {
    private final String username;
    private final boolean manager;

    public User(String username, boolean manager) {
        this.username = username;
        this.manager = manager;
    }

    public String getUsername() {
        return username;
    }

    public boolean isManager() {
        return manager;
    }
}
