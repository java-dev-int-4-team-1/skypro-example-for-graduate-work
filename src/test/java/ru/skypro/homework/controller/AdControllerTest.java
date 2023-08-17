package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdMapper adMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdService adService;
    private final static String LOGIN = "user@gmail.com";
    private final static String PASSWORD = "password";

    private final static String IMAGE = "image";

    private static CreateOrUpdateAd createAd(String title, String description, int price ) {
        CreateOrUpdateAd createAd = new CreateOrUpdateAd();
        createAd.setTitle(title);
        createAd.setDescription(description);
        createAd.setPrice(price);
        return createAd;
    }
    public static Stream<CreateOrUpdateAd> streamIncorrectAdProperties() {

        return Stream.of(
                  new CreateOrUpdateAd(),
                  createAd("-", "---", -1),
                  createAd("-", "Some description", 10),
                  createAd("The Title", "---", 1),
                  createAd("The Title", "Some description", -1),
                  createAd("The looooooooooooooooooooooooooooooooooooooooong Title", "Some description", -1),
                  createAd("The Title", "Very loooooooooooooooooooooooooooooooooooooooooooooooo" +
                          "ooooooooooooooooooooooooooooooooooooooooooooooooooo0ong " +
                          " description", 10_000)
        );
    }
    @ParameterizedTest
    @MethodSource ("streamIncorrectAdProperties")
    public void givenCreate_whenIncorrectInput_thenBadRequest(CreateOrUpdateAd incorrectCreateAd) throws Exception {
        log.trace("givenCreate_whenIncorrectInput_thenBadRequest");
        performCreate(incorrectCreateAd).andExpect(status().isBadRequest());
    }

    public static Stream<CreateOrUpdateAd> streamCorrectAdProperties() {

        return Stream.of(
                createAd("Ttl.", "Dsc.", 0),
                createAd("The Title", "Some description", 1_000),
                createAd("The Title The Title The Title 32", "Some description Some description " +
                        "Some description Some 12345678", 10_000_000)
        );
    }
    @ParameterizedTest
    @MethodSource ("streamCorrectAdProperties")
    public void givenCreate_whenCorrectInput_thenCreated(CreateOrUpdateAd correctCreateAd) throws Exception {
        log.trace("givenCreate_whenCorrectInput_thenCreated");

        AdDto expected = adMapper.createOrUpdateAdToAdDto(correctCreateAd, IMAGE);
        when(adService.create(any(CreateOrUpdateAd.class), any(MockMultipartFile.class))).thenReturn(expected);

        performCreate(correctCreateAd)
                .andExpect(result -> {
                    status().isCreated();
                    AdDto actual = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), AdDto.class);
                    assertThat(actual)
                            .isNotNull()
                            .isEqualTo(expected);
                });
    }

    private ResultActions performCreate(CreateOrUpdateAd incorrectCreateAd) throws Exception {
        byte[] propertiesJson = objectMapper.writeValueAsBytes(incorrectCreateAd);

        MockMultipartFile propertiesMockMultipartFile = new MockMultipartFile("properties", "ad.txt",
                "application/json", propertiesJson);
        MockMultipartFile imageMockMultipartFile = new MockMultipartFile("image", "some-image.png",
                "image/png", "an-image".getBytes());

        log.trace("imageMockMultipartFile.getName()={}", imageMockMultipartFile.getName());

        return mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.POST, "/ads")
                        .file(propertiesMockMultipartFile)
                        .file(imageMockMultipartFile)
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString((
                                        LOGIN + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8))
                        )
                );
    }

    @ParameterizedTest
    @MethodSource ("streamCorrectAdProperties")
    public void givenCreate_whenCorrectInputButUnauthorized_thenUnauthorized(CreateOrUpdateAd correctCreateAd) throws Exception {
        log.trace("givenCreate_whenCorrectInputButUnauthorized_thenUnauthorized");
        performCreateUnauthorized(correctCreateAd).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource ("streamIncorrectAdProperties")
    public void givenCreate_whenIncorrectInputButUnauthorized_thenUnauthorized(CreateOrUpdateAd incorrectCreateAd) throws Exception {
        log.trace("givenCreate_whenIncorrectInputButUnauthorized_thenUnauthorized");
        performCreateUnauthorized(incorrectCreateAd).andExpect(status().isUnauthorized());
    }


    private ResultActions performCreateUnauthorized(CreateOrUpdateAd incorrectCreateAd) throws Exception {
        byte[] propertiesJson = objectMapper.writeValueAsBytes(incorrectCreateAd);

        MockMultipartFile propertiesMockMultipartFile = new MockMultipartFile("properties", "ad.txt",
                "application/json", propertiesJson);
        MockMultipartFile imageMockMultipartFile = new MockMultipartFile(IMAGE, "some-image.png",
                "image/png", "an-image".getBytes());


        return mockMvc.perform(MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/ads")
                .file(propertiesMockMultipartFile)
                .file(imageMockMultipartFile)
        );
    }

    @Test
    void update() {
    }
}