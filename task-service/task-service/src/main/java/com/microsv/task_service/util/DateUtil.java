package com.microsv.task_service.util;

import com.microsv.common.enumeration.ErrorCode;
import com.microsv.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class DateUtil {
    public static void ValidateDeadline(LocalDateTime deadline) {
        if (deadline != null && deadline.isBefore(LocalDateTime.now())) {
            throw new BaseException(ErrorCode.BAD_REQUEST);
        }
    }
}
