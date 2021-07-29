package service.impl;

import server.User;
import service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    public User getUserByUserId(Integer id) {
        System.out.println("客户端查询了" + id + "的用户");
        Random random = new Random();

        return User.builder().userName(UUID.randomUUID().toString()).id(id).sex(random.nextBoolean()).build();
    }
}
