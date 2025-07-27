package com.cityzen.lockermanagementservice.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private Status status;
    private T data;
    private String message;
    private String api;
}
