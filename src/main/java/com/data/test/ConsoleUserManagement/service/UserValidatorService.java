package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.WrongUserInformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidatorService {
    private static final Logger logger = LoggerFactory.getLogger(UserValidatorService.class.getName());
    private static Validator validator;

    public UserValidatorService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    public void validateUser(UserDto userDto) throws WrongUserInformationException{
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                logger.info(constraintViolation.getMessage());
            }
            throw new WrongUserInformationException("Unable to create new user, wrong user information provided");
        }
    }

    public void validateNewEmail(String email) throws WrongUserInformationException{
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new WrongUserInformationException("Wrong form of email provided, try again");
        }
    }

    public void validateNewTelephoneNumber(String telephoneNumber) throws WrongUserInformationException{
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telephoneNumber);
        if(!matcher.matches()){
            throw new WrongUserInformationException("Wrong form of telephone number provided, try again");
        }
    }
}
