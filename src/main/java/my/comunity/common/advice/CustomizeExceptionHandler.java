package my.comunity.common.advice;

import my.comunity.common.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){
        HttpStatus status=getStatus(request);
        if(e instanceof CustomizeException)
            model.addAttribute("message",e.getMessage());
        else
            model.addAttribute("message","哎呀 服务器冒烟了");
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer stateCode =(Integer) request.getAttribute("javax.servlet.error.status_code");
        if (stateCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(stateCode);
    }
}