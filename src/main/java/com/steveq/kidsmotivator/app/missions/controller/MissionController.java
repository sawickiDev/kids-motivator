package com.steveq.kidsmotivator.app.missions.controller;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.auth.service.UserService;
import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.missions.service.MissionService;
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
import java.util.List;

@Controller
public class MissionController {

    private MissionService missionService;
    private UserService userService;

    @Autowired
    public MissionController(MissionService missionService,
                             UserService userService) {
        this.missionService = missionService;
        this.userService = userService;
    }

    @GetMapping("/missions")
    public String openMissionPage (Model model) {
        User currentUser = userService.getCurrentlyLoggedUser();

        if(!model.containsAttribute("mission")){
            model.addAttribute("mission", new Mission());
        }

        List<Mission> missions = missionService.getAllByOwner(currentUser);
        model.addAttribute("kidsAvailable", userService.getActiveKidsForUser(currentUser));
        model.addAttribute("curUser", currentUser);
        model.addAttribute("stages", missionService.getStagesByUser(currentUser));
        model.addAttribute("allMissions", missions);

        return "missions";
    }

    @GetMapping("/delete-mission/{missionId}")
    public String deleteMission (@PathVariable String missionId) {
        missionService.deleteMissionById(Integer.valueOf(missionId));
        return "redirect:/missions";
    }

    @GetMapping("/update-mission/{missionId}")
    public RedirectView updateMission (@PathVariable String missionId,
                                       RedirectAttributes redirectAttributes) {
        Mission missionToUpdate = missionService.getMissionById(Integer.valueOf(missionId));
        redirectAttributes.addFlashAttribute("mission", missionToUpdate);

        return new RedirectView("/missions");
    }

    @PostMapping("/add-mission")
    public RedirectView addMission (@Valid @ModelAttribute("mission") Mission mission,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            // add this attributes in order to persist errors after redirection
            redirectAttributes.addFlashAttribute("mission", mission);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mission", bindingResult);

            return new RedirectView("/missions");
        }

        Mission savedMission = missionService.saveMission(mission);

        if (savedMission == null)
            redirectAttributes.addFlashAttribute("saveMissionError", true);

        return new RedirectView("missions");
    }

}
