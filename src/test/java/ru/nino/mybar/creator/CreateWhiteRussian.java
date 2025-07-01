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
public class CreateWhiteRussian {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
         Хотите попробовать "Белый русский"? Это крепкий кофейно-сливочный коктейль на водке. Когда-то он считался женским, но после выхода фильма "Большой Лебовски" стал любимым напитком всех "чуваков". Если не смотрели фильм братьев Коэнов, то выпейте бокал "Белого русского" и срочно исправьте эту оплошность.\s
            """;

    @Test
    public void createWhiteRussianTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Белый русский");
        cocktail.setDescription(description);
        cocktail.setVolume(90);
        cocktail.setStrength(25);
        cocktail.setIngredients(getIngredients());
        cocktail.setInstruments(getInstruments());
        cocktail.setSteps(getSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Наполни рокс кубиками льда доверху", "", null, null));
        steps.add(new Step("Налей в бокал нежирные сливки 30 мл, кофейный ликер 30 мл и водку 30 мл", "", null, null));
        steps.add(new Step("Размешай коктейльной ложкой, пока не замерзнут стенки", "", null, null));

        return steps;
    }

    private List<IngredientAndCount> getIngredients() {
        Ingredient vodka = ingredientRepository.findByName("Водка");
        Ingredient coffeeLiqueur = ingredientRepository.findByName("Кофейный ликер");
        Ingredient lightCream = ingredientRepository.findByName("Нежирные сливки");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(vodka, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(coffeeLiqueur, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(lightCream, 30, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(crushedIce, 120, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        Instrument rocksGlass = instrumentsRepository.findByName("Рокс");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");
        Instrument barSpoon = instrumentsRepository.findByName("Коктейльная ложка");
        Instrument straws = instrumentsRepository.findByName("Трубочки");

        instruments.add(rocksGlass);
        instruments.add(jigger);
        instruments.add(barSpoon);
        instruments.add(straws);

        return instruments;
    }
}