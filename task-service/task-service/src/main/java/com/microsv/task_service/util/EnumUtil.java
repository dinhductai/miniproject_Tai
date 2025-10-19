package com.microsv.task_service.util;

import com.microsv.task_service.enumeration.PriorityLevel;
import com.microsv.task_service.enumeration.TaskStatus;

public class EnumUtil {


    public static TaskStatus convertStatus(Object status) {
        if (status == null) return null;
        return TaskStatus.valueOf(status.toString().toUpperCase());
    }

    public static PriorityLevel convertPriority(Object priority) {
        if (priority == null) return null;
        return PriorityLevel.valueOf(priority.toString().toUpperCase());
    }
}
