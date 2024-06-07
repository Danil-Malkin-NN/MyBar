package ru.nino.mybar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.nino.mybar.config.PostgresDbForTest;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class MyBarControllerTest extends PostgresDbForTest {

    public static final String BASIC_AUTH = "Basic VGVzdFVzZXJOYW1lOlRlc3RQYXNzd29yZA==";
    @Autowired
    private MockMvc mvc;

    @Test
    void getAvailableCocktails() throws Exception {
        var answer = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/my/available/cocktails")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", BASIC_AUTH)
                )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        answer.andReturn().getResponse().getContentAsString();


    }

}