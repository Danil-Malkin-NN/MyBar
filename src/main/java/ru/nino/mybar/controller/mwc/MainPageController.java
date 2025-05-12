package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String mainPage(@PageableDefault(size = 10) Pageable pageable, Model model,
                           @RequestParam(required = false, defaultValue = "") String cocktailName,
                           Principal principal) {
        Page<CocktailsModel> page;

        if(principal != null) {
            String email = UserUtils.getEmail((OAuth2AuthenticationToken) principal);
            page = service.getPageWithNameFilterModel(cocktailName, pageable, email);
        } else {
            page = service.getPageWithNameFilterModel(cocktailName, pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("items", page.getContent());
        model.addAttribute("pageNow", pageable.getPageNumber());

        return "mainPage";

    }

}
