package com.steveq.kidsmotivator.app.missions.service;

import com.steveq.kidsmotivator.app.missions.model.Mission;

public interface MissionService {
    Mission saveMission(Mission mission);
    void deleteMissionById(Integer missionId);
}
