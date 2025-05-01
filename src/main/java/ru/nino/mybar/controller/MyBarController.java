package ru.nino.mybar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.service.UserBarService;

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

    @PostMapping("ingredients/add")
    public List<IngredientDto> addIngredient(Principal user, Long ingredientsId) {

        return userBarService.addIngredient(user.getName(),ingredientsId);
    }

    @DeleteMapping("ingredients/delete")
    public List<IngredientDto> deleteIngredient(Principal user, Long ingredientsId) {

        return userBarService.deleteIngredientsFromMyBar(user.getName(),ingredientsId);
    }


}
