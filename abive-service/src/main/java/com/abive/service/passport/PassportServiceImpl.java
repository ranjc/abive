package com.abive.service.passport;

import com.abive.dao.passport.UserDao;
import com.abive.domain.passport.User;
import com.abive.dao.passport.UserQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ranjiangchuan on 15/3/29.
 */

@Service
public class PassportServiceImpl implements PassportService {

    @Autowired
    private UserDao userDao;

    @Override
    public User verify(String account, String password) {

        User verifyUser = null;

        if (StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)){
            UserQuery query = new UserQuery();
            query.setAccount(account);
            List<User> users = userDao.list(query);
            if (CollectionUtils.isNotEmpty(users)){
                if (password.equals(users.get(0).getPassword())){
                    verifyUser = users.get(0);
                }
            }
        }

        return verifyUser;
    }

    @Override
    public User findByAccount(String account) {

        if (StringUtils.isNotEmpty(account)){
            UserQuery query = new UserQuery();
            query.setAccount(account);
            List<User> users = userDao.list(query);
            if (CollectionUtils.isNotEmpty(users)){
                return users.get(0);
            }
        }

        return null;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
