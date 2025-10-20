package com.microsv.user_service.repository.query;

public class UserQuery {
    public static final String COUNT_ALL_NEW_USERS_THIS_WEEK =
            "SELECT COUNT(*) FROM users " +
                    "WHERE DATE_TRUNC('week', created_at) = DATE_TRUNC('week', CURRENT_DATE)";
}
