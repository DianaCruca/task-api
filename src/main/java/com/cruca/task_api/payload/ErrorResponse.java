package com.cruca.task_api.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {

    private static final long serialVersionUID = 1L;
    private Date time =  new Date();
    private Integer status;
    private String message;
    private String url;

    public ErrorResponse(Integer status, String message, String url) {
        super();
        this.status = status;
        this.message = message;
        this.url = url.replace("uri=", "");
    }

}
