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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Category category_1 = TestsRepositoryUtil.createCategory("Sport", "Liber pentru toate varstele!");
        Category category_3 = TestsRepositoryUtil.createCategory("Arta", "Expozitii de arta");
        Set<Category> list1 = new HashSet<>();
        list1.add(category_1);
        list1.add(category_3);
        categoryRepository.save(category_1);
        categoryRepository.save(category_3);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, list1, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventRepository.save(event);
        Category category1 = TestsRepositoryUtil.createCategory("Ana", "description");
        categoryRepository.saveAndFlush(category1);
        event.getCategories().add(category1);
        eventRepository.saveAndFlush(event);
        Category category2 = TestsRepositoryUtil.createCategory("Dana", "description");
        categoryRepository.saveAndFlush(category2);
        event.getCategories().add(category2);
        eventRepository.save(event);
        Category category3 = TestsRepositoryUtil.createCategory("Doriana", "description");
        categoryRepository.save(category3);
        event.getCategories().add(category3);
        eventRepository.save(event);
        List<Category> categories = categoryRepository.findAllByEvents_id(event.getId());
        assertThat(categories.size()).isEqualTo(5);

    }

}
