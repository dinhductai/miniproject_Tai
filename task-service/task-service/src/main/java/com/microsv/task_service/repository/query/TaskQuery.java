package com.microsv.task_service.repository.query;

public class TaskQuery {
    public static final String GET_ALL_TASK_TODAY =
            "SELECT t.task_id AS taskId, t.title AS title, t.description AS description, " +
                    " t.deadline AS deadline, t.status AS status, t.priority AS priority, " +
                    " t.created_at AS createdAt, t.completed_at AS completedAt, t.user_id AS userId " +
                    "FROM tasks t " +
                    "WHERE t.user_id = :userId AND t.deadline >= date_trunc('day', now()) " +
                    "AND t.deadline < date_trunc('day', now() + interval '1 day') " +
                    "ORDER BY t.deadline ASC ";

    public static final String GET_OVERDUE_TASK_TODAY =
            "SELECT t.task_id AS taskId, t.title AS title, t.description AS description, " +
                    "t.deadline AS deadline, t.status AS status, t.priority AS priority, " +
                    "t.created_at AS createdAt, t.completed_at AS completedAt, t.user_id AS userId " +
                    "FROM tasks t " +
                    "WHERE t.user_id = :userId " +
                    "AND t.deadline >= date_trunc('day', now()) " +
                    "AND t.deadline < date_trunc('day', now() + interval '1 day') " +
                    "AND t.deadline < now() " +
                    "AND t.status <> 'COMPLETED' " +
                    "ORDER BY t.deadline ASC";

    public static final String GET_COMPLETED_TASK_TODAY =
            "SELECT t.task_id AS taskId, t.title AS title, t.description AS description, " +
                    "t.deadline AS deadline, t.status AS status, t.priority AS priority, " +
                    "t.created_at AS createdAt, t.completed_at AS completedAt, t.user_id AS userId " +
                    "FROM tasks t " +
                    "WHERE t.user_id = :userId " +
                    "AND t.deadline >= date_trunc('day', now()) " +
                    "AND t.deadline < date_trunc('day', now() + interval '1 day') " +
                    "AND t.status = 'COMPLETED' " +
                    "ORDER BY t.deadline ASC";

}
