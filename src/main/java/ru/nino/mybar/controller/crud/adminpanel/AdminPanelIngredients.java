package ru.nino.mybar.controller.crud.adminpanel;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nino.mybar.entity.Ingredient;
import ru.nino.mybar.repository.impl.IngredientRepositoryImpl;


@Controller
@RequestMapping("/page/AdminPanel")
@RequiredArgsConstructor
public class AdminPanelIngredients {

@Autowired
private final IngredientRepositoryImpl ingredientRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ingredients/{id}")
    public String EditIngredient(@PathVariable Long id, Model model) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(null);
        model.addAttribute("ingredient", ingredient);

        return "editingred";

    }

    @PostMapping("/ingredients/save")
    public String saveIngredient(@ModelAttribute Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return "redirect:/page/ingredients";

    }

}
