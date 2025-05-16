package ru.nino.mybar.controller.crud.adminpanel;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
public class AdminpanelCoctel {
    @Autowired
    private CocktailRepositoryImpl cocktailDto;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cocktaill/{id}")
    public String editCocktailForm(@PathVariable Long id, Model model) {
        model.addAttribute("cocktail", cocktailDto.findById(id).orElse(null));
        return "editcocktail";
    }

    @PostMapping("/cocktaill/save")
    public String saveCocktail(Cocktail cocktail) {
        cocktailDto.save(cocktail);
        return "redirect:/page";
    }





}
