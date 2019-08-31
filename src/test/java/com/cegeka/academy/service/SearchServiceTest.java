package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Category;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.AddressRepository;
import com.cegeka.academy.repository.CategoryRepository;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.serviceValidation.SearchService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class SearchServiceTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SearchService searchService;

    private Event event;

    @BeforeEach
    public void init() {

        categoryRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
        User userOwner = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(userOwner);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Set<Category> set = new HashSet<>();
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, userRepository.findAll().get(0), set, null);
        Category category = TestsRepositoryUtil.createCategory("Ana", "description1");
        Category category1 = TestsRepositoryUtil.createCategory("MARIA", "description1");
        event.getCategories().add(category);
        category.getEvents().add(event);
        User user = TestsRepositoryUtil.createUser("login1", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        user.getEvents().add(event);
        event.getUsers().add(user);
        userRepository.save(user);
        eventRepository.save(event);
        categoryRepository.save(category1);

    }

    @Test
    public void assertSearchEventsByCategoryNameIsWorkingWithValidArgument() throws NotFoundException {

        Set<Event> events = searchService.searchEventsByCategoryName("Ana");
        assertThat(events.size()).isEqualTo(1);
    }

    @Test
    public void assertSearchEventsByCategoryNameIsWorkingWithInvalidArgument() {

        Assertions.assertThrows(NotFoundException.class, () -> searchService.searchEventsByCategoryName("Ana1"));
    }

    @Test
    public void assertSearchEventsByCategoryNameIsWorkingWithNullArgument() {

        Assertions.assertThrows(NotFoundException.class, () -> searchService.searchEventsByCategoryName(null));
    }

    @Test
    public void assertSearchEventsByCategoryNameIsWorkingWithValidArgumentButNoEvents() {

        Assertions.assertThrows(NotFoundException.class, () -> searchService.searchEventsByCategoryName("MARIA"));
    }

    @Test
    public void assertSearchUserByInterestedEventsIsWorkingWithValidSet() throws NotFoundException {

        Set<Event> events = new HashSet<>();
        events.add(event);
        List<User> list = searchService.searchUserByInterestedEvents(events);
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void assertSearchUserByInterestedEventsIsWorkingWithVEmptySet() throws NotFoundException {

        Set<Event> events = new HashSet<>();
        List<User> list = searchService.searchUserByInterestedEvents(events);
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void assertSearchUserByInterestedEventsIsWorkingWithNullSet() {

        Assertions.assertThrows(NotFoundException.class, () -> searchService.searchUserByInterestedEvents(null));

    }
}
