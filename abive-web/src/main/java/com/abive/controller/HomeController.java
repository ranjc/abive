package com.abive.controller;


import com.abive.framework.controller.FrameController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ranjiangchuan on 15/3/25.
 */

@Controller
public class HomeController extends FrameController {

    @RequestMapping("")
    public ModelAndView home() {
        return getMv("home");
    }

}
