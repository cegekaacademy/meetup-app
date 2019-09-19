package com.cegeka.academy.service.challengeCategory;

import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.repository.ChallengeCategoryRepository;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class ChallengeCategoryServiceImp implements ChallengeCategoryService {

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Override
    public List<ChallengeCategory> getAllChallengeCategories() throws NotFoundException {

        List<ChallengeCategory> challengeCategories = challengeCategoryRepository.findAll();

        if(CollectionUtils.isEmpty(challengeCategories)){

            throw new NotFoundException().setMessage("List is empty");
        }

        return challengeCategories;

    }
}
