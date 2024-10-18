package com.project.banking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileRequest {
    @NotBlank(message = "Full name must be not blank")
    private String fullName;

    @NotBlank(message = "Gender must be not blank")
    private String gender;

    @NotBlank(message = "Birth date must be not blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid format date (format : yyyy-MM-dd)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @NotBlank(message = "Identity type must be not blank")
    private String identityType;

    @NotBlank(message = "Identity number must be not blank")
    private String identityNumber;

    @NotBlank(message = "Address must be not blank")
    private String address;

    @NotBlank(message = "City must be not blank")
    private String city;

    @NotBlank(message = "Province must be not blank")
    private String province;

    @NotBlank(message = "Country must be not blank")
    private String country;

    @NotBlank(message = "Phone number must be not blank")
    private String phoneNumber;
}
