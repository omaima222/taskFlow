package com.example.taskflow.dtos;

import lombok.Data;

@Data
public class StaticsDto {
    String done_tasks = "0%";
    String to_do_tasks = "0%";
    String in_progress_tasks = "0%";
    String neglected_tasks = "0%";
}
