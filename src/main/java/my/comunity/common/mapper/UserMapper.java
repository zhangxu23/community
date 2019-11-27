package my.comunity.common.mapper;

import my.comunity.common.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{account_id},#{name},#{token},#{gmt_create},#{gmt_modify},#{avatar_url})")
    void insert(User user);
    @Select("select * from user")
    List<User> select();
    @Select("select * from user where token=#{token}")
    User findByToken(String token);
}
