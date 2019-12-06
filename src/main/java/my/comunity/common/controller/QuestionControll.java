package my.comunity.common.controller;

import my.comunity.common.dto.CommentDTO;
import my.comunity.common.dto.QuestionDto;
import my.comunity.common.model.Question;
import my.comunity.common.service.CommentService;
import my.comunity.common.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionControll {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("question/{id}")
    private String question(@PathVariable("id") Long id, Model model){
        questionService.incView(id);
        QuestionDto questionDto=questionService.getById(id);
        model.addAttribute("question",questionDto);
        List<CommentDTO> commentDTOS=commentService.list(id);
        model.addAttribute("commentDtos",commentDTOS);
        List<Question> relatedQuestions = questionService.selectRelated(questionDto);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

}
