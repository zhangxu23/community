package my.comunity.common.controller;

import my.comunity.common.dto.QuestionDto;
import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.User;
import my.comunity.common.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HellowController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/hellow")
    public String hellow(@RequestParam("name")String name, Model model) {
        model.addAttribute("name",name);

        return "hellow";
    }
    @GetMapping("/")
    public String index(Model model) {

        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String  token=cookie.getValue();
                User user=userMapper.findByToken(token);
                if(user!=null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        List<QuestionDto> questionDtos=questionService.list();
        model.addAttribute("questionDtos",questionDtos);
        return "index";
    }
}
