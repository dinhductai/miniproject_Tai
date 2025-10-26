package com.microsv.task_service.controller;

import com.microsv.task_service.dto.request.TaskCreationRequest;
import com.microsv.task_service.dto.request.TaskUpdateRequest;
import com.microsv.task_service.dto.response.*;
import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.TaskStatus;
import com.microsv.task_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    //tạo task mới
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreationRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        TaskResponse response = taskService.createTask(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //lấy tất cả tasks của user
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskResponse> responses = taskService.getAllTasksByUser(userId);
        return ResponseEntity.ok(responses);
    }

    //  lấy task theo id
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId,
                                                @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        TaskResponse response = taskService.getTask(taskId, userId);
        return ResponseEntity.ok(response);
    }

    //lấy tasks theo status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskStatus status,
                                                               @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskResponse> responses = taskService.getTasksByStatus(userId, status);
        return ResponseEntity.ok(responses);
    }

    //lấy tasks sắp đến hạn
    @GetMapping("/upcoming")
    public ResponseEntity<List<TaskResponse>> getUpcomingTasks(
            @RequestParam(required = false, defaultValue = "24") Integer hours,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskResponse> responses = taskService.getUpcomingTasks(userId, hours);
        return ResponseEntity.ok(responses);
    }

    //lấy thống kê tasks
    @GetMapping("/statistics")
    public ResponseEntity<TaskStatisticResponse> getTaskStatistics(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        TaskStatisticResponse statistics = taskService.getTaskStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    //Cập nhật toàn bộ task
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId,
                                                   @RequestBody TaskUpdateRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        TaskResponse response = taskService.updateTask(taskId, request, userId);
        return ResponseEntity.ok(response);
    }

    // cập nhật status của task
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable Long taskId,
                                                         @RequestParam TaskStatus status,
                                                         @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        TaskResponse response = taskService.updateTaskStatus(taskId, status, userId);
        return ResponseEntity.ok(response);
    }

    //Xóa task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId,
                                           @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/today")
    public ResponseEntity<List<TaskResponse>> getAllTasksToday(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskResponse> responses = taskService.getAllTaskToday(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/today/overdue")
    public ResponseEntity<List<TaskResponse>> getOverdueTasksToday(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskResponse> responses = taskService.getOverdueTaskToday(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/today/completed")
    public ResponseEntity<List<TaskResponse>> getCompletedTasksToday(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskResponse> responses = taskService.getCompetedTaskToday(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statistics/weekly-status")
    public ResponseEntity<StatusTaskWeekResponse> getWeeklyTaskStatusRates(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        StatusTaskWeekResponse response = taskService.getWeeklyTaskRates(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics/weekly-distribution")
    public ResponseEntity<List<DailyTaskCountResponse>> getWeeklyTaskDistribution(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<DailyTaskCountResponse> responses = taskService.getWeeklyTaskDistribution(userId);
        return ResponseEntity.ok(responses);
    }

    //done
    @GetMapping("/statistics/completion-before-deadline")
    public ResponseEntity<Double> getCompletedBeforeDeadlineRate(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Double rate = taskService.getCompletionRateThisWeek(userId);
        return ResponseEntity.ok(rate);
    }

    //done
    @GetMapping("/statistics/free-hours")
    public ResponseEntity<Double> getFreeHoursThisWeek(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Double freeHours = taskService.getFreeHoursThisWeek(userId);
        return ResponseEntity.ok(freeHours);
    }


    //done
    @GetMapping("/statistics/creation-timeline")
    public ResponseEntity<List<TaskTimelineResponse>> getTaskCreationTimeline(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        List<TaskTimelineResponse> timeline = taskService.getTaskCreationTimeline(userId);
        return ResponseEntity.ok(timeline);
    }

    @GetMapping("/active-users/weekly")
    public ResponseEntity<Long> countUserRegister() {
        Long count = taskService.countActiveUsersThisWeek();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/weekly")
    public ResponseEntity<Long> countTaskWeekly() {
        Long count = taskService.countTasksCreatedThisWeek();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/statistics/weekly-task-complete")
    public ResponseEntity<List<DailyCompletedTasksResponse>> getDailyCompletedTask() {
        List<DailyCompletedTasksResponse> responses = taskService.getCompletedTasksByDayThisWeek();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statistics/weekly-task-priority")
    public ResponseEntity<List<TaskPriorityCountResponse>> getPriorityTaskCount(){
        List<TaskPriorityCountResponse> responses = taskService.countTasksByPriority();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> getTasks(@RequestParam String title) {
        return ResponseEntity.ok(taskService.findTaskByTitle(title));
    }
}