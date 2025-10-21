package com.microsv.ai_service.service;

import com.microsv.ai_service.entity.ConversationMemory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConversationMemoryService {
    String getConversationId(Long userId);
    Page<ConversationMemory> getConversationMemory(Long userId,int page,int size);
}
