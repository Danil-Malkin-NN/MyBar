package ru.nino.mybar.controller.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.nino.mybar.config.PostgresDbForTest;

@SpringBootTest
@AutoConfigureMockMvc
class CocktailGetTest extends PostgresDbForTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getPageAll() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/cocktails//all/data/pages")
                )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        System.out.println();
    }
}