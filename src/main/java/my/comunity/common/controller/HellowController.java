package my.comunity.common.controller;

import my.comunity.common.dto.User;
import my.comunity.common.mapper.UserMapper;
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
    @GetMapping("/hellow")
    public String hellow(@RequestParam("name")String name, Model model) {
        model.addAttribute("name",name);

        return "hellow";
    }
    @GetMapping("/")
    public String index() {

        Cookie[] cookies=request.getCookies();
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String  token=cookie.getValue();
                User user=userMapper.findByToken(token);
                if(user!=null) {
                    request.getSession().setAttribute("user", user);
                    System.out.println(user);
                }
                break;
            }
        }
        return "index";
    }
}
