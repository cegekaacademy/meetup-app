package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Interest;
import com.cegeka.academy.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class InterestTest {

    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddInterest(){
        Interest interest1  = new Interest();
        interest1.setName("interest1");
        interest1.setDescription("description1");
        interestRepository.save(interest1);
        Interest interest2  = new Interest();
        interest2.setName("interest2");
        interest2.setDescription("description2");
        interestRepository.save(interest2);
        assertThat(interestRepository.findAll().size()).isEqualTo(2);
        assertThat(interestRepository.findAll().get(0).getName()).isEqualTo("interest1");
        assertThat(interestRepository.findAll().get(0).getDescription()).isEqualTo("description1");
        assertThat(interestRepository.findAll().get(1).getName()).isEqualTo("interest2");
        assertThat(interestRepository.findAll().get(1).getDescription()).isEqualTo("description2");

    }

    @Test
    public void testFindByName(){
        Interest interest1  = new Interest();
        interest1.setName("interest1");
        interest1.setDescription("description1");
        interestRepository.save(interest1);
        assertThat(interestRepository.findByName("interest1")).isEqualTo(interestRepository.findAll().get(0));
    }

    @Test
    public void testFindByNameWithNoResult(){
        Interest interest1  = new Interest();
        interest1.setName("interest1");
        interest1.setDescription("description1");
        interestRepository.save(interest1);
        assertThat(interestRepository.findByName("interest12")).isEqualTo(null);
    }

    @Test
    public void testFindByDescription(){
        Interest interest1  = new Interest();
        interest1.setName("interest1");
        interest1.setDescription("description1");
        interestRepository.save(interest1);
        assertThat(interestRepository.findByDescription("description1").get(0)).isEqualTo(interestRepository.findAll().get(0));
    }

    @Test
    public void testAddUserInterest(){
        Interest interest1  = new Interest();
        interest1.setName("interest1");
        interest1.setDescription("description1");

        User user = new User();
        user.setEmail("gigi.gogaie@gmail.com");
        user.setFirstName("Gogaie");
        user.setLastName("Momaie");
        user.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        user.setLogin("gigel@purcel.com");

        Set userSet = new HashSet<>();
        Set interestSet = new HashSet<>();

        userSet.add(user);
        interestSet.add(interest1);

        user.setUserInterests(interestSet);
        interest1.setInterestUsers(userSet);

        interestRepository.save(interest1);
        userRepository.save(user);

        assertThat(userRepository.findAll().get(4).getUserInterests().equals(interestSet)).isTrue();

        assertThat(interestRepository.findAll().get(0).getInterestUsers().equals(userSet)).isTrue();
    }
}
