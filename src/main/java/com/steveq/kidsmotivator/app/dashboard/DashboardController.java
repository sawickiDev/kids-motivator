package com.steveq.kidsmotivator.app.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String openDashboard() {
        return "dashboard";
    }

}
