package ru.nino.mybar.controller.crud.adminpanel;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.IngredientAndCount;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.IngredientAndCountRepositoryImpl;


@Controller
@RequestMapping("/page/AdminPanel")
@RequiredArgsConstructor
public class AdminPanelCockteil {

    @Autowired
    private final CocktailRepositoryImpl cocktailrepository;
    private final IngredientAndCountRepositoryImpl ingredientAndCountRepository;


    @GetMapping("/cocktaill/{id}")
    public String editCocktailForm(@PathVariable Long id, Model model) {
        Cocktail cocktail = cocktailrepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Коктель не найден"));
        model.addAttribute("cocktail", cocktail);
        return "edit-cocktail";
    }

    @PostMapping("/cocktail/save")
    public String saveCocktail(Cocktail cocktail, IngredientAndCount ingredientAndCount) {
        ingredientAndCountRepository.save(ingredientAndCount);
        cocktailrepository.save(cocktail);
        return "redirect:/page";
    }

}
