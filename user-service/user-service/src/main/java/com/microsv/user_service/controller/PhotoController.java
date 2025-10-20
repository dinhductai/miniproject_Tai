package com.microsv.user_service.controller;

import com.microsv.user_service.dto.request.UserCreationRequest;
import com.microsv.user_service.dto.response.UserResponse;
import com.microsv.user_service.service.PhotoService;
import com.microsv.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhotoController {
    UserService userService;
    PhotoService photoService;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upLoadImage(@RequestParam("file") MultipartFile file,
                                                           @AuthenticationPrincipal Jwt jwt) throws IOException {
        Long userId = Long.parseLong(jwt.getSubject());
        String urlImage = photoService.uploadPhoto(file,userId);
        return ResponseEntity.ok(urlImage);
    }

    @GetMapping(value = "/photo")
    public ResponseEntity<String> getPhoto(@AuthenticationPrincipal Jwt jwt){
        Long userId = Long.parseLong(jwt.getSubject());
        String urlImage = photoService.getPhoto(userId);
        return ResponseEntity.ok(urlImage);
    }

    @DeleteMapping(value = "/del")
    public ResponseEntity<?> delPhoto(@AuthenticationPrincipal Jwt jwt){
        Long userId = Long.parseLong(jwt.getSubject());
        photoService.deletePhoto(userId);
        return ResponseEntity.ok().build();
    }
}
