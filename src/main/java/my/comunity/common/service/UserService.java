package my.comunity.common.service;

import my.comunity.common.mapper.UserMapper;
import my.comunity.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User u=userMapper.findByAccountId(user.getAccountId());
        if(u==null){
            u.setGmtCreate(System.currentTimeMillis());
            u.setGmtModify(u.getGmtCreate());
            userMapper.insert(u);
        }else {
            u.setGmtModify(System.currentTimeMillis());
            u.setAvatarUrl(user.getAvatarUrl());
            u.setName(user.getName());
            u.setToken(user.getToken());
            userMapper.update(u);
        }
    }
}
