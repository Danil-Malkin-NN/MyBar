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
public class CreateCosmopolitan {

    @Autowired
    CocktailRepositoryImpl cocktailRepository;

    @Autowired
    InstrumentsRepositoryImpl instrumentsRepository;

    @Autowired
    IngredientRepositoryImpl ingredientRepository;

    String description = """
            Хотите попробовать "Космополитен"? Это кисло-сладкий коктейль на основе цитрусовой водки с добавлением трипл-сека и клюквенного сока. 
            О нём знает каждый, кто хоть раз видел "Секс в большом городе". В сериале его в основном пьют женщины, 
            но на самом деле этот коктейль любят люди во всем мире вне зависимости от пола.\s
            """;

    @Test
    public void createCosmopolitanTest() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Космополитен");
        cocktail.setDescription(description);
        cocktail.setVolume(120);
        cocktail.setStrength(20);
        cocktail.setIngredients(getCosmopolitanIngredients());
        cocktail.setInstruments(getCosmopolitanInstruments());
        cocktail.setSteps(getCosmopolitanSteps());

        cocktailRepository.save(cocktail);
    }

    private List<Step> getCosmopolitanSteps() {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("Налей в шейкер лаймовый сок 10 мл, клюквенный сок 50 мл, ликер трипл сек 20 мл и цитрусовую водку 40 мл", "", null, null));
        steps.add(new Step("Наполни шейкер кубиками льда и взбей", "", null, null));
        steps.add(new Step("Перелей через стрейнер в охлажденный коктейльный бокал", "", null, null));
        steps.add(new Step("Зажги над коктейлем горелку и выжми на нее масло из апельсиновой цедры", "", null, null));

        return steps;
    }

    private List<IngredientAndCount> getCosmopolitanIngredients() {
        Ingredient citrusVodka = ingredientRepository.findByName("Цитрусовая водка");
        Ingredient tripleSec = ingredientRepository.findByName("Трипл сек");
        Ingredient cranberryJuice = ingredientRepository.findByName("Клюквенный сок");
        Ingredient limeJuice = ingredientRepository.findByName("Лаймовый сок");
        Ingredient orangeZest = ingredientRepository.findByName("Апельсиновая цедра");
        Ingredient crushedIce = ingredientRepository.findByName("Дробленый лед");

        List<IngredientAndCount> ingredients = new ArrayList<>();
        ingredients.add(new IngredientAndCount(citrusVodka, 40, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(tripleSec, 20, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(cranberryJuice, 50, UnitType.MILLILITER));
        ingredients.add(new IngredientAndCount(limeJuice, 10, UnitType.MILLILITER)); // исправлено с GRAM на MILLILITER
        ingredients.add(new IngredientAndCount(crushedIce, 200, UnitType.GRAM));
        ingredients.add(new IngredientAndCount(orangeZest, 1, UnitType.PIECES));

        return ingredients;
    }

    private List<Instrument> getCosmopolitanInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        Instrument cocktailGlass = instrumentsRepository.findByName("Коктейльный бокал");
        Instrument strainer = instrumentsRepository.findByName("Стрейнер");
        Instrument shaker = instrumentsRepository.findByName("Шейкер");
        Instrument citrusPress = instrumentsRepository.findByName("Пресс для цитрусовых");
        Instrument zester = instrumentsRepository.findByName("Нож для цедры");
        Instrument jigger = instrumentsRepository.findByName("Джиггер");
        Instrument burner = instrumentsRepository.findByName("Горелка");

        instruments.add(cocktailGlass);
        instruments.add(strainer);
        instruments.add(shaker);
        instruments.add(citrusPress);
        instruments.add(zester);
        instruments.add(jigger);
        instruments.add(burner);

        return instruments;
    }
}