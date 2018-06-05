package com.steveq.kidsmotivator.app.missions.service;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.auth.model.User;

import java.util.List;

public interface MissionService {
    Mission saveMission(Mission mission);
    void deleteMissionById(Integer missionId);
    List<Mission> getAssignedForUser(User user);
    List<Mission> getAllByAssignedKid(User user);
    List<Mission> getAllByOwner(User user);
    Mission getMissionById(int missionId);
    List<String> getStagesByUser(User user);
}
