package com.dailycodebuffer.OrderService.Exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{
    private String errorCode;
    private int status;

    public CustomException(String errorCode, int status, String message){
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
