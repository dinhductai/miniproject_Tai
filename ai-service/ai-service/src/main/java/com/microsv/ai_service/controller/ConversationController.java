package com.microsv.ai_service.controller;

import com.microsv.ai_service.dto.response.ChatAIConversationResponse;
import com.microsv.ai_service.dto.response.ChatAIResponse;
import com.microsv.ai_service.entity.ConversationMemory;
import com.microsv.ai_service.service.impl.ChatAIServiceImpl;
import com.microsv.ai_service.service.impl.ChatMemoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/ai")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConversationController {
    ChatMemoryServiceImpl chatMemoryService;
    ChatAIServiceImpl chatAIService;

    @PostMapping
    public ResponseEntity<ChatAIConversationResponse> chat(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "conversationId", required = false) String conversationId,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId  = Long.parseLong(jwt.getSubject());
        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }
        String chatAIResponses = chatAIService.chat(message, file,conversationId,userId);
        ChatAIConversationResponse response = new ChatAIConversationResponse(conversationId,chatAIResponses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id")
    public ResponseEntity<String> getConversationId(@AuthenticationPrincipal Jwt jwt){
        Long userId  = Long.parseLong(jwt.getSubject());
        String conversationId = chatMemoryService.getConversationId(userId);
        return ResponseEntity.ok(conversationId);
    }

    @GetMapping
    public ResponseEntity<Page<ConversationMemory>> getConversationMemory(@AuthenticationPrincipal Jwt jwt,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size){
        Long userId  = Long.parseLong(jwt.getSubject());
        Page<ConversationMemory> conversation = chatMemoryService.getConversationMemory(userId,page,size);
        return ResponseEntity.ok(conversation);
    }}
