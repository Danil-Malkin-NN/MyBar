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
//import ru.nino.mybar.dto.show.InstrumentDto;
//import ru.nino.mybar.service.InstrumentServiceImpl;
//
//@Controller
//@RequestMapping("page")
//@RequiredArgsConstructor
//public class ToolController {
//
//    private final InstrumentServiceImpl instrumentService;
//
//    @GetMapping("/tools")
//    public String tools(@PageableDefault(size = 10)  Pageable pageable, Model model) {
//
//        Page<InstrumentDto> page = instrumentService.getPage(pageable);
//        model.addAttribute("page", page);
//        model.addAttribute("tools", page.getContent());
//        return "tools";
//    }
//
//}
