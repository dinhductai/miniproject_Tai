package com.microsv.user_service.service.impl;

import com.microsv.user_service.entity.User;
import com.microsv.user_service.enumeration.RoleName;
import com.microsv.user_service.repository.UserRepository;
import com.microsv.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail())!=null) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

//    static void addUsers(){
//        User user = User.builder()
//                .userName("Trung")
//                .password("123")
//                .email("trung@gmail.com")
//                .profile("url1")
//                .roles(RoleName.USER)
//                .build();
//    }
}
