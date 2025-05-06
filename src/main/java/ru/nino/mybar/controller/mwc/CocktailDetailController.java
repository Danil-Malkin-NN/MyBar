package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.service.CocktailServiceImpl;

@Controller
@RequestMapping("page")
@RequiredArgsConstructor
public class CocktailDetailController {

    private final CocktailServiceImpl cocktailService;

    @GetMapping("/cocktails/{id}")
    public String cocktailDetails(@PathVariable Long id, Model model) {
        CocktailDto cocktail = cocktailService.getById(id);
        model.addAttribute("cocktail", cocktail);
        return "cocktail-detail";
    }


}
