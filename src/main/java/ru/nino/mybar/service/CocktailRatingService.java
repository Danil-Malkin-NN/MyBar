package ru.nino.mybar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.CocktailRating;
import ru.nino.mybar.entity.CocktailStatus;
import ru.nino.mybar.repository.impl.CocktailRatingRepository;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CocktailRatingService {
    private final CocktailRatingRepository ratingRepository;
    private final CocktailRepositoryImpl cocktailRepository;

    @Transactional
    public RatingSummary rate(Long cocktailId, String userIdentifier, int score) {
        if (userIdentifier == null || userIdentifier.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (score < 1 || score > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Оценка должна быть от 1 до 5.");
        }
        Cocktail cocktail = cocktailRepository.findByIdAndStatus(cocktailId, CocktailStatus.APPROVED)
                .filter(item -> item.getSubmittedBy() != null && !item.getSubmittedBy().isBlank())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Авторский рецепт не найден."));
        CocktailRating rating = ratingRepository.findByCocktailIdAndUserIdentifier(cocktailId, userIdentifier)
                .orElseGet(CocktailRating::new);
        rating.setCocktail(cocktail);
        rating.setUserIdentifier(userIdentifier);
        rating.setScore(score);
        ratingRepository.save(rating);
        return summary(cocktailId, userIdentifier);
    }

    @Transactional(readOnly = true)
    public RatingSummary summary(Long cocktailId, String userIdentifier) {
        List<CocktailRating> ratings = ratingRepository.findByCocktailId(cocktailId);
        double average = ratings.stream().mapToInt(CocktailRating::getScore).average().orElse(0);
        int currentScore = ratings.stream()
                .filter(rating -> userIdentifier != null && userIdentifier.equals(rating.getUserIdentifier()))
                .mapToInt(CocktailRating::getScore)
                .findFirst().orElse(0);
        return new RatingSummary(average, ratings.size(), currentScore);
    }

    @Transactional(readOnly = true)
    public List<Cocktail> topRatedAuthorCocktails() {
        return ratingRepository.findTopRatedAuthorCocktails();
    }

    public record RatingSummary(double average, int count, int currentScore) {
    }
}
