package com.example.ticket_car.Dto.baseResponseDto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto<T> {

    private int status;
    private String message;
    private T data;
    private long timestamp;

    public BaseResponseDto(int status, String message, T data, long timestamp){
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;

    }

    // ✅ Success response (có data)
    public static <T> BaseResponseDto<T> success(int status, String message, T data) {
        return BaseResponseDto.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }


    // ❌ Error response
    public static <T> BaseResponseDto<T> error(int status, String message) {
        return BaseResponseDto.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .timestamp(System.currentTimeMillis())
                .build();
    }

}
