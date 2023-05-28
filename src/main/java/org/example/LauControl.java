package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LauControl {
    Lau EFY = new Lau("Josephine", "Baker");
    String status = EFY.fetchLau();

    @GetMapping
    String getLau(Model model){
        model.addAttribute("template", EFY.printLau());
        return "main";
    }


}
