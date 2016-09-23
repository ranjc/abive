package com.abive.framework.controller;

import com.abive.framework.context.ServletContext;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;

/**
 * Created by ranjiangchuan on 15/3/28.
 */

public class FrameController implements Serializable {

    private static final long serialVersionUID = 4160350768515593766L;

    /**
     * 获取mv
     * @param path
     * @return
     */
    protected ModelAndView getMv(String path){

        ModelAndView mv = new ModelAndView(path);
        mv.addObject("identity", ServletContext.getIdentity());

        return mv;
    }

}
