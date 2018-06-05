package com.steveq.kidsmotivator.app.prizes.service;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.prizes.model.Prize;

import java.util.List;
import java.util.Set;

public interface PrizesService {
    Prize savePrize(Prize prize);
    void deletePrizeById(int prizeId);
    Prize findById(int prizeId);
    List<Prize> getPrizesAvailable(User user);
    List<Prize> getPrizesTaken(User user);
}
