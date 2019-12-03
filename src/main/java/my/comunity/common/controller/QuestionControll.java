package my.comunity.common.controller;

import my.comunity.common.dto.QuestionDto;
import my.comunity.common.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionControll {
    @Autowired
    QuestionService questionService;
    @GetMapping("question/{id}")
    private String question(@PathVariable("id") Long id, Model model){
        questionService.incView(id);
        QuestionDto questionDto=questionService.getById(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("info","ceshi");
        return "question";
    }

}
