package com.abive.controller.passport;

import com.abive.framework.controller.FrameController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ranjiangchuan on 15/4/3.
 */
@Controller
public class RegisterController extends FrameController {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterController.class);


    @RequestMapping("register")
    public ModelAndView register(){
        ModelAndView mv = getMv("passport/register");
        return mv;
    }

}
