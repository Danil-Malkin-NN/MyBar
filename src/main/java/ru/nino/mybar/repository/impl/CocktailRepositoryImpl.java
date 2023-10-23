package ru.nino.mybar.repository.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.repository.DefaultRepository;

import java.util.List;

@Repository
public interface CocktailRepositoryImpl extends DefaultRepository<Cocktail> {

    @Query(value = "select cocktail.* from cocktail\n" +
            "left join cocktail_ingredients ci on cocktail.id = ci.cocktail_id\n" +
            "                   left join ingredient_and_count on ci.ingredients_id = ingredient_and_count.id\n" +
            "where ingredient_and_count.ingredient_id in (\n" +
            "    select ingredient_id from user_info\n" +
            "                                  left join custom_user cu on user_info.user_id = cu.id\n" +
            "                                  left join user_info_ingredient uii on user_info.id = uii.user_info_id\n" +
            "                                  left join ingredient i on uii.ingredient_id = i.id\n" +
            "    );", nativeQuery = true)
    List<Cocktail> getAvailableCocktails(String name);
}
