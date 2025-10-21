package com.microsv.task_service.repository;

import com.microsv.task_service.dto.response.DailyCompletedTasksResponse;
import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.TaskStatus;
import com.microsv.task_service.repository.query.TaskQuery;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.microsv.task_service.repository.query.TaskQuery.*;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByDeadlineBetweenAndStatus(
            OffsetDateTime startDeadline,
            OffsetDateTime endDeadline,
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

    @Query(value = TaskQuery.GET_COMPLETION_RATE_THIS_WEEK,nativeQuery = true)
    Double getCompletionRateThisWeekByUser(@Param("userId") Long userId);

    @Query(value = TaskQuery.GET_FREE_HOURS_THIS_WEEK,nativeQuery = true)
    Double getFreeHoursThisWeek(@Param("userId") Long userId);

    @Query(value = TaskQuery.GET_WEEKLY_TASK_STATUS_RATE,nativeQuery = true)
    Tuple getWeeklyTaskRates(@Param("userId") Long userId);

    @Query(value = TaskQuery.GET_WEEKLY_TASK_DISTRIBUTION, nativeQuery = true)
    List<Tuple> getWeeklyTaskDistribution(@Param("userId") Long userId);

    @Query(value = TaskQuery.GET_TASK_CREATION_TIMELINE, nativeQuery = true)
    List<Tuple> getTaskCreationTimeline(@Param("userId") Long userId);

    @Query(value = COUNT_ACTIVE_USERS_THIS_WEEK, nativeQuery = true)
    Long countActiveUsersThisWeek();

    @Query(value = COUNT_TASKS_CREATED_THIS_WEEK, nativeQuery = true)
    Long countTasksCreatedThisWeek();

    @Query(value = GET_COMPLETED_TASKS_BY_DAY_THIS_WEEK, nativeQuery = true)
    List<Tuple> getCompletedTasksByDayThisWeek();


    @Query(value = COUNT_TASKS_BY_PRIORITY, nativeQuery = true)
    List<Tuple> countTasksByPriority();
}
