package ru.nino.mybar.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.CocktailRating;

import java.util.List;
import java.util.Optional;

@Repository
public interface CocktailRatingRepository extends JpaRepository<CocktailRating, Long> {
    Optional<CocktailRating> findByCocktailIdAndUserIdentifier(Long cocktailId, String userIdentifier);

    List<CocktailRating> findByCocktailId(Long cocktailId);

    @Query(value = """
            select c.*
            from cocktail c
            join cocktail_rating r on r.cocktail_id = c.id
            where c.status = 'APPROVED' and c.submitted_by is not null
            group by c.id
            order by avg(r.score) desc, count(r.id) desc, c.name asc
            limit 10
            """, nativeQuery = true)
    List<Cocktail> findTopRatedAuthorCocktails();
}
