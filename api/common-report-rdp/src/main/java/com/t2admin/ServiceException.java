package com.t2admin;

public class ServiceException extends  RuntimeException{

    private ResultCode resultCode;
    private String message;

    public ServiceException() {
        //...
    }

    public ServiceException(String message) {
        this.message=message;
        this.resultCode=ResultCode.BUSINESS_PROCESSING_FAILED;
    }
    public ServiceException(String message,ResultCode resultCode) {
        this.message=message;
        this.resultCode=resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
