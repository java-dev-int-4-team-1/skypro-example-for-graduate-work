package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.CreateOrUpdateAd;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
        invalidCreateAd.setDescription("123");
        invalidCreateAd.setTitle("1");
        invalidCreateAd.setPrice(-1);

        String body = objectMapper.writeValueAsString(invalidCreateAd);

        MockMultipartFile mockImgFile = new MockMultipartFile("mocked-img-file", new byte[0]);

        mockMvc.perform(multipart("/ads", body, mockImgFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() {
    }
}