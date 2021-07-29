package server;

import service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        try {
            ServerSocket serverSocket = new ServerSocket(8898);
            System.out.println("服务端启动");
            // BIO
            while (true) {
                Socket socket = serverSocket.accept();
                // 开启一个线程
                new Thread(()->{
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                        Integer id = ois.readInt();
                        User userByUserId = userService.getUserByUserId(id);

                        oos.writeObject(userByUserId);
                        oos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("从IO中读取错误");
                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }
    }
}
