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
public class CreateLongIslandIcedTea {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
         Хотите попробовать "Лонг айленд айс ти"? Это очень крепкий лонг на пяти видах алкоголя. Кроме водки, джина, рома, текилы и цитрусового ликёра, туда добавляют колу и лимонный сок, так что на вкус он получается сладковато-кислым. Коктейль придумали во времена сухого закона в Америке, маскируя его под чай, поэтому иногда его до сих пор подают в чайниках или френч-прессах.\s
            """;

    @Test
    public void createLongIslandIcedTeaTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Лонг Айленд Айс Ти");
        cocktail.setDescription(description);
        cocktail.setVolume(300);
        cocktail.setStrength(22);
        cocktail.setIngredients(getIngredients());
        cocktail.setInstruments(getInstruments());
        cocktail.setSteps(getSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Наполни хайбол кубиками льда доверху", "", null, null));
        steps.add(new Step("Налей лимонный сок 30 мл, сахарный сироп 30 мл и ликер трипл сек 30 мл", "", null, null));
        steps.add(new Step("Добавь водку 30 мл, джин 30 мл, белый ром 30 мл и серебряную текилу 30 мл", "", null, null));
        steps.add(new Step("Долей колу доверху и аккуратно размешай коктейльной ложкой", "", null, null));
        steps.add(new Step("Укрась долькой лимона", "", null, null));

        return steps;
    }

    private List<IngredientAndCount> getIngredients() {
        Ingredient vodka = ingredientRepository.findByName("Водка");
        Ingredient gin = ingredientRepository.findByName("Лондонский сухой джин");
        Ingredient whiteRum = ingredientRepository.findByName("Белый ром");
        Ingredient silverTequila = ingredientRepository.findByName("Серебряная текила");
        Ingredient tripleSec = ingredientRepository.findByName("Трипл сек");
        Ingredient sugarSyrup = ingredientRepository.findByName("Сахарный сироп");
        Ingredient lemonJuice = ingredientRepository.findByName("Лимонный сок");
        Ingredient cola = ingredientRepository.findByName("Кола");
        Ingredient lemon = ingredientRepository.findByName("Лимон");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(vodka, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(gin, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(whiteRum, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(silverTequila, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(tripleSec, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(sugarSyrup, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(lemonJuice, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(cola, 100, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(lemon, 40, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(crushedIce, 300, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        Instrument highballGlass = instrumentsRepository.findByName("Хайбол");
        Instrument citrusPress = instrumentsRepository.findByName("Пресс для цитрусовых");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");
        Instrument barSpoon = instrumentsRepository.findByName("Коктейльная ложка");
        Instrument straws = instrumentsRepository.findByName("Трубочки");

        instruments.add(highballGlass);
        instruments.add(jigger);
        instruments.add(barSpoon);
        instruments.add(straws);
        instruments.add(citrusPress);

        return instruments;
    }
}