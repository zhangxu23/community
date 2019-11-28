package my.comunity.common.controller;

import my.comunity.common.mapper.QuestionMapper;
import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.Question;
import my.comunity.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class PublicController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/public")
    public String pub(){
        return "public";
    }
    @PostMapping("/public")
    public String pub(@RequestParam(value = "title",required = false) String title,
                      @RequestParam(value = "description",required = false)String description,
                      @RequestParam(value = "tag",required =false) String tag, Model model){
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
        User user=null;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String  token=cookie.getValue();
                user=userMapper.findByToken(token);
                if(user!=null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
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
        question.setGmtCreate(new Date().getTime());
        question.setGmtModified(System.currentTimeMillis());
        questionMapper.insert(question);
        return "redirect:/";
    }

}
