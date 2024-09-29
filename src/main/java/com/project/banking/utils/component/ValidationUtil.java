package com.project.banking.utils.component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
    private final Validator validator;

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> validated = validator.validate(object);
        if (!validated.isEmpty()) {
            throw new ConstraintViolationException(validated);
        }
    }
}
