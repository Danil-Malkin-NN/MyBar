package ru.nino.mybar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.nino.mybar.config.PostgresDbForTest;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class MyBarControllerTest extends PostgresDbForTest {

    public static final String BASIC_AUTH = "Basic VGVzdFVzZXJOYW1lOlRlc3RQYXNzd29yZA==";
    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

//    @Test
//    @DisplayName("Проверка что ингредиенты доступные пользователю помечаются как доступные в ДТО коктейлей")
//    void getAvailableCocktails() throws Exception {
//        var answer = mvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/my/available/cocktails")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("Authorization", BASIC_AUTH)
//                )
//                .andExpect(MockMvcResultMatchers.status()
//                        .isOk());
//
//        var contentAsString = answer.andReturn()
//                .getResponse()
//                .getContentAsString(StandardCharsets.UTF_8);
//
//
//        List<CocktailUserIngredientsDto> list = objectMapper.readValue(contentAsString, new TypeReference<List<CocktailUserIngredientsDto>>() {
//        });
//
//        list.stream()
//                .flatMap(cocktailUserIngredientsDto -> cocktailUserIngredientsDto.getIngredients()
//                        .stream())
//                .filter(ingredientAvailableDto -> "Лондонский сухой джин".equals(ingredientAvailableDto.getName())
//                                || "Лаймовый кордиал".equals(ingredientAvailableDto.getName()))
//                .map(IngredientAvailableDto::isAvailable)
//                .forEach(Assertions::assertTrue);
//
//
//    }

}