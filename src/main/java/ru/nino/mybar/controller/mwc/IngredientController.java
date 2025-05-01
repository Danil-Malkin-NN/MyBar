package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.IngredientDto;
import ru.nino.mybar.service.IngredientServiceImpl;

@Controller()
@RequestMapping("page")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    @GetMapping("/ingredients")
    public String ingredients(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<IngredientDto> page = ingredientService.getPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("ingredients", page.getContent());
        return "ingredientsPage";
    }

}
