package com.steveq.kidsmotivator.app.kids;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KidsController {

    @GetMapping("/kids")
    public ModelAndView showKids() {
        ModelAndView modelAndView = new ModelAndView("km-kids");
        return null;
    }

}
