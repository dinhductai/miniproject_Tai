package com.microsv.user_service.service;



import com.microsv.user_service.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
}
