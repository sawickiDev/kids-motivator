package com.steveq.kidsmotivator.app.missions.service;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.persistence.model.User;

import java.util.List;

public interface MissionService {
    Mission saveMission(Mission mission);
    void deleteMissionById(Integer missionId);
    List<Mission> getAssignedForUser(User user);
}
