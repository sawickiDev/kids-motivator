package com.steveq.kidsmotivator.app.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(name = "/dashboard")
    public String openDashboard() {
        return "dashboard";
    }

}
