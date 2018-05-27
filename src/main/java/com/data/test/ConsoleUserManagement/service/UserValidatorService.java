package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.dto.UserDto;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserValidatorService {

    private static Validator validator;

    public UserValidatorService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Boolean validateUser(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                System.out.println(constraintViolation.getMessage());
            }
            return false;
        } else {
            return true;
        }
    }
}
