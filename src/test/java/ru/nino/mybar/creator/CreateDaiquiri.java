package ru.nino.mybar.creator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.nino.mybar.entity.*;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.repository.impl.InstrumentsRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("prod")
public class CreateDaiquiri {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
           Хотите попробовать "Дайкири"? Это кислый коктейль на основе рома с добавлением лаймового сока и сахарного сиропа. 
           "Дайкири" придумал американец, посетивший Кубу, поэтому этот классический коктейль встречается на страницах 
           многих произведений американской литературы ХХ века.\s
            """;

    @Test
    public void createDaiquiriTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Дайкири");
        cocktail.setDescription(description);
        cocktail.setVolume(105);
        cocktail.setStrength(25);
        cocktail.setIngredients(getDaiquiriIngredients());
        cocktail.setInstruments(getDaiquiriInstruments());
        cocktail.setSteps(getDaiquiriSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getDaiquiriSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Налей в шейкер лаймовый сок 30 мл, сахарный сироп 15 мл и белый ром 60 мл", "", null, null));
        steps.add(new Step("Наполни шейкер кубиками льда и взбей", "", null, null));
        steps.add(new Step("Перелей через стрейнер в охлажденное шампанское блюдце", "", null, null));
        return steps;
    }

    private List<IngredientAndCount> getDaiquiriIngredients() {
        Ingredient whiteRum = ingredientRepository.findByName("Белый ром");
        Ingredient sugarSyrup = ingredientRepository.findByName("Сахарный сироп");
        Ingredient limeJuice = ingredientRepository.findByName("Лаймовый сок");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(whiteRum, 60, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(sugarSyrup, 15, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(limeJuice, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(crushedIce, 200, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getDaiquiriInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument coupeGlass = instrumentsRepository.findByName("Шампанское блюдце");
        Instrument strainer = instrumentsRepository.findByName("Стрейнер");
        Instrument shaker = instrumentsRepository.findByName("Шейкер");
        Instrument citrusPress = instrumentsRepository.findByName("Пресс для цитрусовых");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");

        instruments.add(coupeGlass);
        instruments.add(strainer);
        instruments.add(shaker);
        instruments.add(citrusPress);
        instruments.add(jigger);

        return instruments;
    }
}