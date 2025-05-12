package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.model.CocktailsModel;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.service.UserBarService;
import ru.nino.mybar.utils.UserUtils;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("page")
@RequiredArgsConstructor
public class MyBarPageController {

    private final UserBarService userBarService;

    @GetMapping("/my/bar")
    public String page(Principal user, Model model, @PageableDefault(size = 10) Pageable pageable) {
        String email = UserUtils.getEmail((OAuth2AuthenticationToken) user);

        List<IngredientDto> allUserIngredients = userBarService.findAllUserIngredients(email);

        List<CocktailUserIngredientsDto> availableCocktails = userBarService.getAvailableCocktails(email);
        Page<CocktailsModel> favorites = userBarService.getUserFavoritesCocktails(pageable, email);

        model.addAttribute("ingredients", allUserIngredients);
        model.addAttribute("cocktails", availableCocktails);

        model.addAttribute("favorites", favorites);


        return "MyBarPage";
    }


}
