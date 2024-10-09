package com.project.banking.utils.component;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ConverterUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter EXP_FORMATTER = DateTimeFormatter.ofPattern("MM-yy");

    public LocalDate convertToLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Convert failed. Invalid date format");
        }
    }

    public LocalDate convertToExpiryDate(String dateString) {
        try {
            YearMonth yearMonth = YearMonth.parse(dateString, EXP_FORMATTER);
            return yearMonth.atDay(1);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Convert failed. Invalid expiry date format");
        }
    }
}
