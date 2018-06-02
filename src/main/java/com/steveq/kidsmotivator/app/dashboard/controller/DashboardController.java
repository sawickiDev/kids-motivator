package com.steveq.kidsmotivator.app.dashboard.controller;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.missions.service.MissionService;
import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private MissionService missionService;

    @GetMapping("/dashboard")
    public String openDashboard(Model model) {

        if(!model.containsAttribute("kid")){
            model.addAttribute("kid", new User());
        }

        List<User> kids = userService.getKidsForParent();

        if (kids != null
            && !kids.isEmpty()) {
                model.addAttribute("kidsList", kids);
        }

        return "dashboard";
    }

    @PostMapping("/register-kid")
    public RedirectView registerKid(@Valid @ModelAttribute("kid") User kid,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    SessionStatus sessionStatus) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("kid", kid);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.kid", bindingResult);

            return new RedirectView("dashboard");
        }

        User savedUser = userService.saveUser(kid);

        if (savedUser == null)
            redirectAttributes.addFlashAttribute("saveKidError", true);

        sessionStatus.setComplete();

        return new RedirectView("dashboard");
    }

}
