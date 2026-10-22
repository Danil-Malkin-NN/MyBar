package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nino.mybar.dto.model.CocktailsModel;
import ru.nino.mybar.service.CocktailServiceImpl;
import ru.nino.mybar.utils.UserUtils;

import java.security.Principal;

@Controller
@RequestMapping("page")
@RequiredArgsConstructor
public class MainPageController {

    private final CocktailServiceImpl service;

    @GetMapping()
    public String mainPage(@PageableDefault(size = 100) Pageable pageable, Model model,
                           @RequestParam(required = false, defaultValue = "") String cocktailName,
                           @RequestParam(defaultValue = "evening") String mood,
                           Principal principal) {
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        String selectedMood = switch (mood) {
            case "morning", "evening", "night" -> mood;
            default -> "evening";
        };
        Integer minStrength = switch (selectedMood) {
            case "evening" -> 11;
            case "night" -> 25;
            default -> null;
        };
        Integer maxStrength = switch (selectedMood) {
            case "morning" -> 10;
            case "evening" -> 24;
            default -> null;
        };
        Sort moodSort = selectedMood.equals("night")
                ? Sort.by(Sort.Direction.DESC, "strength").and(Sort.by("name"))
                : Sort.by(Sort.Direction.ASC, "strength").and(Sort.by("name"));
        Pageable limitedPageable = PageRequest.of(pageable.getPageNumber(), size,
                pageable.getSort().isSorted() ? pageable.getSort() : moodSort);
        String email = principal instanceof OAuth2AuthenticationToken token
                ? UserUtils.getEmail(token)
                : null;
        Page<CocktailsModel> page = service.searchApprovedCatalog(cocktailName, "", minStrength,
                maxStrength, null, null, limitedPageable, email);

        model.addAttribute("page", page);
        model.addAttribute("items", page.getContent());
        model.addAttribute("pageNow", limitedPageable.getPageNumber());
        model.addAttribute("cocktailName", cocktailName);
        model.addAttribute("mood", selectedMood);
        model.addAttribute("moodLabel", switch (selectedMood) {
            case "morning" -> "Утро · лёгкие";
            case "night" -> "Ночь · крепкие";
            default -> "Вечер · средние";
        });
        model.addAttribute("topAuthorCocktails", service.getTopRatedAuthorCocktails());

        return "mainPage";

    }

}
