package com.ics.oauth2server.user;

import com.ics.oauth2server.validators.annotations.Password;
import com.ics.oauth2server.validators.annotations.PhoneNo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public @Data class UserRequest {

//    @NotEmpty(message = "Username must not be empty")
    public String username;
//    @Email(message = "Enter valid email id")
//    @NotEmpty(message = "Email must not be empty")
//    @NotNull(message = "Email must not be null")
    public String emailId;
    public String firstName;
    public String lastName;
//    @PhoneNo(message = "Phone no is not valid")
    @NotBlank(message = "Phone no must not be blank")
    @NotEmpty(message = "Phone no must not be empty")
    @Min(value = 10,message = "Phone No is not valid")
    public String phoneNo;
    @Password(message = "Password is not valid, must contains 1 Upper, 1 Lower characters, 1 Special character and 1 number")
    @NotBlank(message = "Password must not be blank")
    @NotEmpty(message = "Password must not be empty")
    @Length(message = "Password length must not be less than 8 and not exceed 14 character",min = 8, max = 14)
    public String password;

}
