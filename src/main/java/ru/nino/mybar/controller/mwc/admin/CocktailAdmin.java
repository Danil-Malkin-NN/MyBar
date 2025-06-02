package ru.nino.mybar.controller.mwc.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.service.IngredientServiceImpl;
import ru.nino.mybar.service.InstrumentServiceImpl;

@Controller
@RequiredArgsConstructor
public class CocktailAdmin {

    private final IngredientServiceImpl ingredientService;
    private final InstrumentServiceImpl instrumentService;

    @GetMapping("/cocktails/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ingredients", ingredientService.getAll());
        model.addAttribute("instruments", instrumentService.getAll());
        model.addAttribute("cocktailDto", new CocktailDto());
        return "admin/create-cocktail";
    }
}

