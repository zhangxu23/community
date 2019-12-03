package my.comunity.common.service;

import my.comunity.common.dto.PageDto;
import my.comunity.common.dto.QuestionDto;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.exception.CustomizeException;
import my.comunity.common.mapper.QuestionMapper;
import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.Question;
import my.comunity.common.model.QuestionExample;
import my.comunity.common.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    public PageDto list(Integer  page, Integer size) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(size*(page-1),size));
        for (Question question : questions) {
            User u = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(u);
            questionDtos.add(questionDto);
        }
        PageDto pageDto=new PageDto();
        pageDto.setQuestionDtos(questionDtos);
        QuestionExample example = new QuestionExample();
        Integer totalCount=(int) questionMapper.countByExample(example);
        pageDto.setPaging(totalCount,page,size);
        return pageDto;
    }

    public QuestionDto getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        User u = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(u);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(new Date().getTime());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int code=questionMapper.updateByExampleSelective(question,questionExample);
            if(code!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        synchronized (QuestionService.class) {
            Question question=questionMapper.selectByPrimaryKey(id);
            question.setViewCount(question.getViewCount()+1);
            questionMapper.updateByPrimaryKey(question);
        }
    }
}
