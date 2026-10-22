package ru.nino.mybar.controller.mwc.adminpanel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.CocktailStatus;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;

@Controller
@RequestMapping("/page/AdminPanel/cocktails")
@RequiredArgsConstructor
public class CocktailModerationController {
    private final CocktailRepositoryImpl cocktailRepository;

    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("submissions", cocktailRepository.findByStatusOrderByIdAsc(CocktailStatus.PENDING));
        return "admin/cocktail-review";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        updateStatus(id, CocktailStatus.APPROVED);
        return "redirect:/page/AdminPanel/cocktails/review";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        updateStatus(id, CocktailStatus.REJECTED);
        return "redirect:/page/AdminPanel/cocktails/review";
    }

    private void updateStatus(Long id, CocktailStatus status) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Коктейль не найден"));
        if (cocktail.getStatus() != CocktailStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Рецепт уже рассмотрен");
        }
        cocktail.setStatus(status);
        cocktailRepository.save(cocktail);
    }
}
