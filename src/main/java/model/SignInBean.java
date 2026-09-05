package model;

/**
 * 登录请求体对应的 JavaBean。
 * Jackson 通过无参构造和 setter 把 JSON 字段写入对象。
 */
public class SignInBean {
    private String username = "";
    private String password = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }
}
