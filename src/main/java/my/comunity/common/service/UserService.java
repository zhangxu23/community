package my.comunity.common.service;

import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.User;
import my.comunity.common.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> us=userMapper.selectByExample(example);
        if(us==null&&us.size()==0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            User u=us.get(0);
            u.setGmtModified(System.currentTimeMillis());
            u.setAvatarUrl(user.getAvatarUrl());
            u.setName(user.getName());
            u.setToken(user.getToken());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(u.getId());
            userMapper.updateByExampleSelective(u,userExample);
        }
    }
}
