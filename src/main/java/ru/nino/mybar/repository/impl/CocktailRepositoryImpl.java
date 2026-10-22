package ru.nino.mybar.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.CocktailStatus;
import ru.nino.mybar.repository.NameFinderRepository;

import java.util.List;

@Repository
public interface CocktailRepositoryImpl extends NameFinderRepository<Cocktail> {

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"ingredients", "ingredients.ingredient"})
    Page<Cocktail> findAll(Pageable pageable);

    Page<Cocktail> findByStatus(CocktailStatus status, Pageable pageable);

    @Query(value = """
            select cocktail.*
            from cocktail
                     left join cocktail_ingredients ci on cocktail.id = ci.cocktail_id
                     left join ingredient_and_count on ci.ingredients_id = ingredient_and_count.id
            where cocktail.status = 'APPROVED'
              and ingredient_and_count.ingredient_id in (?1)
            group by cocktail.id
            order by count(ingredient_and_count.ingredient_id) desc
            limit 10;
            """, nativeQuery = true)
    List<Cocktail> findByIngredientsList(List<Integer> ingreients);

    @Override
    Cocktail findByName(String s);

    List<Cocktail> findTop5ByNameLikeIgnoreCase(String name);

    List<Cocktail> findTop10ByNameContainingIgnoreCaseAndStatusOrderByNameAsc(String name, CocktailStatus status);

    @Query(value = """
                with ingredient_id_users as (
                    select ingredient_id as id
                    from user_data ud
                            inner join user_ingredient ui on ud.id = ui.user_id
                    where ud.email = ?1
                )
                select
                    cocktail.*
                from cocktail
                         left join cocktail_ingredients ci on cocktail.id = ci.cocktail_id
                         left join ingredient_and_count on ci.ingredients_id = ingredient_and_count.id
                where cocktail.status = 'APPROVED'
                  and ingredient_and_count.ingredient_id in (select id from ingredient_id_users)
                group by cocktail.id
                order by count(ingredient_and_count.ingredient_id) desc
                limit 10;
            """, nativeQuery = true)
    List<Cocktail> getAvailableCocktails(String email);
    Page<Cocktail> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
            select c from Cocktail c
            where c.status = ru.nino.mybar.entity.CocktailStatus.APPROVED
              and (:name = '' or lower(c.name) like lower(concat('%', :name, '%')))
              and (:baseAlcohol = '' or lower(c.baseAlcohol) = lower(:baseAlcohol))
              and (:minStrength is null or c.strength >= :minStrength)
              and (:maxStrength is null or c.strength <= :maxStrength)
              and (:minVolume is null or c.volume >= :minVolume)
              and (:maxVolume is null or c.volume <= :maxVolume)
            """)
    Page<Cocktail> searchApprovedCatalog(@Param("name") String name,
                                         @Param("baseAlcohol") String baseAlcohol,
                                         @Param("minStrength") Integer minStrength,
                                         @Param("maxStrength") Integer maxStrength,
                                         @Param("minVolume") Integer minVolume,
                                         @Param("maxVolume") Integer maxVolume,
                                         Pageable pageable);

    List<Cocktail> findByStatusOrderByNameAsc(CocktailStatus status);

    List<Cocktail> findByStatusAndSubmittedByOrderByIdDesc(CocktailStatus status, String submittedBy);

    List<Cocktail> findBySubmittedByOrderByIdDesc(String submittedBy);

    List<Cocktail> findByStatusOrderByIdAsc(CocktailStatus status);

    java.util.Optional<Cocktail> findByIdAndStatus(Long id, CocktailStatus status);

    java.util.Optional<Cocktail> findFirstByNameAndStatus(String name, CocktailStatus status);

    List<Cocktail> findByIdInAndStatus(List<Long> ids, CocktailStatus status);

    @Query("select distinct c.baseAlcohol from Cocktail c where c.status = ru.nino.mybar.entity.CocktailStatus.APPROVED and c.baseAlcohol <> '' order by c.baseAlcohol")
    List<String> findDistinctApprovedBaseAlcohols();
    @Query(value = """
        select c from User u
        left join u.favoriteCocktail c
        where u.email = ?1
        """)
    Page<Cocktail> findCocktailByUsersEmail(Pageable pageable, String email);
}
