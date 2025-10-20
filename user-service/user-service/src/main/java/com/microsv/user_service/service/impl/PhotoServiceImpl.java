package com.microsv.user_service.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.microsv.user_service.entity.User;
import com.microsv.user_service.repository.UserRepository;
import com.microsv.user_service.service.PhotoService;
import com.microsv.user_service.service.UserService;
import com.microsv.user_service.util.CloundinaryUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PhotoServiceImpl implements PhotoService {
    UserService userService;
    UserRepository userRepository;
    Cloudinary cloudinary;

    @Override
    public String uploadPhoto(MultipartFile file, Long userId) {
        Map uploadResult ;
        try {
           uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String photoUrl = (String) uploadResult.get("url");
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            user.setProfile(photoUrl);
            userRepository.save(user);
        }
        return photoUrl;
    }

    @Override
    public String getPhoto(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user.getProfile();
    }

    @Override
    public void deletePhoto(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        String publicId = CloundinaryUtil.extractPublicId(user.getProfile());
        try {
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if(deleteResult.get("result").equals("ok")){
                //cloud trả về ok thì xóa ở db
                user.setProfile(null);
                userRepository.save(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
