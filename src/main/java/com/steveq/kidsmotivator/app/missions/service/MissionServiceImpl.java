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

        System.out.println("MISSIONS TO SAVE :: " + mission);
        mission.setStage(Mission.STAGE.OPEN.name());
        mission.setOwner(userService.getCurrentlyLoggedUser());
        System.out.println("MISSIONS TO SAVE :: " + mission);

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
