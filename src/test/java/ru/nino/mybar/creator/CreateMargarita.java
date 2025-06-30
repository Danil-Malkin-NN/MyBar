package ru.nino.mybar.creator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.entity.Step;
import ru.nino.mybar.entity.UnitType;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;
import ru.nino.mybar.repository.impl.InstrumentsRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

@Disabled
@SpringBootTest
@ActiveProfiles("prod")
public class CreateMargarita {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
          Хотите попробовать маргариту? Это солоноватый кислый коктейль на текиле с лаймовым соком. Бармены во всем мире очень любят создавать твисты на этот классический коктейль, но окаёмка из соли практически всегда остаётся неизменным украшением "Маргариты".\s
            """;

    @Test
    public void createMargaritaTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Маргарита");
        cocktail.setDescription(description);
        cocktail.setVolume(115);
        cocktail.setStrength(20);
        cocktail.setIngredients(getIngredients());
        cocktail.setInstruments(getInstruments());
        cocktail.setSteps(getSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Сделай на бокале для маргариты соленую окаемку", "", null, null));
        steps.add(new Step("Налей в шейкер лаймовый сок 30 мл, сахарный сироп 10 мл, ликер трипл сек 25 мл и серебряную текилу 50 мл", "", null, null));
        steps.add(new Step("Наполни шейкер кубиками льда и взбей", "", null, null));
        steps.add(new Step("Перелей через стрейнер в охлажденный бокал для маргариты", "", null, null));
        steps.add(new Step("Укрась кружком лайма", "", null, null));

        return steps;
    }

    private List<IngredientAndCount> getIngredients() {
        Ingredient tequila = ingredientRepository.findByName("Серебрянная текила");
        Ingredient tripleSec = ingredientRepository.findByName("Трипл сек Fruko Schulz");
        Ingredient sugarSyrup = ingredientRepository.findByName("Сахарный сироп");
        Ingredient limeJuice = ingredientRepository.findByName("Лаймовый сок");
        Ingredient lime = ingredientRepository.findByName("Лайм");
        Ingredient salt = ingredientRepository.findByName("Соль");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(tequila, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(tripleSec, 25, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(sugarSyrup, 10, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(limeJuice, 30, UnitType.MILLILITER)); // исправлено с GRAM на MILLILITER
        ingredients.add(new IngredientAndCount(lime, 10, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(salt, 2, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(crushedIce, 200, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        Instrument margaritaGlass = instrumentsRepository.findByName("Бокал маргарита");
        Instrument shaker = instrumentsRepository.findByName("Шейкер");
        Instrument strainer = instrumentsRepository.findByName("Стрейнер");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");
        Instrument citrusPress = instrumentsRepository.findByName("Пресс для цитрусовых");

        instruments.add(margaritaGlass);
        instruments.add(shaker);
        instruments.add(strainer);
        instruments.add(jigger);
        instruments.add(citrusPress);

        return instruments;
    }
}