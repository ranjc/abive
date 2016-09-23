package com.abive.framework.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ranjiangchuan on 15/4/1.
 */
public class AppConfig {

    static final String Abive = "Abive";
    static final String Differ = "Differ";

    static Map<String, Object> configMap = new HashMap<String, Object>();

    static void putIfAbsent(String key, Object object) {
        synchronized (configMap) {
            if (!configMap.containsKey(key)) {
                configMap.put(key, object);
            }
        }

    }

    public static AbiveProperties getAbive() {
        return (AbiveProperties)configMap.get(Abive);
    }

    public static DifferProperties getDiffer() {
        return (DifferProperties)configMap.get(Differ);
    }
}
