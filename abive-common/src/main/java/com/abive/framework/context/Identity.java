package com.abive.framework.context;

import com.abive.domain.passport.User;
import com.abive.util.JsonUtil;

/**
 * Created by ranjiangchuan on 15/4/5.
 */
public class Identity {

    private String account;
    private String name;

    public Identity(){

    }

    public Identity(String account){
        this.account=account;
    }

    public Identity(String account , String name){
        this.account=account;
        this.name=name;
    }

    public Identity(User user){
        this.account=user.getAccount();
        this.name=user.getName();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return JsonUtil.objectToJson(this);
    }
}
