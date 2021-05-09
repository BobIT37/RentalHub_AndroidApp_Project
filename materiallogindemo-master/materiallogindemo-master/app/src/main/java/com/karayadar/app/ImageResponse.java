package com.karayadar.app;

public class ImageResponse {

    String error ;
    String message;
    public ImageResponse(){}

    public ImageResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
