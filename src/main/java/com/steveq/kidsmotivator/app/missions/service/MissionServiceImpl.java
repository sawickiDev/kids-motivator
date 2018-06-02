package com.steveq.kidsmotivator.app.missions.service;

import com.steveq.kidsmotivator.app.missions.dao.MissionRepository;
import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserService userService;

    @Override
    public Mission saveMission(Mission mission) {
        Mission persistedMission = null;

        System.out.println("MISSION TO UPDATE :: " + mission);

        if (mission.getStage().isEmpty())
            mission.setStage(Mission.STAGE.OPEN.name());

        if (mission.getOwner() == null)
            mission.setOwner(userService.getCurrentlyLoggedUser());

        System.out.println("ASSIGNED ID");

        if (mission.getAssignedId() >= 0
            && mission.getAssignedKid() == null) {
            mission.setAssignedKid(userService.getUserById(mission.getAssignedId()));
        }

        try{
            persistedMission = missionRepository.save(mission);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return persistedMission;
    }

    @Override
    public void deleteMissionById(Integer missionId) {
        Mission mission = missionRepository.findFirstById(missionId);
        missionRepository.delete(mission);
    }

    @Override
    public List<Mission> getAssignedForUser(User user) {
        System.out.println("ASSIGNED KID :: " + user.getId());
        return missionRepository.findAllByAssignedKid(user);
    }
}
