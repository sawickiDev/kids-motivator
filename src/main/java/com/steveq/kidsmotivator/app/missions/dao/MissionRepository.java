package com.steveq.kidsmotivator.app.missions.dao;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    List<Mission> findAllByOwner(User owner);
    Mission findFirstById(Integer id);
    List<Mission> findAllByAssignedKid(User kid);
}
