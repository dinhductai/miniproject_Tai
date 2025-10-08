package com.microsv.ai_service.service.impl;

import com.microsv.ai_service.dto.response.ChatAIResponse;
import com.microsv.ai_service.service.ChatAIService;
import com.microsv.ai_service.util.PromptUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.MimeTypeUtils;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatAIServiceImpl implements ChatAIService {
    ChatClient chatClient;

    public ChatAIServiceImpl(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultSystem(PromptUtil.SYSTEM_PROMPT)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Override
    public List<ChatAIResponse> chat(String message, MultipartFile file, String conversationId, Long userId) {
        return chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,conversationId))
                .user(promptUserSpec -> {
                    if(message!=null && !message.isEmpty()) {
                        promptUserSpec.text(message);
                    }
                })
                .call()
                .entity(new ParameterizedTypeReference<List<ChatAIResponse>>() {
                });
    }
}
