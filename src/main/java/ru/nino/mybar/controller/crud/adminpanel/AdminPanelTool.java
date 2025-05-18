package ru.nino.mybar.controller.crud.adminpanel;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.nino.mybar.entity.Instrument;
import ru.nino.mybar.repository.impl.InstrumentsRepositoryImpl;

@Controller
@RequestMapping("/page/AdminPanel")
@AllArgsConstructor
public class AdminPanelTool {

    @Autowired
    private InstrumentsRepositoryImpl instrumentsRepository;


    @GetMapping("/instrument/{id}")
    public String toolEdit(@PathVariable Long id, Model model){
       Instrument instrument = instrumentsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Коктель не найден"));
        model.addAttribute("instrument", instrument);
        return "edit-instrument";
    }

    @PostMapping("/instrument/save")
    public String saveTool(Instrument instrument){
        instrumentsRepository.save(instrument);

        return "redirect:/page";
    }
    
}
