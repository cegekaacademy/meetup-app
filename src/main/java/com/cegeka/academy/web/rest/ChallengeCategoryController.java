package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.service.challengeCategory.ChallengeCategoryService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/challengeCategory")

public class ChallengeCategoryController {

    @Autowired
    private ChallengeCategoryService challengeCategoryService;

    @GetMapping("/all")
    public List<ChallengeCategory> getAllChallengeCategories() throws NotFoundException {

        return challengeCategoryService.getAllChallengeCategories();
    }
}
