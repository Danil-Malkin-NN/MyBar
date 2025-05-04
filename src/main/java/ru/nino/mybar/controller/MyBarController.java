package ru.nino.mybar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.service.UserBarService;
import ru.nino.mybar.utils.UserUtils;

import java.security.Principal;
import java.util.List;

@Tag(name = "Контроллер бара пользователя")
@RestController
@RequestMapping("my")
@RequiredArgsConstructor
public class MyBarController {

    private final UserBarService userBarService;

    @GetMapping("ingredients")
    public List<IngredientDto> getMyIngredients(Principal user) {
        String name = user.getName();
        return userBarService.findAllUserIngredients(name);
    }

    @GetMapping("available/cocktails")
    public List<CocktailUserIngredientsDto> getAvailableCocktails(Principal user) {
        return userBarService.getAvailableCocktails(user.getName());
    }

    @PostMapping("ingredients/add/{ingredientsId}")
    public List<IngredientDto> addIngredient(Principal user, @PathVariable(value = "ingredientsId") Long ingredientsId) {
        String email = UserUtils.getEmail((OAuth2AuthenticationToken) user);

        return userBarService.addIngredient(email, ingredientsId);
    }

    @DeleteMapping("ingredients/delete/{ingredientsId}")
    public List<IngredientDto> deleteIngredient(Principal user, @PathVariable Long ingredientsId) {
        String email = UserUtils.getEmail((OAuth2AuthenticationToken) user);

        return userBarService.deleteIngredientsFromMyBar(email, ingredientsId);
    }

}
