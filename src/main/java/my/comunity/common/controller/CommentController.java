package my.comunity.common.controller;

import my.comunity.common.dto.CommentResultDto;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.model.Comment;
import my.comunity.common.model.User;
import my.comunity.common.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        if(comment==null|| StringUtils.isBlank(comment.getContent())){
            return CommentResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return CommentResultDto.okOf();
    }
    @ResponseBody
    @GetMapping("/comment/{id}")
    public Comment comment2(){
        return null;
    }

}
