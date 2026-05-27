package com.macuniv.student_api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T>
{
    private final boolean success;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(T data,String message)
    {
        return new ApiResponse<>(true,message,data);
    }

    public static <T> ApiResponse<T> error(List<String> errors)
    {
       return new ApiResponse<>(false,String.join(",",errors),null);
    }
}
