package com.example.taskflow.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class IdsRequest {
    @NotNull
    Long user_id;
    Long to_user_id;
}
