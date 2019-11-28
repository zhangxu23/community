package my.comunity.common.controller;

import my.comunity.common.dto.PageDto;
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
    public String hellow(@RequestParam(value = "name")String name, Model model) {
        model.addAttribute("name",name);
        return "hellow";
    }
    @GetMapping("/")
    public String index(Model model,@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "size",defaultValue = "5") Integer size) {

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
        PageDto pageDtos=questionService.list(page,size);
        model.addAttribute("pageDtos",pageDtos);
        return "index";
    }
}
