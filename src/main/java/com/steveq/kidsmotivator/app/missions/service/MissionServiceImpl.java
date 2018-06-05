package com.steveq.kidsmotivator.app.missions.service;

import com.steveq.kidsmotivator.app.missions.dao.MissionRepository;
import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MissionServiceImpl implements MissionService {

    private MissionRepository missionRepository;
    private UserService userService;

    @Autowired
    public MissionServiceImpl(MissionRepository missionRepository,
                              UserService userService) {
        this.missionRepository = missionRepository;
        this.userService = userService;
    }

    @Override
    public Mission saveMission(Mission mission) {

        Mission persistedMission = null;

//        if (mission.getStage().isEmpty())
//            mission.setStage(Mission.STAGE.OPEN.name());

        if (mission.getOwner() == null)
            mission.setOwner(userService.getCurrentlyLoggedUser());

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
        if (user != null)
            return missionRepository.findAllByAssignedKid(user);
        return new ArrayList<>();
    }

    @Override
    public List<Mission> getAllByAssignedKid(User user) {
        if (user != null)
            return missionRepository
                    .findAllByAssignedKidAndStageIn(user,
                                                    Stream.of("ASSIGNED", "OVERDUE").
                                                            collect(Collectors.toList())
                                                    );
        return new ArrayList<>();
    }

    @Override
    public List<Mission> getAllByOwner(User user) {
        if (user == null)
            return new ArrayList<>();

        if (userService.isUserParent(user))
            return missionRepository.findAllByOwner(user);
        else if (userService.isUserKid(user))
            return missionRepository
                    .findAllByAssignedKid(user);

        return new ArrayList<>();
    }

    @Override
    public Mission getMissionById(int missionId) {
        if (missionId >= 0)
            return missionRepository.findFirstById(missionId);
        return new Mission(){};
    }

    @Override
    public List<String> getStagesByUser(User user) {
        Stream<String> stream = Arrays.stream(Mission.STAGE.values())
                                .map(Mission.STAGE::name);
        if (userService.isUserKid(user))
            return  stream
                    .filter(s -> !"open".equals(s.toLowerCase()))
                    .collect(Collectors.toList());
        else if (userService.isUserParent(user))
            return  stream
                    .collect(Collectors.toList());

        return new ArrayList<>();
    }
}
