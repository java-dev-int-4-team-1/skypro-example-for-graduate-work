package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.CreateOrUpdateAd;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdController.class)
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private final static String LOGIN = "user@gmail.com";
    private final static String PASSWORD = "password";

    @Test
    /** ToDo to solve: Returns however 403 instead of 400 **/
    void givenCreate_whenIncorrectInput_thenBadRequest() throws Exception {

        CreateOrUpdateAd invalidCreateAd = new CreateOrUpdateAd();
        invalidCreateAd.setDescription("---");
        invalidCreateAd.setTitle("-");
        invalidCreateAd.setPrice(-1);
        byte[] propertiesJson = objectMapper.writeValueAsBytes(invalidCreateAd);


        MockMultipartFile propertiesMockMultipartFile = new MockMultipartFile("properties", "ad.txt",
                "application/json", propertiesJson);
        MockMultipartFile imageMockMultipartFile = new MockMultipartFile("image", "some-image.png",
                "image/png", "an-image".getBytes());


        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.POST, "/ads")
                        .file(propertiesMockMultipartFile)
                        .file(imageMockMultipartFile)
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString((
                                        LOGIN + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8))
                        )
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() {
    }
}