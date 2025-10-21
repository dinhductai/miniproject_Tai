package com.microsv.task_service.util;

import com.microsv.common.enumeration.ErrorCode;
import com.microsv.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.chrono.ChronoLocalDate;

public class DateUtil {
    public static void ValidateDeadline(OffsetDateTime deadline) {
        if (deadline != null && deadline.isBefore(OffsetDateTime.now())) {
            throw new BaseException(ErrorCode.BAD_REQUEST);
        }
    }
}
