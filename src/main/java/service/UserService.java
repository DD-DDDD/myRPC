package service;

import server.User;

public interface UserService {
    User getUserByUserId(Integer id);
}
