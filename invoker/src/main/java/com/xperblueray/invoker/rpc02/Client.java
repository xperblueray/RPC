package com.xperblueray.invoker.rpc02;

import com.xperblueray.common.User;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        Stub stub = new Stub();
        User user = stub.findUserById(123);
        System.out.println(user);
    }
}
