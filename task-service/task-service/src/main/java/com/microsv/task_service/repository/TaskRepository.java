package com.microsv.task_service.repository;

import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.TaskStatus;
import com.microsv.task_service.repository.query.TaskQuery;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByDeadlineBetweenAndStatus(
            LocalDateTime startDeadline,
            LocalDateTime endDeadline,
            TaskStatus status
    );
    Optional<Task>  findByTaskIdAndUserId(Long taskId, long userId);

    List<Task> findAllByUserId(Long userId);

    List<Task> findAllByUserIdAndStatus(Long userId, TaskStatus status);

    Long countByUserIdAndStatus(Long userId, TaskStatus status);

    @Query(value = TaskQuery.GET_ALL_TASK_TODAY,nativeQuery = true)
    List<Tuple> getAllTaskToday(@Param("userId") Long userId);

    @Query(value = TaskQuery.GET_OVERDUE_TASK_TODAY, nativeQuery = true)
    List<Tuple> getOverdueTasksToday(@Param("userId") Long userId);

    @Query(value = TaskQuery.GET_COMPLETED_TASK_TODAY,nativeQuery = true)
    List<Tuple> getCompletedTasksToday(@Param("userId") Long userId);

}
