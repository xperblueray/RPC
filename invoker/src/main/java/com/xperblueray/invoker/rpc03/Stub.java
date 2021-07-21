package com.xperblueray.invoker.rpc03;

import com.xperblueray.common.IUserService;
import com.xperblueray.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static IUserService getStub() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Socket socket = new Socket("127.0.0.1", 8888);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeInt(123);

                socket.getOutputStream().write(baos.toByteArray());
                socket.getOutputStream().flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int receiveId = dis.readInt();
                String name = dis.readUTF();
                User user = new User(receiveId, name);

                System.out.println(user);
                dos.close();
                socket.close();
                return user;
            }
        };

        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(), new Class[]{IUserService.class}, handler);
        return (IUserService) o;


    }
}
