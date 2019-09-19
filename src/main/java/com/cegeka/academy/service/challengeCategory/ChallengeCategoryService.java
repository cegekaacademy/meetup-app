package com.cegeka.academy.service.challengeCategory;

import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.web.rest.errors.NotFoundException;

import java.util.List;

public interface ChallengeCategoryService {

    List<ChallengeCategory> getAllChallengeCategories() throws NotFoundException;
}
