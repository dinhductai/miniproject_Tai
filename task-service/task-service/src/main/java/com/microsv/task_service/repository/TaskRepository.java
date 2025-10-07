package com.microsv.task_service.repository;

import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByDeadlineBetweenAndStatus(
            LocalDateTime startDeadline,
            LocalDateTime endDeadline,
            TaskStatus status
    );}
