package my.comunity.common.service;

import my.comunity.common.dto.QuestionDto;
import my.comunity.common.mapper.QuestionMapper;
import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.Question;
import my.comunity.common.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    public List<QuestionDto> list() {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> questions = questionMapper.list();
        for (Question question : questions) {
            User u = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(u);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }

}
