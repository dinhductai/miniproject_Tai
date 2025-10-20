package com.microsv.user_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    String uploadPhoto(MultipartFile file,Long userId);
    String getPhoto(Long userId);
    void deletePhoto(Long userId);
}
