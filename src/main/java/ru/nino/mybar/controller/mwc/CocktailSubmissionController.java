package ru.nino.mybar.controller.mwc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.nino.mybar.dto.user.CocktailSubmissionForm;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.service.CocktailSubmissionService;
import ru.nino.mybar.service.CocktailServiceImpl;
import ru.nino.mybar.utils.UserUtils;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/page/my/cocktails")
@RequiredArgsConstructor
public class CocktailSubmissionController {
    private final CocktailSubmissionService submissionService;
    private final CocktailServiceImpl cocktailService;

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("submission", new CocktailSubmissionForm());
        return "cocktail-submission";
    }

    @PostMapping
    public String submit(@Valid @ModelAttribute("submission") CocktailSubmissionForm form,
                         BindingResult bindingResult, Principal principal, Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cocktail-submission";
        }
        String author = principal instanceof OAuth2AuthenticationToken token
                ? UserUtils.getEmail(token)
                : principal.getName();
        try {
            submissionService.submit(form, author);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("submissionError", exception.getMessage());
            return "cocktail-submission";
        }
        redirectAttributes.addFlashAttribute("message", "Рецепт отправлен администратору на проверку.");
        return "redirect:/page/my/cocktails";
    }

    @GetMapping
    public String mySubmissions(Principal principal, Model model) {
        String author = principal instanceof OAuth2AuthenticationToken token
                ? UserUtils.getEmail(token)
                : principal.getName();
        List<Cocktail> submissions = cocktailService.getSubmissions(author);
        model.addAttribute("submissions", submissions);
        return "my-cocktail-submissions";
    }
}
