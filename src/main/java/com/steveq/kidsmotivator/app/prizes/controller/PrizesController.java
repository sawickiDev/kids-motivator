package com.steveq.kidsmotivator.app.prizes.controller;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import com.steveq.kidsmotivator.app.prizes.service.PrizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PrizesController {

    @Autowired
    private PrizesService prizesService;

    @Autowired
    private UserService userService;


    @GetMapping("/prizes")
    public String openPrizesPage (Model model) {
        User currentlyLogged = userService.getCurrentlyLoggedUser();

        if(!model.containsAttribute("prize")){
            model.addAttribute("prize", new Prize());
        }

        List<Prize> prizesAvailable = new ArrayList<>();
        List<Prize> prizesTaken = new ArrayList<>();

        if (userService.isUserParent(currentlyLogged)) {
            Set<User> users = new HashSet<>();
            users.add(currentlyLogged);
            prizesAvailable = prizesService.getPrizesAvailableByOwner(users);
            prizesTaken = prizesService.getPrizesTakenByOwner(users);
            model.addAttribute("kidsAvailable", userService.getKidsForParent());
        }
        else if (userService.isUserKid(currentlyLogged)) {
            prizesAvailable = prizesService.getPrizesAvailableByOwner(currentlyLogged.getParents());
            prizesTaken = prizesService.getPrizesByAssignedKid(currentlyLogged);
            List<User> availableUser = new ArrayList<>();
            availableUser.add(currentlyLogged);
            model.addAttribute("kidsAvailable", availableUser);
        }

        model.addAttribute("prizesAvailable", prizesAvailable);
        model.addAttribute("prizesTaken", prizesTaken);

        return "prizes";
    }

    @PostMapping("/add-prize")
    public RedirectView addPrize(@Valid @ModelAttribute("prize") Prize prize,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("SAVE PRIZE :: " + prize);
        User currentlyLogged = userService.getCurrentlyLoggedUser();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("prize", prize);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.prize", bindingResult);

            return new RedirectView("prizes");
        }

        if (userService.isUserKid(currentlyLogged)) {
            if (currentlyLogged.getSumPoints() < prize.getValue()){
                return new RedirectView("prizes");
            }
        }

        System.out.println("SAVE PRIZE :: " + prize);

        Prize savedPrize = prizesService.savePrize(prize);

        if (savedPrize == null)
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

        System.out.println("PRIZE TO UPDATE :: " + prizeToUpdate);
        redirectAttributes.addFlashAttribute("prize", prizeToUpdate);

        return new RedirectView("/prizes");
    }
}
