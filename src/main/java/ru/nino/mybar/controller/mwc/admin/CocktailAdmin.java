package ru.nino.mybar.controller.mwc.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.service.CocktailServiceImpl;
import ru.nino.mybar.service.IngredientServiceImpl;
import ru.nino.mybar.service.InstrumentServiceImpl;

@Controller
@RequestMapping("admin/cocktails")
@RequiredArgsConstructor
public class CocktailAdmin {

    private final IngredientServiceImpl ingredientService;
    private final InstrumentServiceImpl instrumentService;
    private final CocktailServiceImpl cocktailService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ingredients", ingredientService.getAll());
        model.addAttribute("instruments", instrumentService.getAll());
        model.addAttribute("cocktailDto", new CocktailDto());
        return "admin/create-cocktail";
    }

    @PostMapping("/create")
    public String createCocktail(@ModelAttribute CocktailDto cocktailDto) {
        cocktailService.create(cocktailDto);
        return "redirect:/cocktails";
    }

}

