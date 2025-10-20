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

    public static final String GET_COMPLETION_RATE_THIS_WEEK =
            "SELECT \n" +
                    "   COALESCE((COUNT(CASE WHEN t.status = 'COMPLETED' AND t.completed_at IS NOT NULL AND t.completed_at <= t.deadline THEN 1 END) * 100.0) / \n" +
                    "   NULLIF(COUNT(CASE WHEN t.status = 'COMPLETED' AND t.completed_at IS NOT NULL THEN 1 END), 0), 0) AS completion_rate\n" +
                    "FROM tasks t\n" +
                    "WHERE \n" +
                    "   t.completed_at IS NOT NULL\n" +
                    "   AND DATE_TRUNC('week', t.completed_at) = DATE_TRUNC('week', CURRENT_DATE)\n" +
                    "   AND t.status = 'COMPLETED'";

    public static final String GET_FREE_HOURS_THIS_WEEK =
            "WITH bounds AS (\n" +
                    "    SELECT \n" +
                    "        DATE_TRUNC('week', CURRENT_DATE) AS week_start,\n" +
                    "        DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '7 days' AS week_end\n" +
                    "), \n" +
                    "busy_seconds AS (\n" +
                    "    SELECT COALESCE(SUM(\n" +
                    "        EXTRACT(EPOCH FROM (\n" +
                    "            LEAST(COALESCE(t.completed_at, CURRENT_TIMESTAMP), (SELECT week_end FROM bounds)) - \n" +
                    "            GREATEST(t.created_at, (SELECT week_start FROM bounds))\n" +
                    "        ))\n" +
                    "    ), 0) AS total_busy\n" +
                    "    FROM tasks t\n" +
                    "    WHERE t.user_id = :userId\n" +
                    "        AND t.created_at < (SELECT week_end FROM bounds)\n" +
                    "        AND COALESCE(t.completed_at, CURRENT_TIMESTAMP) > (SELECT week_start FROM bounds)\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    (7 * 24 * 3600 - total_busy) / 3600.0 AS free_hours\n" +
                    "FROM busy_seconds;";

    public static final String GET_WEEKLY_TASK_STATUS_RATE =
            "WITH week_bounds AS (\n" +
                    "        SELECT \n" +
                    "            DATE_TRUNC('week', CURRENT_DATE) AS week_start,\n" +
                    "            DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '7 days' AS week_end\n" +
                    "    ),\n" +
                    "    week_tasks AS (\n" +
                    "        SELECT *\n" +
                    "        FROM tasks t\n" +
                    "        CROSS JOIN week_bounds w\n" +
                    "        WHERE \n" +
                    "            -- lấy task có thời gian thuộc tuần hiện tại\n" +
                    "            (t.created_at, COALESCE(t.completed_at, t.deadline)) OVERLAPS (w.week_start, w.week_end)\n" +
                    "            AND t.user_id = :userId\n" +
                    "    )\n" +
                    "    SELECT \n" +
                    "        ROUND(100.0 * COUNT(CASE WHEN t.status = 'DONE' THEN 1 END) / NULLIF(COUNT(*), 0), 2) AS completedRate,\n" +
                    "        ROUND(100.0 * COUNT(CASE WHEN t.status = 'IN_PROGRESS' THEN 1 END) / NULLIF(COUNT(*), 0), 2) AS inProgressRate,\n" +
                    "        ROUND(100.0 * COUNT(CASE WHEN t.status = 'TODO' AND t.deadline < NOW() THEN 1 END) / NULLIF(COUNT(*), 0), 2) AS todoRate\n" +
                    "    FROM week_tasks t;";

    public static final String GET_WEEKLY_TASK_DISTRIBUTION =
            "WITH current_week AS (\n" +
                    "        SELECT \n" +
                    "            DATE_TRUNC('week', CURRENT_DATE) AS week_start,\n" +
                    "            DATE_TRUNC('week', CURRENT_DATE) + INTERVAL '7 days' AS week_end\n" +
                    "    ),\n" +
                    "    days AS (\n" +
                    "        SELECT \n" +
                    "            generate_series(\n" +
                    "                (SELECT week_start FROM current_week),\n" +
                    "                (SELECT week_end FROM current_week) - INTERVAL '1 day',\n" +
                    "                '1 day'\n" +
                    "            )::date AS day_date\n" +
                    "    )\n" +
                    "    SELECT \n" +
                    "        TO_CHAR(d.day_date, 'Day') AS day_name,\n" +
                    "        COUNT(t.task_id) AS task_count\n" +
                    "    FROM days d\n" +
                    "    LEFT JOIN tasks t \n" +
                    "        ON t.user_id = :userId\n" +
                    "        AND t.created_at::date = d.day_date\n" +
                    "    GROUP BY d.day_date\n" +
                    "    ORDER BY d.day_date;";

    public static final String GET_TASK_CREATION_TIMELINE =
            "WITH week_series AS (\n" +
                    "    SELECT \n" +
                    "        generate_series(\n" +
                    "            DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '5 weeks',\n" +
                    "            DATE_TRUNC('week', CURRENT_DATE),\n" +
                    "            '1 week'::interval\n" +
                    "        ) AS week_start\n" +
                    "), \n" +
                    "task_weeks AS (\n" +
                    "    SELECT \n" +
                    "        DATE_TRUNC('week', created_at) AS week_start,\n" +
                    "        COUNT(*) AS task_count\n" +
                    "    FROM tasks\n" +
                    "    WHERE user_id = :userId\n" +
                    "    GROUP BY DATE_TRUNC('week', created_at)\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    TO_CHAR(ws.week_start, 'YYYY-\"W\"IW') AS week_label,\n" +
                    "    COALESCE(tw.task_count, 0) AS task_count\n" +
                    "FROM week_series ws\n" +
                    "LEFT JOIN task_weeks tw ON ws.week_start = tw.week_start\n" +
                    "ORDER BY ws.week_start;";
}
