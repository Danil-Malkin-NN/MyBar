package ru.nino.mybar.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.repository.NameFinderRepository;

import java.util.List;

@Repository
public interface CocktailRepositoryImpl extends NameFinderRepository<Cocktail> {

    @Query(value = """
                with ingredient_id_users as (
                    select ingredient_id as id
                    from user_info
                             left join custom_user cu on user_info.user_id = cu.id
                             left join user_info_ingredient uii on user_info.id = uii.user_info_id
                             left join ingredient i on uii.ingredient_id = i.id
                    where cu.name = ?1
                )
                select
                    cocktail.*
                from cocktail
                         left join cocktail_ingredients ci on cocktail.id = ci.cocktail_id
                         left join ingredient_and_count on ci.ingredients_id = ingredient_and_count.id
                where ingredient_and_count.ingredient_id in (select id from ingredient_id_users)
                group by cocktail.id
                order by count(ingredient_and_count.ingredient_id) desc
                limit 10;
            """,
            nativeQuery = true)
    List<Cocktail> getAvailableCocktails(String name);

    List<Cocktail> findTop5ByNameLikeIgnoreCase(String name);

    @Override
    Cocktail findByName(String s);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"ingredients", "ingredients.ingredient"})
    Page<Cocktail> findAll(Pageable pageable);


}
