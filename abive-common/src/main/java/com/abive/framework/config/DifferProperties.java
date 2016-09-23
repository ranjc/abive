package com.abive.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ranjiangchuan on 15/4/1.
 */
@Configuration
public class DifferProperties {

    private @Value("${abive.rsa.key.path}") String                  rsaKeyPath;
    private @Value("${abive.domain}") String                        domian;


    public DifferProperties(){
        AppConfig.putIfAbsent(AppConfig.Differ,this);
    }

    public String getRsaKeyPath() {
        return rsaKeyPath;
    }

    public void setRsaKeyPath(String rsaKeyPath) {
        this.rsaKeyPath = rsaKeyPath;
    }

    public String getDomian() {
        return domian;
    }

    public void setDomian(String domian) {
        this.domian = domian;
    }
}
