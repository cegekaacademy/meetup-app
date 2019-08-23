package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Category;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class CategoryTest {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAllByEventId() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Category category1 = TestsRepositoryUtil.createCategory("Ana", "description");
        categoryRepository.save(category1);
        event.getCategories().add(category1);
        eventRepository.save(event);
        Category category2 = TestsRepositoryUtil.createCategory("Dana", "description");
        categoryRepository.save(category2);
        event.getCategories().add(category2);
        eventRepository.save(event);
        Category category3 = TestsRepositoryUtil.createCategory("Doriana", "description");
        categoryRepository.save(category3);
        event.getCategories().add(category3);
        eventRepository.save(event);
        List<Category> categories = categoryRepository.findAllByEvents_id(event.getId());
        assertThat(categories.size()).isEqualTo(3);

    }

}
