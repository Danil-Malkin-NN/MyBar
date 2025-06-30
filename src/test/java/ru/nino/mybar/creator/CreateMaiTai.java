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
public class CreateMaiTai {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
            Хотите попробовать "Май тай"? Это крепкий лонг на выдержанном роме с ликером драй оранж и двумя сиропами - 
            сахарным и миндальным. Благодаря соку лайма коктейль получается кислым и очень вкусным. 
            Таитяне, которые дали коктейлю название, когда впервые попробовали "Май тай", 
            сказали, что он "не от мира сего".\s
            """;

    @Test
    public void createMaiTaiTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Май Тай");
        cocktail.setDescription(description);
        cocktail.setVolume(250);
        cocktail.setStrength(25);
        cocktail.setIngredients(getMaiTaiIngredients());
        cocktail.setInstruments(getMaiTaiInstruments());
        cocktail.setSteps(getMaiTaiSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getMaiTaiSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Наполни рокс дробленым льдом доверху", "", null, null));
        steps.add(new Step("Налей в шейкер лаймовый сок 20 мл, сахарный сироп 10 мл, миндальный сироп 10 мл, ликер драй оранж 20 мл и выдержанный ром 50 мл", "", null, null));
        steps.add(new Step("Наполни шейкер кубиками льда и взбей", "", null, null));
        steps.add(new Step("Перелей через стрейнер в рокс", "", null, null));
        steps.add(new Step("Досыпь немного дробленого льда", "", null, null));
        steps.add(new Step("Укрась долькой лайма и веточкой мяты", "", null, null));

        return steps;
    }

    private List<IngredientAndCount> getMaiTaiIngredients() {
        Ingredient agedRum = ingredientRepository.findByName("Выдержанный ром");
        Ingredient dryCuracao = ingredientRepository.findByName("Ликер драй оранж");
        Ingredient sugarSyrup = ingredientRepository.findByName("Сахарный сироп");
        Ingredient orgeatSyrup = ingredientRepository.findByName("Миндальный сироп");
        Ingredient limeJuice = ingredientRepository.findByName("Лаймовый сок");
        Ingredient lime = ingredientRepository.findByName("Лайм");
        Ingredient mint = ingredientRepository.findByName("Мята");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");
        Ingredient cubeIce = ingredientRepository.findByName("Лед в кубиках");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(agedRum, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(dryCuracao, 20, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(sugarSyrup, 10, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(orgeatSyrup, 10, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(limeJuice, 20, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(lime, 30, UnitType.GRAM)); // долька лайма
        ingredients.add(new IngredientAndCount(mint, 1, UnitType.GRAM)); // веточка мяты
        ingredients.add(new IngredientAndCount(crushedIce, 150, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(cubeIce, 200, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getMaiTaiInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument rocksGlass = instrumentsRepository.findByName("Рокс");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");
        Instrument strainer = instrumentsRepository.findByName("Стрейнер");
        Instrument shaker = instrumentsRepository.findByName("Шейкер");
        Instrument citrusPress = instrumentsRepository.findByName("Пресс для цитрусовых");
        Instrument straws = instrumentsRepository.findByName("Трубочки");

        instruments.add(rocksGlass);
        instruments.add(jigger);
        instruments.add(strainer);
        instruments.add(shaker);
        instruments.add(citrusPress);
        instruments.add(straws);

        return instruments;
    }
}