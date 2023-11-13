package ru.nino.mybar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.service.UserBarService;

import java.security.Principal;
import java.util.List;

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
    public List<CocktailDto> getAvailableCocktails(Principal user) {
        return userBarService.getAvailableCocktails(user.getName());
    }

    @PostMapping("ingredients/add")
    public List<IngredientDto> addIngredient(Principal user, Integer ingredientsId) {

        return userBarService.addIngredient(user.getName(),ingredientsId);
    }

    @DeleteMapping("ingredients/delete")
    public List<IngredientDto> deleteIngredient(Principal user, Integer ingredientsId) {

        return userBarService.deleteIngredientsFromMyBar(user.getName(),ingredientsId);
    }


}
