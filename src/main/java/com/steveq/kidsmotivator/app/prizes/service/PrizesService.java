package com.steveq.kidsmotivator.app.prizes.service;

import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.prizes.model.Prize;

import java.util.List;
import java.util.Set;

public interface PrizesService {

    List<Prize> getPrizesByOwner(Set<User> parents);
    List<Prize> getPrizesNotAssigned();
    List<Prize> getPrizesByAssignedKid(User assignee);
    List<Prize> getPrizesAvailableByOwner(Set<User> parents);
    List<Prize> getPrizesTakenByOwner(Set<User> parents);
    Prize savePrize(Prize prize);
    void deletePrizeById(int prizeId);
    Prize findById(int prizeId);

}
