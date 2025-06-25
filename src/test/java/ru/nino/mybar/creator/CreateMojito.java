package ru.nino.mybar.creator;

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

@SpringBootTest
@ActiveProfiles("prod")
public class CreateMojito {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
            Алкогольный коктейль на основе светлого рома и листьев мяты. Происходит с острова Куба, стал популярен в США в 1980-х. Коктейль входит в список «современной классики» международной ассоциации барменов (IBA) и классифицируется как лонг дринк[1].
            Мохито традиционно состоит из четырёх ингредиентов: газированная вода, сахар, лайм и мята. Для охлаждения напитка в него добавляют колотый лёд. В алкогольный мохито добавляют пятый ингредиент — ром.
            """;

    @Test
    public void createMojito() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Мохито");
        cocktail.setDescription(description);
        cocktail.setVolume(300);
        cocktail.setStrength(10);
        cocktail.setIngredients(getMojitoIngredients());
        cocktail.setInstruments(getMojitoInstruments());
        cocktail.setSteps(getMojitoSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getMojitoSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Положите лайм в хайбол и разомни мадлером, что бы получить лаймовый вкус", "", null, null));
        steps.add(new Step("Добавьте в стакан листья мяты, предварительно сжав их в руке, для выделения сока", "", null, null));
        steps.add(new Step("Добавьте дроблёный лёд", "", null, null));
        steps.add(new Step("Добавьте сахарный сироп и ром", "", null, null));
        steps.add(new Step("Долейте содовую до краёв и размешайте коктейль", "", null, null));
        steps.add(new Step("Если хотите украсить наденьте кружок лайма на край бокала или добавьте веточку мяты, а можно всё вместе", "", null, null));


        return steps;
    }

    private List<IngredientAndCount> getMojitoIngredients() {

        Ingredient rom = ingredientRepository.findByName("Белый ром");
        Ingredient sweetWater = ingredientRepository.findByName("Сахарный сироп");
        Ingredient soda = ingredientRepository.findByName("Содовая");
        Ingredient lime = ingredientRepository.findByName("Лайм");
        Ingredient mint = ingredientRepository.findByName("Мята");
        Ingredient crashIce = ingredientRepository.findByName("Дробленый лед");

        ArrayList<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(rom, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(sweetWater, 15, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(soda, 100, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(lime, 80, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(mint, 10, UnitType.PIECES));
        ingredients.add(new IngredientAndCount(crashIce, 200, UnitType.GRAM));

        return ingredients;
    }

    private List<Instrument> getMojitoInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument hybol = instrumentsRepository.findByName("Хайбол");
        Instrument madler = instrumentsRepository.findByName("Мадлер");
        Instrument jiger = instrumentsRepository.findByName("Джиггер");
        Instrument spun = instrumentsRepository.findByName("Коктейльная ложка");
        Instrument pipes = instrumentsRepository.findByName("Трубочки");


        instruments.add(hybol);
        instruments.add(madler);
        instruments.add(jiger);
        instruments.add(spun);
        instruments.add(pipes);

        return instruments;
    }

}
