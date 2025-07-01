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
public class CreateAperol {

	public String description = """
			популярный летний коктейль, который готовится на основе игристого вина (обычно Просекко), ликера Апероль и содовой. Он известен своим оранжевым цветом и освежающим вкусом, часто подается в винных бокалах со льдом и долькой апельсина.
			""";
	@Autowired
	CocktailRepositoryImpl cocktailRepository;
	@Autowired
	IngredientRepositoryImpl ingredientRepository;
	@Autowired
	InstrumentsRepositoryImpl instrumentsRepository;

	@Test
	public void createAperol() {
		Cocktail cocktail = new Cocktail();
		cocktail.setName("Апероль Шприц");
		cocktail.setDescription(description);
		cocktail.setStrength(10);
		cocktail.setVolume(180);
		cocktail.setIngredients(getAperolIngredients());
		cocktail.setInstruments(getAperolInstruments());
		cocktail.setSteps(getAperolSteps());

		cocktailRepository.save(cocktail);
	}

	private List<IngredientAndCount> getAperolIngredients() {
		ArrayList<IngredientAndCount> ingredientAndCounts = new ArrayList<>();

		Ingredient proseco = ingredientRepository.findByName("Просекко");
		ingredientAndCounts.add(new IngredientAndCount(proseco, 90, UnitType.MILLILITER));

		Ingredient aperol = ingredientRepository.findByName("Апероль");
		ingredientAndCounts.add(new IngredientAndCount(aperol, 60, UnitType.MILLILITER));

		Ingredient sodovaya = ingredientRepository.findByName("Содовая");
		ingredientAndCounts.add(new IngredientAndCount(sodovaya, 30, UnitType.MILLILITER));

		Ingredient orange = ingredientRepository.findByName("Апельсин");
		ingredientAndCounts.add(new IngredientAndCount(orange, 1, UnitType.PIECES));

		Ingredient ice = ingredientRepository.findByName("Ледяная сфера");
		ingredientAndCounts.add(new IngredientAndCount(ice, 100, UnitType.GRAM));

		return ingredientAndCounts;
	}

	private List<Instrument> getAperolInstruments() {
		List<Instrument> instruments = new ArrayList<>();

		Instrument vine = instrumentsRepository.findByName("Бокал для вина");
		instruments.add(vine);

		Instrument djiger = instrumentsRepository.findByName("Джиггер");
		instruments.add(djiger);

		Instrument pipes = instrumentsRepository.findByName("Трубочки");
		instruments.add(pipes);

		return instruments;
	}

	private List<Step> getAperolSteps() {
		List<Step> steps = new ArrayList<>();

		steps.add(new Step("Наполни бокал для вина льдом", "Охлаждаем бокал", null, null));
		steps.add(new Step("Налей в бокал просекко 100 мл и апероль 100 мл", "", null, null));
		steps.add(new Step("Добавь сплэш содовой и размешай коктейльной ложкой", "", null, null));
		steps.add(new Step("Укрась долькой апельсина", "", null, null));

		return steps;
	}

}
