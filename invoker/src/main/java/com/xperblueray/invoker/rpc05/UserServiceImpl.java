package com.xperblueray.invoker.rpc05;

import com.xperblueray.common.IUserService;
import com.xperblueray.common.User;

public class UserServiceImpl implements IUserService {
    @Override
    public User findUserById(Integer id) {
        return new User(id, "world");
    }
}
