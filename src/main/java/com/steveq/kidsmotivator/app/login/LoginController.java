package com.steveq.kidsmotivator.app.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/km-login")
    public String openLoginPage() {
        return "km-login";
    }
}
