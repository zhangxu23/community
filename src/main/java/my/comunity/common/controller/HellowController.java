package my.comunity.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HellowController {
    @GetMapping("/hellow")
    public String hellow(@RequestParam("name")String name, Model model) {
        model.addAttribute("name",name);
        return "hellow";
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
