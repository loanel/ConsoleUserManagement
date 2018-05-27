package com.data.test.ConsoleUserManagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    @NotNull(message = "Username can't be null")
    @Size(min = 3, message = "Username is too short, minimum 3 characters required")
    private final String username;


    @NotNull(message = "Password can't be null")
    @Size(min = 8, message = "Password is too short, minimum 8 characters required")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[a-z]).+", message = "No small character in password"),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "No large character in password"),
            @Pattern(regexp = "(?=.*[!.?@#$%^&+=]).+", message = "No special character in password"),
            @Pattern(regexp = "(?=.*[0-9]).+", message = "No number in password")
    })
    private final PasswordHolder password;


    @NotNull(message = "Email can't be null")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Wrong email, please input a proper email address")
    private final String email;


    @NotNull(message = "Telephone number can't be null")
    @Pattern(regexp = "[0-9]+", message = "Wrong telephone number, please only provide digits in the number")
    private final String telephoneNumber;


    public UserDto(String username, PasswordHolder password, String email, String telephoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public PasswordHolder getPassword() {
        return new PasswordHolder(password.getCharArray());
    }

    public String getEmail() {
        return email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }
}
