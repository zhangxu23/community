package my.comunity.common.advice;

import com.alibaba.fastjson.JSON;
import my.comunity.common.dto.CommentResultDto;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model){
        HttpStatus status=getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            CommentResultDto resultDTO;
            // 返回 JSON
            if (e instanceof CustomizeException) {
                resultDTO = CommentResultDto. errorOf((CustomizeException) e);
            } else {
                resultDTO = CommentResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else
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
