package com.example.social_web.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDTO {
@NotBlank
    String name;
    @Email
    @NotBlank
    String username;
    @NotBlank
    @Size(min = 2, max = 16)
    String password;
    @Pattern(regexp = "\\d{10,11}", message = "Phone number must be 10 or 11 digits")
    @NotBlank
    String phone;

}
