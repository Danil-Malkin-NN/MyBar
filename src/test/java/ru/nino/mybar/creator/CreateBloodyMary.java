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
public class CreateBloodyMary {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
           Хотите попробовать "Кровавую Мэри"? Это слабоалкогольный овощной лонг на водке с томатным соком и сельдереем. 
           Напиток, в который также добавляют лимонный сок, табаско и ворчестер, когда-то придумали как лекарство против похмелья. 
           Сегодня, правда, коктейль стал таким популярным в барах, что и сам нередко вызывает недуг, который был создан лечить.\s
            """;

    @Test
    public void createBloodyMaryTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Кровавая Мэри");
        cocktail.setDescription(description);
        cocktail.setVolume(210);
        cocktail.setStrength(11);
        cocktail.setIngredients(getBloodyMaryIngredients());
        cocktail.setInstruments(getBloodyMaryInstruments());
        cocktail.setSteps(getBloodyMarySteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getBloodyMarySteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Наполни хайбол кубиками льда доверху", "", null, null));
        steps.add(new Step("Налей в шейкер лимонный сок 10 мл, томатный сок 120 мл и водку 50 мл", "", null, null));
        steps.add(new Step("Добавь табаско соус красный 3 дэш и ворчестер соус 3 дэш", "", null, null));
        steps.add(new Step("Приправь щепоткой сельдереевой соли и щепоткой черного перца молотого", "", null, null));
        steps.add(new Step("Наполни шейкер льдом, закрой и перекатывай несколько минут из одной руки в другую по вертикали", "", null, null));
        steps.add(new Step("Перелей через стрейнер в хайбол и укрась стеблем сельдерея", "", null, null));
        return steps;
    }

    private List<IngredientAndCount> getBloodyMaryIngredients() {
        Ingredient vodka = ingredientRepository.findByName("Водка");
        Ingredient tomatoJuice = ingredientRepository.findByName("Томатный сок");
        Ingredient lemonJuice = ingredientRepository.findByName("Лимонный сок");
        Ingredient celeryStalk = ingredientRepository.findByName("Сельдерей");
        Ingredient tabascoSauce = ingredientRepository.findByName("Табаско соус красный");
        Ingredient worcestershireSauce = ingredientRepository.findByName("Ворчестер соус");
        Ingredient celerySalt = ingredientRepository.findByName("Сельдереевая соль");
        Ingredient blackPepper = ingredientRepository.findByName("Черный перец молотый");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(vodka, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(tomatoJuice, 120, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(lemonJuice, 10, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(celeryStalk, 15, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(tabascoSauce, 1, UnitType.PIECES));
        ingredients.add(new IngredientAndCount(worcestershireSauce, 1, UnitType.PIECES));
        ingredients.add(new IngredientAndCount(celerySalt, 1, UnitType.PIECES));
        ingredients.add(new IngredientAndCount(blackPepper, 1, UnitType.PIECES));
        ingredients.add(new IngredientAndCount(crushedIce, 380, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getBloodyMaryInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument highballGlass = instrumentsRepository.findByName("Хайбол");
        Instrument strainer = instrumentsRepository.findByName("Стрейнер");
        Instrument shaker = instrumentsRepository.findByName("Шейкер");
        Instrument citrusPress = instrumentsRepository.findByName("Пресс для цитрусовых");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");
        Instrument straws = instrumentsRepository.findByName("Трубочки");

        instruments.add(highballGlass);
        instruments.add(strainer);
        instruments.add(shaker);
        instruments.add(citrusPress);
        instruments.add(jigger);
        instruments.add(straws);

        return instruments;
    }
}
