package ru.nino.mybar.repository.impl;

import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.repository.NameFinderRepository;

import java.io.Serializable;
import java.lang.ScopedValue;
import java.util.List;

@Repository
public interface CocktailRepositoryImpl extends NameFinderRepository<Cocktail> {

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"ingredients", "ingredients.ingredient"})
    Page<Cocktail> findAll(Pageable pageable);

    @Query(value = """
            select cocktail.*
            from cocktail
                     left join cocktail_ingredients ci on cocktail.id = ci.cocktail_id
                     left join ingredient_and_count on ci.ingredients_id = ingredient_and_count.id
            where ingredient_and_count.ingredient_id in (?1)
            group by cocktail.id
            order by count(ingredient_and_count.ingredient_id) desc
            limit 10;
            """, nativeQuery = true)
    List<Cocktail> findByIngredientsList(List<Integer> ingreients);

    @Override
    Cocktail findByName(String s);

    List<Cocktail> findTop5ByNameLikeIgnoreCase(String name);

    @Query(value = """
                with ingredient_id_users as (
                    select ingredient_id as id
                    from keycloak.user_entity ue
                            inner join user_entity_ingredient uei on ue.id = uei.user_id 
                             left join ingredient i on uei.ingredient_id = i.id
                    where ue.email = ?1
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
            """, nativeQuery = true)
    List<Cocktail> getAvailableCocktails(String email);

    Page<Cocktail> findByNameContainingIgnoreCase(String name, Pageable pageable);



    @Query(value = """
        select c from User u
        left join u.favoriteCocktail c
        where u.email = ?1
        """)
    Page<Cocktail> findCocktailByUsersEmail(Pageable pageable, String email);
}
