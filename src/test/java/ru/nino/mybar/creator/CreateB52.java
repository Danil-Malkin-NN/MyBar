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
public class CreateB52 {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
            Хотите попробовать "Б-52"? Это крепкий сладкий шот на кофейном ликёре с добавлением айриш крима и трипл сека. 
            На вкус он сладкий и сливочно-кофейный. Верхний слой шота поджигается, и коктейль нужно быстро выпить через трубочку: 
            не только вкусно, но и зрелищно.\s
            """;

    @Test
    public void createB52Test() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Б-52");
        cocktail.setDescription(description);
        cocktail.setVolume(45);
        cocktail.setStrength(25);
        cocktail.setIngredients(getB52Ingredients());
        cocktail.setInstruments(getB52Instruments());
        cocktail.setSteps(getB52Steps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getB52Steps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Налей в стопку кофейный ликер 15 мл", "", null, null));
        steps.add(new Step("Используя коктейльную ложку, уложи слой айриш крим 15 мл", "", null, null));
        steps.add(new Step("Аккуратно уложи слой ликера трипл сек 15 мл", "", null, null));
        steps.add(new Step("Подожги верхний слой, подавай с трубочками", "", null, null));

        return steps;
    }

    private List<IngredientAndCount> getB52Ingredients() {
        Ingredient coffeeLiqueur = ingredientRepository.findByName("Кофейный ликер");
        Ingredient irishCream = ingredientRepository.findByName("Айриш крим");
        Ingredient tripleSec = ingredientRepository.findByName("Трипл сек");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(coffeeLiqueur, 15, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(irishCream, 15, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(tripleSec, 15, UnitType.MILLILITER));

        return ingredients;
    }

    private List<Instrument> getB52Instruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument shotGlass = instrumentsRepository.findByName("Стопка");
        Instrument burner = instrumentsRepository.findByName("Горелка");
        Instrument barSpoon = instrumentsRepository.findByName("Коктейльная ложка");
        Instrument straws = instrumentsRepository.findByName("Трубочки");

        instruments.add(shotGlass);
        instruments.add(burner);
        instruments.add(barSpoon);
        instruments.add(straws);

        return instruments;
    }
}