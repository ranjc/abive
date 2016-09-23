package com.abive.service.passport;

import com.abive.domain.passport.User;

/**
 * Created by ranjiangchuan on 15/3/29.
 */
public interface PassportService {

    /**
     * 根据账号密码校验
     * @param account 账号
     * @param password 密码
     * @return
     */
    public User verify(String account, String password);

    /**
     * 根据账号获取用户
     * @param account 账号
     * @return
     */
    public User findByAccount(String account);

}
