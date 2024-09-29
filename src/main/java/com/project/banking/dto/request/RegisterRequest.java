package com.project.banking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "E-mail required")
    @Email(message = "Invalid e-mail format")
    private String email;

    @NotBlank(message = "Password required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,}$",
            message = """
                    Password must contain one number, one lowercase letter, one uppercase letter,\s
                    one special character, no space, and it must be at least 8 characters long
                   """
    )
    private String password;

    @NotBlank(message = "Name must be not blank")
    private String fullName;

    @NotBlank(message = "Gender must be not blank")
    private String gender;

    @NotBlank(message = "Birth date must be not blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid format date (format : yyyy-MM-dd)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @NotBlank(message = "Identity Type must be not blank")
    private String identityType;

    @NotBlank(message = "Identity Number required")
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
