package my.comunity.common.controller;

import my.comunity.common.dto.QuestionDto;
import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.Question;
import my.comunity.common.model.User;
import my.comunity.common.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublicController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/public")
    public String pub(){
        return "public";
    }
    @GetMapping("/public/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        QuestionDto question=questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("id",question.getId());
        return "public";
    }
    @PostMapping("/public")
    public String pub(@RequestParam(value = "title",required = false) String title,
                      @RequestParam(value = "description",required = false)String description,
                      @RequestParam(value = "tag",required =false) String tag,
                      @RequestParam(value = "id",required = false) Long id, Model model){
        model.addAttribute("title",title);
        model.addAttribute("tag",tag);
        model.addAttribute("description",description);
        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "public";
        }
        if(description==null||description==""){
            model.addAttribute("error","描述不能为空");
            return "public";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "public";
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "public";
        }
        Question question=new Question();
        question.setTag(tag);
        question.setCreator((long) user.getId());
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator((long) user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

}
