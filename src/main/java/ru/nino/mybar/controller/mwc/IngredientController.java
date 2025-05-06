//package ru.nino.mybar.controller.mwc;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.nino.mybar.dto.show.IngredientDto;
//import ru.nino.mybar.service.IngredientServiceImpl;
//import ru.nino.mybar.utils.UserUtils;
//
//import java.security.Principal;
//
//@Controller()
//@RequestMapping("page")
//@RequiredArgsConstructor
//public class IngredientController {
//
//    private final IngredientServiceImpl ingredientService;
//
//    @GetMapping("/ingredients")
//    public String ingredients(@PageableDefault(size = 10) Pageable pageable, Model model, Principal user) {
//        Page<IngredientDto> page;
//        if(user == null) {
//             page = ingredientService.getPage(pageable);
//        }else {
//            String email = UserUtils.getEmail((OAuth2AuthenticationToken) user);
//            page = ingredientService.getPage(pageable, email);
//        }
//        model.addAttribute("page", page);
//
//        model.addAttribute("ingredients", page.getContent());
//        return "ingredientsPage";
//    }
//
//}
