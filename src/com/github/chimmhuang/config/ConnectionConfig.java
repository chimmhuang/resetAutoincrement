package com.github.chimmhuang.config;

/**
 * Database connection configuration
 * 数据库连接配置
 *
 * @author Chimm Huang
 */
public class ConnectionConfig {

    private static ConnectionConfig instance;

    private String classDriver;
    private String url;
    private String username;
    private String password;


    /**
     * Configuration information.
     * Custom configuration is required here.
     * 此处需要自定义配置
     */
    public static ConnectionConfig getInstance() {
        if (instance == null) {
            instance = ConnectionConfig.builder()
                    .classDriver("com.mysql.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/test")
                    .username("root")
                    .password("123456")
                    .build();
        }

        return instance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ConnectionConfig connectionConfig = new ConnectionConfig();

        public Builder classDriver(String classDriver) {
            connectionConfig.setClassDriver(classDriver);
            return this;
        }

        public Builder url(String url) {
            connectionConfig.setUrl(url);
            return this;
        }

        public Builder username(String username) {
            connectionConfig.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            connectionConfig.setPassword(password);
            return this;
        }

        public ConnectionConfig build() {
            return connectionConfig;
        }

    }

    public String getClassDriver() {
        return classDriver;
    }

    public void setClassDriver(String classDriver) {
        this.classDriver = classDriver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
