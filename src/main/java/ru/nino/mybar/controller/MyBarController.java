package ru.nino.mybar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.service.UserBarService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MyBarController {

    private final UserBarService userBarService;

    @GetMapping("ingredients")
    public List<IngredientDto> getMyIngredients(Principal user) {
        String name = user.getName();
//        ingredientRepository.findAllByUserName(user.getName());
        return userBarService.findAllUserIngredients(name);
    }

    @GetMapping("availeble/coctails")
    public List<CocktailDto> getAvailableCocktails(Principal user){
        return userBarService.getAvailableCocktails(user.getName());

    }


}
