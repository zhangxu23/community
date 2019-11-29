package my.comunity.common.mapper;

import my.comunity.common.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user")
    List<User> select();
    @Select("select * from user where token=#{token}")
    User findByToken(String token);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Long id);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(String accountId);
    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModify},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
