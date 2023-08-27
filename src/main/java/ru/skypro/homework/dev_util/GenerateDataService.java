package ru.skypro.homework.dev_util;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;
import ru.skypro.homework.config.AdAppConstants;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenerateDataService implements AdAppConstants {

    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;


    
    private final Faker faker = new Faker();
    private final FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    private final String IMAGE_SAMPLE = "bender.jpg";

    public void generateData(int userCount, int adCount, int commentCount) {
        log.trace("generateData(userCount={}, adCount={}, commentCount={})", userCount, adCount, commentCount);

        generateUsers(userCount);
        generateAds(adCount);
        generateComments(commentCount);
    }

    private void generateUsers(int userCount) {
        log.trace("generateUsers(userCount={})", userCount);

        for (int i = 0; i < userCount; i++) {
            generateUser();
        }
    }

    private Role generateRole() {
        return faker.random().nextBoolean() ? Role.USER : Role.ADMIN;
    }
    private void generateUser() {
        User  user = new User();

        user.setFirstName(faker.harryPotter().character());
        user.setLastName(faker.lebowski().character());
        user.setEmail(fakeValuesService.bothify("??????##@gmail.com"));
        user.setPassword(faker.bothify("???????#####????????##"));
        user.setRole(generateRole());
        user.setPhone(faker.numerify("############"));
        user.setImage(IMAGE_SAMPLE);

        user = userRepository.save(user);

        log.trace("Generated user: {}", user);

    }

    private void generateAds(int adCount) {
        log.trace("generateUsers(adCount={})", adCount);

        for (int i = 0; i < adCount; i++) {
            generateAd();
        }
    }

    private void generateAd() {
        Ad ad = new Ad();
        ad.setAuthor(getUser());
        ad.setPrice(getNextInt(AD_PRICE_MIN, AD_PRICE_MAX));
        ad.setTitle(faker.lorem().fixedString(getNextInt(AD_TITLE_LENGTH_MIN, AD_TITLE_LENGTH_MAX)));
        ad.setDescription(faker.lorem().fixedString(getNextInt(AD_DESCRIPTION_LENGTH_MIN, AD_DESCRIPTION_LENGTH_MAX)));
        ad.setImage(IMAGE_SAMPLE);
        
        ad = adRepository.save(ad);


        log.trace("Generated ad: {}", ad);

    }

    private User getUser() {
        int userCount = (int)userRepository.count();

        return userRepository
                .findAll(PageRequest.of(faker.random().nextInt(userCount), 1))
                .stream()
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    private Integer getNextInt(int min, int max) {
        return faker.random().nextInt(min, max + 1);
    }

    private void generateComments(int commentCount) {
        log.trace("generateComments(commentCount={})", commentCount);

        for (int i = 0; i < commentCount; i++) {
            generateComment();
        }
    }

    private void generateComment() {

        Comment comment = new Comment();

        comment.setAd(getAd());
        comment.setAuthor(getUser());
        comment.setText(faker.lorem().fixedString(getNextInt(10, 100)));
        comment.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        comment = commentRepository.save(comment);

        log.trace("Generated comment: {}", comment);
    }

    private Ad getAd() {
        int adCount = (int)adRepository.count();

        return adRepository
                .findAll(PageRequest.of(faker.random().nextInt(adCount), 1))
                .stream()
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

}
