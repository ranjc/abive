package com.abive.interceptor;

import com.abive.framework.config.AppConfig;
import com.abive.framework.context.Identity;
import com.abive.framework.context.ServletContext;
import com.abive.framework.security.DESComponent;
import com.abive.service.passport.PassportService;
import com.abive.util.IP;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ranjiangchuan on 15/3/28.
 */
public class FrameInterceptor extends HandlerInterceptorAdapter {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FrameInterceptor.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PassportService passportService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler == null || handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        ServletContext.initContext(request, response, applicationContext);
        ServletContext.set(ServletContext.IP, IP.getRemoteIP(request));

        initIdentity(request);

        return true;
    }

    /**
     * 获取并初始化登录的用户信息
     * @param request
     */
    private void initIdentity(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        if (null==cookies || cookies.length==0){
            return;
        }

        try {
            for (Cookie cookie : cookies){
                if (AppConfig.getAbive().getPassportCookieName()
                        .equals(cookie.getName())){

                    String json = DESComponent.decrypt(cookie.getValue());
                    Identity identity = new ObjectMapper().readValue(json,Identity.class);
                    ServletContext.set(ServletContext.IDENTITY,identity);

                    break;
                }
            }
        }catch (Exception e){
            logger.error("read cookie error : ",e);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ServletContext.remove();
    }
}
