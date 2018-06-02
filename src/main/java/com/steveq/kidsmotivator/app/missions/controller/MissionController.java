package com.steveq.kidsmotivator.app.missions.controller;

import com.steveq.kidsmotivator.app.missions.dao.MissionRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
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
        User currentUser = userService.getCurrentlyLoggedUser();

        if(!model.containsAttribute("mission")){
            model.addAttribute("mission", new Mission());
        }
        List<Mission> missions = new ArrayList<>();

        if (userService.isUserParent(currentUser)) {
            missions = missionRepository.findAllByOwner(userService.getCurrentlyLoggedUser());
        } else if (userService.isUserKid(currentUser)) {
            List<String> containingStages = new ArrayList<>();
            containingStages.add("ASSIGNED");
            containingStages.add("OVERDUE");
            missions = missionRepository.findAllByAssignedKidAndStageIn(currentUser, containingStages);
            model.addAttribute("curUser", currentUser);
        }

        List<String> stages = new ArrayList<>();

        for (Mission.STAGE stage : Mission.STAGE.values()){

            if (userService.isUserKid(currentUser)
                && "open".equals(stage.name().toLowerCase())) {
                continue;
            }
            stages.add(stage.name());
        }

        System.out.println("STAGES :: " + stages);

        model.addAttribute("stages", stages);
        model.addAttribute("kidsAvailable", userService.getKidsForParent());

        if (missions != null
                && !missions.isEmpty()) {
            model.addAttribute("allMissions", missions);
        }

        return "missions";
    }

    @GetMapping("/delete-mission/{missionId}")
    public RedirectView deleteMission (@PathVariable String missionId) {
        System.out.println("MISSION ID :: " + missionId);
        missionService.deleteMissionById(Integer.valueOf(missionId));

        return new RedirectView("/missions");
    }

    @GetMapping("/update-mission/{missionId}")
    public RedirectView updateMission (@PathVariable String missionId,
                                       RedirectAttributes redirectAttributes) {
        Mission missionToUpdate = missionRepository.findFirstById(Integer.valueOf(missionId));

        redirectAttributes.addFlashAttribute("mission", missionToUpdate);

        return new RedirectView("/missions");
    }

    @PostMapping("/add-mission")
    public RedirectView addMission (@Valid @ModelAttribute("mission") Mission mission,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("mission", mission);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mission", bindingResult);

            return new RedirectView("/missions");
        }

        System.out.println("SAAAVE MISSION :: " + mission);

        Mission savedMission = missionService.saveMission(mission);

        if (savedMission == null)
            redirectAttributes.addFlashAttribute("saveMissionError", true);

        return new RedirectView("missions");
    }

}
