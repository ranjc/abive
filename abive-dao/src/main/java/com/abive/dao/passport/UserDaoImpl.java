package com.abive.dao.passport;

import com.abive.dao.BaseDaoImpl;
import com.abive.dao.passport.UserDao;
import com.abive.domain.passport.User;
import org.springframework.stereotype.Repository;

/**
 * Created by ranjiangchuan on 15/3/29.
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
}
