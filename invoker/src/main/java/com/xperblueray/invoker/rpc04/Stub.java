package com.xperblueray.invoker.rpc04;

import com.xperblueray.common.IUserService;
import com.xperblueray.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
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
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int receiveId = dis.readInt();
                String name = dis.readUTF();
                User user = new User(receiveId, name);

//                System.out.println(user);
                oos.close();
                socket.close();
                return user;
            }
        };

        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(), new Class[]{IUserService.class}, handler);
        return (IUserService) o;
    }
}
