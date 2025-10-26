package com.microsv.user_service.mapper;

import com.microsv.user_service.dto.request.UserCreationRequest;
import com.microsv.user_service.dto.request.UserUpdateRequest;
import com.microsv.user_service.dto.response.UserAuthResponse;
import com.microsv.user_service.dto.response.UserResponse;
import com.microsv.user_service.entity.Role;
import com.microsv.user_service.entity.User;
import jakarta.persistence.Tuple;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private PasswordEncoder passwordEncoder;

    public User toCreateUser(UserCreationRequest request, Role role) {
        return User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .profile(request.getProfile())
                .roles(Set.of(role))
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .profile(user.getProfile())
                .roles(user.getRoles().stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.toSet()))
                .build();
    }

    public User toUpdateUser(UserUpdateRequest request,User user) {
        return user.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .profile(request.getProfile())
                .build();
    }

    public UserAuthResponse toUserAuthResponse(User user,Set<String> scope) {
        return UserAuthResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .profile(user.getProfile())
                .password(user.getPassword())
                .roles(scope)
                .build();
    }


    public User tupleToUser(Tuple tuple){
        return User.builder()
                .userId(tuple.get("userId", Long.class))
                .userName(tuple.get("userName", String.class))
                .password(tuple.get("password",String.class))
                .email(tuple.get("email", String.class))
                .profile(tuple.get("profile", String.class))
                .createdAt(tuple.get("createdAt", LocalDateTime.class))
                .build();
    }
}
