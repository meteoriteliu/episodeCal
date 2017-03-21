package com.meteoriteliu.fa.controller.error;

/**
 * Created by meteo on 2017/3/21.
 */
public class ApiErrorResponse {
    int status;
    int code;
    String message;

    public ApiErrorResponse(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
