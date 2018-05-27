package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserValidatorService {
    private static final Logger logger = LoggerFactory.getLogger(UserValidatorService.class.getName());
    private static Validator validator;

    public UserValidatorService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Boolean validateUser(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                logger.info(constraintViolation.getMessage());
            }
            return false;
        } else {
            return true;
        }
    }
}
