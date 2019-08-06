package com.cegeka.academy.repository;


import com.cegeka.academy.domain.AgeIntervals;
import com.cegeka.academy.domain.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {

    
}
