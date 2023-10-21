package ru.nino.mybar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.dto.show.CocktailDto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("search")
public class MagicSearch {


    @GetMapping("")
    public List<CocktailDto> getCocktailWith(Principal principal) {
        return new ArrayList<>();
    }


}
