package ru.skypro.homework.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.testutil.AdTestUtil;

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
class AdControllerTest extends AdTestUtil {

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

    private String buildLoginPassword() {
        return "Basic " + Base64.getEncoder().encodeToString((
                LOGIN + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8));
    }
    public static Stream<CreateOrUpdateAd> streamIncorrectAdProperties() {

        return Stream.of(
                new CreateOrUpdateAd(),
                generateCreateOrUpdateAd("-", "---", -1),
                generateCreateOrUpdateAd("-", "Some description", 10),
                generateCreateOrUpdateAd("The Title", "---", 1),
                generateCreateOrUpdateAd("The Title", "Some description", -1),
                generateCreateOrUpdateAd("The looooooooooooooooooooooooooooooooooooooooong Title", "Some description", -1),
                generateCreateOrUpdateAd("The Title", "Very loooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "ooooooooooooooooooooooooooooooooooooooooooooooooooo0ong " +
                        " description", 10_000)
        );
    }

    @ParameterizedTest
    @MethodSource("streamIncorrectAdProperties")
    public void givenCreate_whenIncorrectInput_thenBadRequest(CreateOrUpdateAd incorrectCreateAd) throws Exception {
        log.trace("givenCreate_whenIncorrectInput_thenBadRequest");
        performCreate(incorrectCreateAd).andExpect(status().isBadRequest());
    }

    public static Stream<CreateOrUpdateAd> streamCorrectAdProperties() {

        return Stream.of(
                generateCreateOrUpdateAd("Ttl.", "Dsc.", 0),
                generateCreateOrUpdateAd("The Title", "Some description", 1_000),
                generateCreateOrUpdateAd("The Title The Title The Title 32", "Some description Some description " +
                        "Some description Some 12345678", 10_000_000)
        );
    }

    @ParameterizedTest
    @MethodSource("streamCorrectAdProperties")
    public void givenCreate_whenCorrectInput_thenCreated(CreateOrUpdateAd correctCreateAd) throws Exception {
        log.trace("givenCreate_whenCorrectInput_thenCreated");


        MockMultipartFile imageMockMultipartFile = new MockMultipartFile(IMAGE, "some-image.png",
                "image/png", "an-image".getBytes());

        AdDto expected = adMapper.map(
                adMapper.map(correctCreateAd, imageMockMultipartFile)
        );


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
                .header(HttpHeaders.AUTHORIZATION, buildLoginPassword())
        );
    }

    @ParameterizedTest
    @MethodSource("streamCorrectAdProperties")
    public void givenCreate_whenCorrectInputButUnauthorized_thenUnauthorized(CreateOrUpdateAd correctCreateAd) throws Exception {
        log.trace("givenCreate_whenCorrectInputButUnauthorized_thenUnauthorized");
        performCreateUnauthenticated(correctCreateAd).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource("streamIncorrectAdProperties")
    public void givenCreate_whenIncorrectInputButUnauthorized_thenUnauthorized(CreateOrUpdateAd incorrectCreateAd) throws Exception {
        log.trace("givenCreate_whenIncorrectInputButUnauthorized_thenUnauthorized");
        performCreateUnauthenticated(incorrectCreateAd).andExpect(status().isUnauthorized());
    }


    private ResultActions performCreateUnauthenticated(CreateOrUpdateAd incorrectCreateAd) throws Exception {
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
    public void patchProperties() throws Exception {
        //given
        CreateOrUpdateAd properties = generateCreateOrUpdateAd();

        MockMultipartFile imageMockMultipartFile = new MockMultipartFile(IMAGE, "some-image.png",
                "image/png", "an-image".getBytes());

        AdDto expected = adMapper.map(
                adMapper.map(properties, imageMockMultipartFile)
        );

        //when
        when(adService.patchProperties(ID, properties)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                .patch("/ads/{id}", Integer.toString(ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(properties))
                .header(HttpHeaders.AUTHORIZATION,
                       buildLoginPassword()
                )
        //then
        ).andExpect((result)->{
                assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                AdDto actual = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                        new TypeReference<>() {
                        });
                assertThat(actual)
                        .isNotNull()
                        .isEqualTo(expected);

        });
    }


}