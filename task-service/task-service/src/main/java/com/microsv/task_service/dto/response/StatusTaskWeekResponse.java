package com.microsv.task_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusTaskWeekResponse {
    private Double completedRate;
    private Double inProgressRate;
    private Double todoRate;

}
