package ru.nino.mybar.controller.mwc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page/shopping-list")
public class ShoppingListController {
    @GetMapping
    public String shoppingList() {
        return "shopping-list";
    }
}
