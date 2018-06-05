package com.steveq.kidsmotivator.app.prizes.dao;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PrizesRepository extends JpaRepository<Prize, Integer> {

    List<Prize> findAllByOwner(Set<User> parents);
    List<Prize> findAllByOwnerAndAssigneeIsNull(Set<User> parents);
    List<Prize> findAllByOwnerAndAssigneeIsNotNull(Set<User> parents);
    List<Prize> findAllByAssigneeIsNull();
    List<Prize> findAllByAssignee(User assignee);
    Prize findFirstById(int prizeId);

}
