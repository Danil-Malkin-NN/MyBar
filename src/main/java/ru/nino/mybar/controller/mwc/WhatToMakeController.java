package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nino.mybar.service.IngredientServiceImpl;
import ru.nino.mybar.service.PartyPlanningService;

import java.util.List;

@Controller
@RequestMapping("/page/what-to-make")
@RequiredArgsConstructor
public class WhatToMakeController {
    private final IngredientServiceImpl ingredientService;
    private final PartyPlanningService partyPlanningService;

    @GetMapping
    public String page() {
        return "what-to-make";
    }

    @GetMapping("/ingredients")
    @ResponseBody
    public List<IngredientOption> searchIngredients(@RequestParam(defaultValue = "") String query) {
        if (query.trim().length() < 2) {
            return List.of();
        }
        return ingredientService.searchIngredients(query).stream()
                .map(ingredient -> new IngredientOption(ingredient.getId(), ingredient.getName()))
                .toList();
    }

    @GetMapping("/recommendations")
    @ResponseBody
    public List<PartyPlanningService.PartyRecommendation> recommendations(
            @RequestParam(required = false) List<Long> ingredientIds) {
        return partyPlanningService.recommendCocktails(ingredientIds, 4);
    }

    public record IngredientOption(Long id, String name) {
    }
}
