package my.comunity.common.service;

import my.comunity.common.enums.CommentTypeEnum;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.exception.CustomizeException;
import my.comunity.common.mapper.CommentMapper;
import my.comunity.common.mapper.QuestionMapper;
import my.comunity.common.model.Comment;
import my.comunity.common.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0)
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        if(comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            Comment commentDb= commentMapper.selectByPrimaryKey(comment.getParentId());
            if(commentDb==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            Question question=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            synchronized (CommentService.class){
                Question question1=questionMapper.selectByPrimaryKey(comment.getParentId());
                question.setCommentCount(question.getCommentCount()+1);
                questionMapper.updateByPrimaryKey(question);
            }

        }
    }
}
