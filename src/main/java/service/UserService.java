package service;

import server.User;

public interface UserService {
    User getUserByUserId(Integer id);
    // 给这个服务器增加一个功能
    Integer insertUserId(User user);
}
