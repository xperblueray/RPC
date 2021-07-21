package com.xperblueray.invoker.rpc03;

import com.xperblueray.common.IUserService;
import com.xperblueray.common.User;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        IUserService userService = Stub.getStub();
        User user = userService.findUserById(123);
        System.out.println(user);
    }
}
