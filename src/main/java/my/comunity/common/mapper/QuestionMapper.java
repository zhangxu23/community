package my.comunity.common.mapper;

import my.comunity.common.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into Question (title,description,creator,tag) values (#{title},#{description},#{creator},#{tag})")
    public void insert(Question question);
}
