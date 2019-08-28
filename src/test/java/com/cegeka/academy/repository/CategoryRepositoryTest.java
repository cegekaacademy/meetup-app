package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Category;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class CategoryRepositoryTest {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Event event;

    @BeforeEach
    public void init() {

        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Category category1 = TestsRepositoryUtil.createCategory("Ana", "description1");
        categoryRepository.save(category1);
        event.getCategories().add(category1);
        eventRepository.save(event);
        Category category2 = TestsRepositoryUtil.createCategory("Dana", "description2");
        categoryRepository.save(category2);
        event.getCategories().add(category2);
        eventRepository.save(event);
        Category category3 = TestsRepositoryUtil.createCategory("Doriana", "description3");
        categoryRepository.save(category3);
        event.getCategories().add(category3);
        eventRepository.save(event);
    }

    @Test
    public void testFindAllByEventId() {
        List<Category> categories = categoryRepository.findAllByEvents_id(event.getId());
        assertThat(categories.size()).isEqualTo(3);

    }

    @Test
    public void assertThatFindByNameIsWorkingWithValidArgument() {

        Category findCategory = categoryRepository.findByName("Ana");
        assertThat(findCategory).isEqualTo(categoryRepository.findAll().get(0));

    }

    @Test
    public void assertThatFindByNameIsWorkingWithInvalidArgument() {

        Category findCategory = categoryRepository.findByName("Ana1");
        assertThat(findCategory).isEqualTo(null);
    }

    @Test
    public void assertThatFindByNameIsWorkingWithNullArgument() {

        Category findCategory = categoryRepository.findByName(null);
        assertThat(findCategory).isEqualTo(null);
    }
}
