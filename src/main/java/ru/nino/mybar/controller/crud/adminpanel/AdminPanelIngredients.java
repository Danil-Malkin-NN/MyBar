package ru.nino.mybar.controller.crud.adminpanel;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;


@Controller
@RequestMapping("/page/AdminPanel")
@RequiredArgsConstructor
public class AdminPanelIngredients {

@Autowired
private final IngredientRepositoryImpl ingredientRepository;


    @GetMapping("/ingredients/{id}")
    public String editIngredient(@PathVariable Long id, Model model) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Коктель не найден"));
        model.addAttribute("ingredient", ingredient);
        return "edit-Ingridients";

    }

    @PostMapping("/ingredients/save")
    public String saveIngredient(@ModelAttribute Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return "redirect:/page/ingredients";

    }

}
