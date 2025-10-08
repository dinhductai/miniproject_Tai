package com.microsv.ai_service.service;

import com.microsv.ai_service.dto.response.ChatAIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChatAIService {
    List<ChatAIResponse> chat(String message, MultipartFile file, String conversationId, Long userId);

}
