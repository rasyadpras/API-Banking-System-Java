package com.project.banking.dto.response.format;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorExceptionResponse {
    private int statusCode;
    private String message;
    private String error;
}
