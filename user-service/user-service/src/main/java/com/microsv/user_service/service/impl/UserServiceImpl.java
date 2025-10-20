package com.microsv.user_service.service.impl;

import com.microsv.common.enumeration.ErrorCode;
import com.microsv.common.exception.BaseException;
import com.microsv.user_service.dto.request.UserCreationRequest;
import com.microsv.user_service.dto.request.UserUpdateRequest;
import com.microsv.user_service.dto.response.UserAuthResponse;
import com.microsv.user_service.dto.response.UserResponse;
import com.microsv.user_service.entity.Role;
import com.microsv.user_service.entity.User;
import com.microsv.user_service.enumeration.RoleName;
import com.microsv.user_service.mapper.UserMapper;
import com.microsv.user_service.repository.PermissionRepository;
import com.microsv.user_service.repository.RoleRepository;
import com.microsv.user_service.repository.UserRepository;
import com.microsv.user_service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new BaseException(ErrorCode.INVALID_USER_ID);
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new BaseException(ErrorCode.INVALID_INPUT);
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_IN_USE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.DATABASE_QUERY_ERROR);
        }
    }

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (request == null) {
            throw new BaseException(ErrorCode.INVALID_INPUT);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_IN_USE);
        }
        Role userRole = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new BaseException(ErrorCode.ROLE_NOT_FOUND));
        try {
            User savedUser = userRepository.save(userMapper.toCreateUser(request,userRole));
            return userMapper.toUserResponse(savedUser);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.DATABASE_QUERY_ERROR);
        }
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        try {
            return userMapper.toUserResponse(userRepository.save(userMapper.toUpdateUser(request,user)));
        } catch (Exception e) {
            throw new BaseException(ErrorCode.DATABASE_QUERY_ERROR);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.DATABASE_QUERY_ERROR);
        }
    }

    @Override
    public UserAuthResponse getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BaseException(ErrorCode.INVALID_INPUT);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        Set<String> scopes = new HashSet<>();
        user.getRoles().forEach(role -> {
            scopes.add("ROLE_" + role.getRoleName().name());
            if (role.getPermissions() != null) {
                role.getPermissions().forEach(permission -> scopes.add(permission.getPermissionName()));
            }
        });

        return userMapper.toUserAuthResponse(user,scopes);
    }

    @Override
    public long countUser() {
        return userRepository.count();
    }

    @Override
    public Long countUserRegisterThisWeek() {
        return userRepository.countUsersRegisteredThisWeek();
    }


}
