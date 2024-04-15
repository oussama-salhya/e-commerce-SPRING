package com.ouss.ecom.errors;

@lombok.Data
public class CustomError {
    private int statusCode;
    private String message;

    // getters and setters
}