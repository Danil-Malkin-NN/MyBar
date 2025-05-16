package ru.nino.mybar.controller.crud.adminpanel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nino.mybar.dto.show.InstrumentDto;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.repository.impl.InstrumentsRepositoryImpl;
import ru.nino.mybar.service.InstrumentServiceImpl;

@Controller
public class Adminpanelinstrument {
    @Autowired
    private InstrumentsRepositoryImpl instrumentDto;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("page/instrument/{id}")
    public String instrumentedit(@PathVariable Long id, Model model){
         model.addAttribute("instrument",instrumentDto.findById(id).orElse(null));
        return "editinstrument";
    }
    @PostMapping("/instrument/save")
    public String saveInstrument(Instrument instrument){
        instrumentDto.save(instrument);
        return "redirect:/page";
    }


}
