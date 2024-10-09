package com.project.banking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCardRequest {
    @NotBlank(message = "Bank account id required")
    private String bankAccountId;

    @NotBlank(message = "Card type must be not blank")
    private String cardType;

    @NotBlank(message = "Card number must be not blank")
    @Size(min = 16, max = 16, message = "Card number must consist of 16 digits of number")
    @Pattern(regexp = "\\d{16}", message = "Card number only must contain digits")
    private String cardNumber;

    @NotBlank(message = "Principal card must be not blank")
    private String principalCard;

    @NotBlank(message = "Expiration date must be not blank")
    @Pattern(regexp = "^\\d{2}-\\d{2}$", message = "Invalid format date (format : MM-yy)")
    @JsonFormat(pattern = "MM-yy")
    private String expirationDate;

    @NotBlank(message = "CVV must be not blank")
    @Size(min = 3, max = 4, message = "CVV must be 3 or 4 digits of number")
    @Pattern(regexp = "^\\d{3,4}$", message = "CVV must only contain digits")
    private String cvv;
}
