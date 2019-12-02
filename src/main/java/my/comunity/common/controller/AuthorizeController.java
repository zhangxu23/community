package my.comunity.common.controller;

import my.comunity.common.dto.AccessTokenDTO;
import my.comunity.common.dto.GithubUser;
import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.User;
import my.comunity.common.provider.GithubProvider;
import my.comunity.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("callback")
    public String callback(@RequestParam("code") String code,@RequestParam("state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getGithubUser(accessToken);
        if(githubUser!=null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setAccountId(githubUser.getId().toString());
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout() {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
