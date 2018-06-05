package com.steveq.kidsmotivator.app.prizes.service;

import com.steveq.kidsmotivator.app.auth.model.User;
import com.steveq.kidsmotivator.app.auth.service.UserService;
import com.steveq.kidsmotivator.app.prizes.dao.PrizesRepository;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PrizesServiceImpl implements PrizesService {

    private PrizesRepository prizesRepository;
    private UserService userService;

    @Autowired
    public PrizesServiceImpl(PrizesRepository prizesRepository,
                             UserService userService) {
        this.prizesRepository = prizesRepository;
        this.userService = userService;
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
        return new Prize();
    }

    @Override
    public List<Prize> getPrizesAvailable(User user) {
        if (userService.isUserParent(user))
            return prizesRepository.findAllByOwnerAndAssigneeIsNull(Stream.of(user).collect(Collectors.toSet()));
        else if (userService.isUserKid(user))
            return prizesRepository.findAllByOwnerAndAssigneeIsNull(user.getParents());

        return new ArrayList<>();
    }

    @Override
    public List<Prize> getPrizesTaken(User user) {
        if (userService.isUserParent(user))
            return prizesRepository.findAllByOwnerAndAssigneeIsNotNull(Stream.of(user).collect(Collectors.toSet()));
        else if (userService.isUserKid(user))
            return prizesRepository.findAllByAssignee(user);

        return new ArrayList<>();
    }
}
