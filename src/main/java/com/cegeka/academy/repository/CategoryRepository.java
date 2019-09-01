package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByEvents_id(Long eventId);

    Category findByName(String categoryName);

}
