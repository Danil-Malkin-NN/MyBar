package ru.nino.mybar.controller.crud.adminpanel;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.repository.impl.InstrumentsRepositoryImpl;

@Controller
@RequestMapping("/page/AdminPanel")
@AllArgsConstructor
public class AdminPanelTool {

    @Autowired
    private InstrumentsRepositoryImpl instrumentDto;


    @GetMapping("/instrument/{id}")
    public String ToolEdit(@PathVariable Long id, Model model){
         model.addAttribute("instrument",instrumentDto.findById(id).orElse(null));

        return "editinstrument";
    }

    @PostMapping("/instrument/save")
    public String SaveTool(Instrument instrument){
        instrumentDto.save(instrument);

        return "redirect:/page";
    }


}
