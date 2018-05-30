package com.steveq.kidsmotivator.app.dashboard;

import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public ModelAndView openDashboard() {
        ModelAndView mav = new ModelAndView("dashboard");
        mav.addObject("kid", new User());
        return mav;
    }

    // TODO: Password Validation
    @PostMapping("/register-kid")
    public String registerKid(@Valid @ModelAttribute("kid") User kid,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            System.out.println("ERRORS");
            System.out.println(bindingResult);
            System.out.println(bindingResult.getAllErrors());

//            redirectAttributes.addFlashAttribute("kid", kid);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.kid", bindingResult);
//            return new RedirectView("dashboard");
            return "dashboard";
        }

        User savedUser = userService.saveUser(kid);

        if (savedUser == null) {
            System.out.println("COULDNT SAVE CONTACT");
        }

//        return new RedirectView("dashboard");
        return "dashboard";
    }

}
