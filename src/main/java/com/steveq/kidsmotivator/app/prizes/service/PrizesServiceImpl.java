package com.steveq.kidsmotivator.app.prizes.service;

import com.steveq.kidsmotivator.app.persistence.model.User;
import com.steveq.kidsmotivator.app.persistence.service.UserService;
import com.steveq.kidsmotivator.app.prizes.dao.PrizesRepository;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PrizesServiceImpl implements PrizesService {

    @Autowired
    private PrizesRepository prizesRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Prize> getPrizesByOwner(Set<User> parents) {
        if (parents != null)
            return prizesRepository.findAllByOwner(parents);
        return null;
    }

    @Override
    public List<Prize> getPrizesNotAssigned() {
        return prizesRepository.findAllByAssigneeIsNull();
    }

    @Override
    public List<Prize> getPrizesByAssignedKid(User assignee) {
        if (assignee != null)
            return prizesRepository.findAllByAssignee(assignee);
        return null;
    }

    @Override
    public List<Prize> getPrizesAvailableByOwner(Set<User> parents) {
        if (parents != null)
            return prizesRepository.findAllByOwnerAndAssigneeIsNull(parents);
        return null;
    }

    @Override
    public List<Prize> getPrizesTakenByOwner(Set<User> parents) {
        if (parents != null)
            return prizesRepository.findAllByOwnerAndAssigneeIsNotNull(parents);
        return null;
    }

    @Override
    public Prize savePrize(Prize prize) {
        Prize savedPrize = null;

        if (userService.isUserParent(userService.getCurrentlyLoggedUser()))
            prize.setOwner(userService.getCurrentlyLoggedUser());

        try {
            savedPrize = prizesRepository.save(prize);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return savedPrize;
    }

    @Override
    public void deletePrizeById(int prizeId) {
        if (prizeId >= 0)
            prizesRepository.delete(prizesRepository.findFirstById(prizeId));
    }

    @Override
    public Prize findById(int prizeId) {
        if (prizeId >= 0)
            return prizesRepository.findFirstById(prizeId);
        return null;
    }
}
