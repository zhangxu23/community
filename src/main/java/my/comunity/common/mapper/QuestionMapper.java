package my.comunity.common.mapper;

import my.comunity.common.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into Question (title,description,creator,tag,gmt_create,gmt_modified) values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    public void insert(Question question);
    @Select("select * from question limit #{page},#{size}")
    public List<Question>  list(@Param("page") Integer page,@Param("size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);
    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
