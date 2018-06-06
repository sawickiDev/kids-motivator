package com.steveq.kidsmotivator.app.dashboard.controller;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class DashboardController {

    private UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String openDashboard(Model model) {

        // initialize form model attribute
        if(!model.containsAttribute("kid")){
            model.addAttribute("kid", new User());
        }

        model.addAttribute("kidsList", userService.getKidsForUser(userService.getCurrentlyLoggedUser()));

        return "dashboard";
    }

    @GetMapping("/toggle-kid-active/{kidId}")
    public String toggleKidActive(@PathVariable("kidId") String kidId) {
        System.out.println("TOGGLE ACTIVE :: " + kidId);
        User kid = userService.getUserById(Integer.valueOf(kidId));
        kid.setActive(!kid.getActive());
        userService.saveUser(kid);

        return "redirect:/dashboard";
    }

    @PostMapping("/register-kid")
    public RedirectView registerKid(@Valid @ModelAttribute("kid") User kid,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            // add this attributes to persist errors after redirection
            redirectAttributes.addFlashAttribute("kid", kid);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.kid", bindingResult);

            return new RedirectView("dashboard");
        }

        User savedUser = userService.saveUser(kid);

        // show error on the page if couldn't create user
        if (savedUser == null)
            redirectAttributes.addFlashAttribute("saveKidError", true);

        return new RedirectView("dashboard");
    }

}
