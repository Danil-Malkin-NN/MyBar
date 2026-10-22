package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nino.mybar.service.CocktailRatingService;

@Controller
@RequestMapping("/page/cocktails")
@RequiredArgsConstructor
public class CocktailRatingController {
    private final CocktailRatingService cocktailRatingService;

    @PostMapping("/{id}/rating")
    public String rate(@PathVariable Long id, @RequestParam int score, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        cocktailRatingService.rate(id, authentication.getName(), score);
        return "redirect:/page/cocktails/" + id;
    }
}
