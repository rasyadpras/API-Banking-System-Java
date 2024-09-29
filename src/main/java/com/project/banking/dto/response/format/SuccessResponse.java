package com.project.banking.dto.response.format;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}
