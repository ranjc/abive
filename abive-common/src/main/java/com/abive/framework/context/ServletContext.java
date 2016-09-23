package com.abive.framework.context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abive.domain.passport.User;

/**
 * 在一个请求的生命周期可用，在拦截器注入，
 * @author <a href="mailto:wanghaiinfo@jd.com">wanghai</a>
 */
public class ServletContext {

    private HttpServletResponse response;
    private ApplicationContext  applicationContext;
    private HttpServletRequest  request;

    private ServletContext() {

    }

    public static final Identity getIdentity() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (Identity) request.getAttribute(IDENTITY);
    }

    public static final ServletContext getServletContext() {
        ServletContext context = local.get();
        return context;
    }

    public static final HttpServletRequest getRequest() {
        ServletContext context = getServletContext();
        if (context == null) {
            return null;
        }
        return (HttpServletRequest) context.request;
    }

    public static final HttpServletResponse getResponse() {
        ServletContext context = getServletContext();
        if (context == null) {
            return null;
        }
        return context.response;
    }

    public static final ApplicationContext getApplicationContext() {
        ServletContext context = getServletContext();
        if (context == null) {
            return null;
        }
        return context.applicationContext;
    }

    public static final String getContextPath() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (String) request.getContextPath();
    }

    public static final String getRequestURI() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (String) request.getRequestURI();
    }

    public static final String getIP() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String ip = null;
        try {
            ip = (String) request.getAttribute(IP);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ip;
    }

    private static Locale getLocale() {
        HttpServletRequest req = ServletContext.getRequest();
        Locale locale = null;
        if (req == null) {
            locale = Locale.getDefault();
        } else {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(req);
            if (localeResolver != null)
                locale = localeResolver.resolveLocale(req);
            else
                locale = req.getLocale();
        }
        return locale;
    }

    public static Map<String, Object> getRootBySpringApplicationContextAsIdMap() {
        lock.lock();
        try {
            if (root == null) {
                ApplicationContext context = getApplicationContext();
                String[] names = context.getBeanDefinitionNames();
                root = new HashMap<String, Object>();
                for (String str : names) {
                    Object bean = context.getBean(str);
                    root.put(str, bean);
                }
            }
        } finally {
            lock.unlock();
        }
        return root;
    }

    public static final void remove() {
        local.remove();
    }

    public static final void initContext(HttpServletRequest request, HttpServletResponse response,
                                         ApplicationContext applicationContext) {
        ServletContext context = new ServletContext();
        context.request = request;
        context.response = response;
        context.applicationContext = applicationContext;
        local.set(context);
    }

    public static final void set(String key, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(key, value);
        }
    }

    private static final ThreadLocal<ServletContext> local         = new ThreadLocal<ServletContext>();
    private static Map<String, Object>               root;
    private static final Lock                        lock          = new ReentrantLock();
    public static final String                       IDENTITY          = "framework.servlet.identity";
    public static final String                       RESPONSE      = "framework.servlet.response";
    public static final String                       CONTEXT       = "framework.servlet.context.Path";
    public static final String                       URI           = "framework.servlet.request.uri";
    public static final String                       IP            = "framework.servlet.request.remoteip";
    public static final String                       FUNS          = "framework.servlet.request.funs";
    public static final String                       REQUEST_START = "framework.servlet.request.start_time";

}
