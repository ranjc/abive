package com.abive.controller.passport;

import com.abive.domain.passport.User;
import com.abive.framework.config.AppConfig;
import com.abive.framework.context.Identity;
import com.abive.framework.context.ServletContext;
import com.abive.framework.controller.FrameController;
import com.abive.framework.security.DESComponent;
import com.abive.util.MD5;
import com.abive.framework.security.RSAComponent;
import com.abive.framework.view.Result;
import com.abive.service.passport.PassportService;
import com.abive.util.security.DES;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by ranjiangchuan on 15/3/28.
 */

@Controller
public class LoginController extends FrameController {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);


    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_INFO_ERROR = "账号或密码错误";
    public static final String LOGIN_INPUT_ERROR = "账号或密码错误";

    @Autowired
    private PassportService passportService;

    @RequestMapping("login")
    public ModelAndView home(@RequestParam(required=false) String forward) {
        ModelAndView mv = getMv("passport/login");
        if (StringUtils.isEmpty(forward)){
            forward = AppConfig.getDiffer().getDomian();
        }
        mv.addObject("forward", forward);

        /**
         * 公钥密钥
         */
        RSAPublicKey publicKey = RSAComponent.getPublicKey();
        mv.addObject("rsaModulus", publicKey.getModulus().toString(16));
        mv.addObject("rsaExponent", publicKey.getPublicExponent().toString(16));

        return mv;
    }

    @RequestMapping(value = "login/do.json",method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Result doLogin(@RequestParam String account,@RequestParam String password){

        Result result;

        //账号密码不能为空
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            result = new Result(Result.ERROR,LOGIN_INPUT_ERROR);
        }else {

            try {
                //去前后空格后，用RSA私钥解密，再用MD5摘要算法计算
                String pwd = MD5.parse(RSAComponent.decryptString(password.trim()));

                User user = passportService.findByAccount(account);

                if (user!=null && pwd.equals(user.getPassword())){

                    Identity identity = new Identity(user);
                    writePassportCookie(identity);

                    result = new Result(LOGIN_SUCCESS);
                }else {
                    result = new Result(Result.ERROR,LOGIN_INFO_ERROR);
                }
            } catch (Exception e) {

                logger.error("check password error",e);
                result = new Result(Result.ERROR,LOGIN_INFO_ERROR);

            }

        }
        return result;
    }

    @RequestMapping("logout")
    public ModelAndView logout(){
        cleanPassportCookie();
        return null;
    }

    /**
     * 写入cookie
     * @param identity
     */
    private void writePassportCookie(Identity identity){

        HttpServletResponse response = ServletContext.getResponse();

        try {
            String cookieValue = DESComponent.encrypt(identity.toString());

            Cookie cookie = new Cookie(AppConfig.getAbive().getPassportCookieName(),cookieValue);

            cookie.setMaxAge(259200);
            cookie.setPath("/");
            cookie.setDomain(AppConfig.getDiffer().getDomian());

            response.addCookie(cookie);
        }catch (Exception e){
            logger.error("write cookie error : ",e);
        }

    }

    /**
     * 清除cookie
     */
    private void cleanPassportCookie(){
        Cookie cookie = new Cookie(AppConfig.getAbive().getPassportCookieName(),"");
        cookie.setMaxAge(0);

        ServletContext.getResponse().addCookie(cookie);
    }

    public PassportService getPassportService() {
        return passportService;
    }

    public void setPassportService(PassportService passportService) {
        this.passportService = passportService;
    }
}
