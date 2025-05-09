package com.cruca.task_api.payload;

import lombok.Builder;

import java.util.Date;

@Builder
public class CorrectRequest {

    private Date time;
    private Integer status;
    private String message;
    private Object data;

}
