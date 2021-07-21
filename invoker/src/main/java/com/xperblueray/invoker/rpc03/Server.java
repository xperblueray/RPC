package com.xperblueray.invoker.rpc03;

import com.xperblueray.common.IUserService;
import com.xperblueray.common.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        while(running) {
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws IOException {
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        DataInputStream dis = new DataInputStream(in);
        DataOutputStream dos = new DataOutputStream(out);

        int i = dis.readInt();
        IUserService service = new UserServiceImpl();
        User user = service.findUserById(i);
        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}