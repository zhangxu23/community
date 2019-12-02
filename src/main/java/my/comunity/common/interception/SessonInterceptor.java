package my.comunity.common.interception;

import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.User;
import my.comunity.common.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessonInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length!=0)
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("token")){
                    String  token=cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> user=userMapper.selectByExample(userExample);
                    if(user!=null&&user.size()!=0) {
                        request.getSession().setAttribute("user", user.get(0));
                    }
                    break;
                }
            }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
