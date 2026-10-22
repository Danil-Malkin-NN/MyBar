package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.CocktailStatus;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.service.CocktailRatingService;
import ru.nino.mybar.service.CocktailServiceImpl;

@Controller
@RequestMapping("page")
@RequiredArgsConstructor
public class CocktailDetailController {

    private final CocktailServiceImpl cocktailService;
    private final CocktailRepositoryImpl cocktailRepository;
    private final CocktailRatingService cocktailRatingService;

    @GetMapping("/cocktails/{id}")
    public String cocktailDetails(@PathVariable Long id, Model model, Authentication authentication) {
        CocktailDto cocktail = cocktailService.getById(id);
        model.addAttribute("cocktail", cocktail);
        var entity = cocktailRepository.findByIdAndStatus(id, CocktailStatus.APPROVED).orElseThrow();
        boolean authorCocktail = entity.getSubmittedBy() != null && !entity.getSubmittedBy().isBlank();
        String userIdentifier = authentication == null ? null : authentication.getName();
        model.addAttribute("authorCocktail", authorCocktail);
        model.addAttribute("rating", cocktailRatingService.summary(id, userIdentifier));
        model.addAttribute("canRate", authorCocktail && authentication != null
                && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken));
        return "cocktail-detail";
    }


}
