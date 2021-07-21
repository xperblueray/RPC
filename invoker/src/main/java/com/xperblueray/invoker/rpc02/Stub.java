package com.xperblueray.invoker.rpc02;

import com.xperblueray.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Stub {
    public User findUserById(Integer id) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);

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
}
