package client;

import server.User;
import service.UserService;

public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8898);
        UserService proxy = clientProxy.getProxy(UserService.class);

        // 服务1
        User userByUserId = proxy.getUserByUserId(10);
        System.out.println("从服务端得到user为： " + userByUserId);
        // 服务2
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = proxy.insertUserId(user);

        System.out.println("向服务器端插入数据: " + integer);
    }
}
