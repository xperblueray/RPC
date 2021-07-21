package com.xperblueray.invoker.rpc05;

import com.xperblueray.common.IUserService;
import com.xperblueray.common.User;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ServerSocket ss = new ServerSocket(8888);
        while(running) {
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        ObjectOutputStream oos = new ObjectOutputStream(out);

        String methodName = ois.readUTF();
        Class[] parameterType = (Class[]) ois.readObject();
        Object[] args = (Object[]) ois.readObject();

        IUserService service = new UserServiceImpl();
        Method method = service.getClass().getDeclaredMethod(methodName, parameterType);
        User user = (User) method.invoke(service, args);
        oos.writeObject(user);
        oos.flush();
    }
}