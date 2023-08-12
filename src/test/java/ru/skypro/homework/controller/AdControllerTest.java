package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.CreateOrUpdateAd;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdController.class)
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test

    void givenCreate_whenIncorrectInput_thenBadRequest() throws Exception {

        CreateOrUpdateAd invalidCreateAd = new CreateOrUpdateAd();
        invalidCreateAd.setDescription("---");
        invalidCreateAd.setTitle("-");
        invalidCreateAd.setPrice(-1);
        byte [] propertiesJson = objectMapper.writeValueAsBytes(invalidCreateAd);


        MockMultipartFile propertiesMockMultipartFile = new MockMultipartFile("properties", "ad.txt",
                "application/json", propertiesJson);
        MockMultipartFile imageMockMultipartFile = new MockMultipartFile("image", "some-image.png",
                "image/png", "an-image".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders
                        .multipart("/ads");
        builder.with(
                request -> {
                    request.setMethod("POST");
                    return request;
                });
        builder.file(propertiesMockMultipartFile);
        builder.file(imageMockMultipartFile);

        mockMvc.perform(builder)
          .andExpect(status().isBadRequest());
    }

    @Test
    void update() {
    }
}