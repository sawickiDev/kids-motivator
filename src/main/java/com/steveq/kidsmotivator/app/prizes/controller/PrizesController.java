package com.steveq.kidsmotivator.app.prizes.controller;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.auth.service.UserService;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import com.steveq.kidsmotivator.app.prizes.service.PrizesService;
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
public class PrizesController {

    private PrizesService prizesService;
    private UserService userService;

    public PrizesController(PrizesService prizesService, UserService userService) {
        this.prizesService = prizesService;
        this.userService = userService;
    }

    @GetMapping("/prizes")
    public String openPrizesPage (Model model) {
        User currentUser = userService.getCurrentlyLoggedUser();

        if(!model.containsAttribute("prize")){
            model.addAttribute("prize", new Prize());
        }

        model.addAttribute("prizesAvailable", prizesService.getPrizesAvailable(currentUser));
        model.addAttribute("prizesTaken", prizesService.getPrizesTaken(currentUser));

        return "prizes";
    }

    @PostMapping("/add-prize")
    public RedirectView addPrize(@Valid @ModelAttribute("prize") Prize prize,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("prize", prize);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.prize", bindingResult);

            return new RedirectView("prizes");
        }

        Prize savedPrize = prizesService.savePrize(prize);

        // check if saved or null object returned
        if (savedPrize.getName() == null)
            redirectAttributes.addFlashAttribute("savePrizeError", true);

        return new RedirectView("prizes");
    }

    @GetMapping("/delete-prize/{prizeId}")
    public RedirectView deletePrize (@PathVariable String prizeId) {
        prizesService.deletePrizeById(Integer.valueOf(prizeId));

        return new RedirectView("/prizes");
    }

    @GetMapping("/update-prize/{prizeId}")
    public RedirectView updateMission (@PathVariable String prizeId,
                                       RedirectAttributes redirectAttributes) {
        Prize prizeToUpdate = prizesService.findById(Integer.valueOf(prizeId));
        redirectAttributes.addFlashAttribute("prize", prizeToUpdate);

        return new RedirectView("/prizes");
    }
}
