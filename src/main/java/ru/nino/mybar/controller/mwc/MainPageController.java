//package ru.nino.mybar.controller.mwc;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.nino.mybar.dto.model.CocktailsModel;
//import ru.nino.mybar.service.CocktailServiceImpl;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("page")
//@RequiredArgsConstructor
//public class MainPageController {
//
//    private final CocktailServiceImpl service;
//
//    @GetMapping()
//    public String mainPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
//
//        Page<CocktailsModel> page = service.getPageModels(pageable);
//        List<CocktailsModel> content = page.getContent();
//
//        model.addAttribute("page", page);
//        model.addAttribute("items", content);
//        model.addAttribute("pageNow", pageable.getPageNumber());
//
//        return "mainPage";
//
//    }
//
//}
