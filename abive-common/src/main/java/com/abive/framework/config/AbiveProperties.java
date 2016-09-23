package com.abive.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ranjiangchuan on 15/4/1.
 */
@Configuration
public class AbiveProperties {

    private @Value("${jdbc.driver}") String       jdbcDriver;
    private @Value("${jdbc.url}") String          jdbcUrl;
    private @Value("${jdbc.username}") String     jdbcUsername;
    private @Value("${jdbc.password}") String     jdbcPassword;

    private @Value("${abive.passport.cookie.name}") String          passportCookieName;
    private @Value("${abive.des.key}") String                       desKey;

    public AbiveProperties(){
        AppConfig.putIfAbsent(AppConfig.Abive,this);
    };

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getPassportCookieName() {
        return passportCookieName;
    }

    public void setPassportCookieName(String passportCookieName) {
        this.passportCookieName = passportCookieName;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }
}
