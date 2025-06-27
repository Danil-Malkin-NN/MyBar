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
public class CreateBlueLagoon {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
            Алкогольный коктейль голубого цвета со сладким вкусом газировки и лёгкими цитрусовыми нотками\s
            """;

    @Test
    public void createAperolShpritcTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Голубая Лагуна");
        cocktail.setDescription(description);
        cocktail.setVolume(240);
        cocktail.setStrength(15);
        cocktail.setIngredients(getMojitoIngredients());
        cocktail.setInstruments(getMojitoInstruments());
        cocktail.setSteps(getMojitoSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getMojitoSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Наполнить шейкер льдом, влить ликёр и водку, хорошенько встряхнуть", "", null, null));
        steps.add(new Step("Хайбол или бокал формы харрикейн наполнить льдом.", "", null, null));
        steps.add(new Step("Налить полученную смесь из шейкера в бокал.", "", null, null));
        steps.add(new Step("Добавить газировку.", "", null, null));
        steps.add(new Step("Украсить край бокала кусочком ананаса (вместо ананаса можно использовать дольку лимона).", "", null, null));


        return steps;
    }

    private List<IngredientAndCount> getMojitoIngredients() {

        Ingredient vodka = ingredientRepository.findByName("Водка");
        Ingredient blueKur = ingredientRepository.findByName("Ликер Блю Кюрасао");
        Ingredient sprait = ingredientRepository.findByName("Спрайт");
        Ingredient limen = ingredientRepository.findByName("лимонный сок");
        Ingredient pineapple = ingredientRepository.findByName("Ананас");

        Ingredient crashIce = ingredientRepository.findByName("Дробленый лед");

        ArrayList<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(vodka, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(blueKur, 10, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(sprait, 100, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(limen, 20, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(crashIce, 200, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(pineapple, 30, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getMojitoInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument hybol = instrumentsRepository.findByName("Харрикейн");
        Instrument jiger = instrumentsRepository.findByName("Джиггер");
        Instrument spun = instrumentsRepository.findByName("Коктейльная ложка");
        Instrument pipes = instrumentsRepository.findByName("Трубочки");

        instruments.add(hybol);
        instruments.add(jiger);
        instruments.add(spun);
        instruments.add(pipes);

        return instruments;
    }
}
