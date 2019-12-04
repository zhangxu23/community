package my.comunity.common.controller;

import my.comunity.common.dto.CommentResultDto;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.model.Comment;
import my.comunity.common.model.User;
import my.comunity.common.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CommentController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody Comment comment){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return CommentResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return CommentResultDto.okOf();
    }
}
