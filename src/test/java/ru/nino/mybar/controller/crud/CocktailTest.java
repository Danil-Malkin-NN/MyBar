package ru.nino.mybar.controller.crud;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.nino.mybar.config.PostgresDbForTest;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.IngredientAndCountDto;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.dto.show.StepDto;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.service.IngredientServiceImpl;
import ru.nino.mybar.service.InstrumentServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static ru.nino.mybar.entity.UnitType.GRAM;
import static ru.nino.mybar.entity.UnitType.MILLILITER;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CocktailTest extends PostgresDbForTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    IngredientServiceImpl ingredientService;
    @Autowired
    InstrumentServiceImpl instrumentService;
    @Autowired
    IngredientRepositoryImpl ingredientRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Создаётся коктейль дайкири из пред сохранённых данных в БД")
    public void createNewOrderWith2AcidWithOutExtraActionsSuccess() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/cocktails/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getCockteilJson())
                                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
        var perform = mvc.perform(
                MockMvcRequestBuilders
                        .get("/cocktails/1")
        );

        var mvcResult = perform.andReturn();
        var response = mvcResult.getResponse();
        var contentAsString = response.getContentAsString(StandardCharsets.UTF_8);
        var cocktailDto = mapper.readValue(contentAsString, CocktailDto.class);

    }

    private String getCockteilJson() throws JsonProcessingException {
        CocktailDto cocktailDto = CocktailDto.builder()
                .name("Дайкири")
                .description("Хотите попробовать \"Дайкири\"? Это кислый коктейль на основе рома с добавлением лаймового сока и сахарного сиропа. \"Дайкири\" придумал американец, посетивший Кубу, поэтому этот классический коктейль встречается на страницах многих произведений американской литературы ХХ века.")
                .ingredients(getIngredients())
                .instruments(getInstruments())
                .steps(getSteps())
                .build();

        return mapper.writeValueAsString(cocktailDto);
    }

    private List<StepDto> getSteps() {
        StepDto firestStep = StepDto.builder()
                .goal("Налей в шейкер лаймовый сок 30 мл, сахарный сироп 15 мл и белый ром 60 мл")
                .description("Налей в шейкер лаймовый сок 30 мл, сахарный сироп 15 мл и белый ром 60 мл")
                .instruments(List.of(instrumentService.getByName("Шейкер")))
                .usesIngredients(List.of(IngredientAndCountDto.builder()
                                .ingredient(ingredientService.getByName("Белый ром"))
                                .count(60)
                                .unitType(MILLILITER)
                                .build(),
                        IngredientAndCountDto.builder()
                                .ingredient(ingredientService.getByName("Сахарный сироп"))
                                .count(30)
                                .unitType(MILLILITER)
                                .build(),

                        IngredientAndCountDto.builder()
                                .ingredient(ingredientService.getByName("Лаймовый сок"))
                                .count(30)
                                .unitType(MILLILITER)
                                .build()))
                .build();
        StepDto secondStep = StepDto.builder()
                .goal("Смешать ингредиенты")
                .description("Наполни шейкер кубиками льда и взбей")
                .instruments(List.of(instrumentService.getByName("Шейкер")))
                .usesIngredients(List.of(IngredientAndCountDto.builder()
                        .ingredient(ingredientService.getByName("Лед в кубиках"))
                        .count(100)
                        .unitType(GRAM)
                        .build()))
                .build();


        return List.of(firestStep, secondStep);
    }

    private List<InstrumentDto> getInstruments() {
        InstrumentDto djigger = instrumentService.getByName("Джиггер");
        InstrumentDto strainer = instrumentService.getByName("Стрейнер");
        InstrumentDto shaker = instrumentService.getByName("Шейкер");
        InstrumentDto champagneSaucer = instrumentService.getByName("Шампанское блюдце");

        return List.of(djigger, strainer, shaker, champagneSaucer);
    }

    private List<IngredientAndCountDto> getIngredients() {
        IngredientAndCountDto whiteRom = IngredientAndCountDto.builder()
                .ingredient(ingredientService.getByName("Белый ром"))
                .count(60)
                .unitType(MILLILITER)
                .build();
        IngredientAndCountDto sugar = IngredientAndCountDto.builder()
                .ingredient(ingredientService.getByName("Сахарный сироп"))
                .count(30)
                .unitType(MILLILITER)
                .build();

        IngredientAndCountDto limeJuice = IngredientAndCountDto.builder()
                .ingredient(ingredientService.getByName("Лаймовый сок"))
                .count(30)
                .unitType(MILLILITER)
                .build();

        IngredientAndCountDto ice = IngredientAndCountDto.builder()
                .ingredient(ingredientService.getByName("Лед в кубиках"))
                .count(100)
                .unitType(GRAM)
                .build();

        return List.of(whiteRom, sugar, limeJuice, ice);
    }
}
