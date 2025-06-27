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
public class CreateSexOnTheBeach {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
             Хотите попробовать "Секс на пляже"? Это сладкий фруктовый лонг на водке с добавлением персикового ликёра. Коктейль прекрасно освежает и очень легко пьётся благодаря ананасовому соку, также входящему в его состав. "Секс на пляже" очень любили пить герои "Санта-Барбары", но сейчас он ассоциируется скорее с летом и шезлонгом.\s
            """;

    @Test
    public void createSexOnTheBeachTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Секс на пляже");
        cocktail.setDescription(description);
        cocktail.setVolume(200);
        cocktail.setStrength(15);
        cocktail.setIngredients(getIngredients());
        cocktail.setInstruments(getMojitoInstruments());
        cocktail.setSteps(getSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Наполни слинг кубиками льда доверху", "", null, null));
        steps.add(new Step("Налей в шейкер клюквенный сок 40 мл, ананасовый сок 40 мл, персиковый ликер 25 мл и водку 50 мл", "", null, null));
        steps.add(new Step("Наполни шейкер кубиками льда и взбей", "", null, null));
        steps.add(new Step("Перелей через стрейнер в слинг", "", null, null));
        steps.add(new Step("Укрась кусочком ананаса и коктейльной вишней на шпажке.", "", null, null));


        return steps;
    }

    private List<IngredientAndCount> getIngredients() {

        Ingredient vodka = ingredientRepository.findByName("Водка");
        Ingredient PersianLiquor = ingredientRepository.findByName("Персиковый ликер");
        Ingredient CranberryJuice = ingredientRepository.findByName("Клюквенный сок");
        Ingredient PineappleJuice = ingredientRepository.findByName("Ананасовый сок");
        Ingredient pineapple = ingredientRepository.findByName("Ананас");
        Ingredient cherry = ingredientRepository.findByName("Коктейльная вишня красная");
        Ingredient crashIce = ingredientRepository.findByName("Дробленый лед");

        ArrayList<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(vodka, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(PersianLiquor, 25, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(CranberryJuice, 40, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(PineappleJuice, 40, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(pineapple, 15, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(cherry, 5, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(crashIce, 350, UnitType.GRAM));


        return ingredients;
    }

    private List<Instrument> getMojitoInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        Instrument sling = instrumentsRepository.findByName("Слинг");
        Instrument shake = instrumentsRepository.findByName("Шейкер");
        Instrument streiner = instrumentsRepository.findByName("Стрейнер");
        Instrument jiger = instrumentsRepository.findByName("Джиггер");
        Instrument spun = instrumentsRepository.findByName("Коктейльная шпажка");
        Instrument pipes = instrumentsRepository.findByName("Трубочки");

        instruments.add(sling);
        instruments.add(shake);
        instruments.add(streiner);
        instruments.add(jiger);
        instruments.add(spun);
        instruments.add(pipes);

        return instruments;
    }
}