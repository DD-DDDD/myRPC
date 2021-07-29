package client;

import server.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8898);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            objectOutputStream.writeInt(new Random().nextInt());
            objectOutputStream.flush();;

            User user = (User) objectInputStream.readObject();
            System.out.println("服务端返回的User:" + user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }

    }
}
