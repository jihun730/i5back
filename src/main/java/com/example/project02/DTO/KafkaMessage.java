package com.example.project02.DTO;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class KafkaMessage {
    private String Server;
    private String Status;
    private Command Command;

    @Data
    @Builder
    public static class Command {
        private String Target;
        private String Status;
    }
}