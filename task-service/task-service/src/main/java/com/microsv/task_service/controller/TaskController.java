package com.microsv.task_service.controller;

import com.microsv.task_service.dto.request.TaskCreationRequest;
import com.microsv.task_service.dto.response.TaskResponse;
import com.microsv.task_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreationRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        TaskResponse response = taskService.createTask(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ... (Các endpoint GET, PUT, DELETE khác)
}