package com.github.chimmhuang.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Database connection configuration
 * 数据库连接配置
 *
 * @author Chimm Huang
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
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

}
