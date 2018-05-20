package com.steveq.kidsmotivator.app.login;

import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/km-login")
    public String getLoginPage() {
        return "km-login";
    }

}
