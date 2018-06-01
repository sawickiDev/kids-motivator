package com.steveq.kidsmotivator.app.missions.controller;

import com.steveq.kidsmotivator.app.missions.dao.MissionRepository;
import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.missions.service.MissionService;
import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
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
import java.util.List;

@Controller
public class MissionController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/missions")
    public String openMissionPage (Model model) {

        if(!model.containsAttribute("mission")){
            model.addAttribute("mission", new Mission());
        }

        List<Mission> missions = missionRepository.findAllByOwner(userService.getCurrentlyLoggedUser());

        if (missions != null
                && !missions.isEmpty()) {
            model.addAttribute("allMissions", missions);
        }

        return "missions";
    }

    @GetMapping("/delete-mission")
    public String deleteMission (@PathVariable String missionId) {



        return "missions";
    }

    @PostMapping("/add-mission")
    public RedirectView addMission (@Valid @ModelAttribute("mission") Mission mission,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        System.out.println("ADD MISSION");

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("mission", mission);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mission", bindingResult);

            return new RedirectView("/missions");
        }
        System.out.println("ADD MISSION");
        Mission savedMission = missionService.saveMission(mission);

        if (savedMission == null)
            redirectAttributes.addFlashAttribute("saveMissionError", true);

        return new RedirectView("missions");
    }

}
