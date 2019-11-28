package my.comunity.common.mapper;

import my.comunity.common.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into Question (title,description,creator,tag,gmt_create,gmt_modified) values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    public void insert(Question question);
    @Select("select * from question")
    public List<Question>  list();
}
