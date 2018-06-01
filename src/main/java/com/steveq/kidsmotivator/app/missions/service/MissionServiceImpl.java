package com.steveq.kidsmotivator.app.missions.service;

import com.steveq.kidsmotivator.app.missions.dao.MissionRepository;
import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserService userService;

    @Override
    public Mission saveMission(Mission mission) {
        Mission persistedMission = null;

        mission.setStage(Mission.STAGE.OPEN.name());
        mission.setOwner(userService.getCurrentlyLoggedUser());

        if (mission.getAssignedId() >= 0) {
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
}
