package ru.nino.mybar.controller.mwc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.service.InstrumentServiceImpl;

import java.util.List;

@Controller
@RequestMapping("page")
@RequiredArgsConstructor
public class ToolController {

    private final InstrumentServiceImpl instrumentService;

    @GetMapping("/tools")
    public String tools(@RequestParam(required = false) Pageable pageable, Model model) {
        if(pageable == null){
            pageable = PageRequest.of(0, 20);
        }
        Page<InstrumentDto> page = instrumentService.getPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("tools", page.getContent());
        return "tools";
    }

}
