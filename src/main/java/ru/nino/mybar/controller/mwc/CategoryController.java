package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.CategoryDto;
import ru.nino.mybar.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("page/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryServece;

    @GetMapping
    public String category(Model model) {
        List<CategoryDto> topCategory = categoryServece.findTopCategory();

        model.addAttribute("topCategory", topCategory);
        return "category";
    }

    @GetMapping("{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        var categoryDto = categoryServece.
                findById(categoryId);

        model.addAttribute("category", categoryDto.getName());
        model.addAttribute("ingredients", categoryDto.getIngredients());
        return "categoryIngredients";
    }

}
