package by.klubnikov.internetbanking.advice;

import by.klubnikov.internetbanking.error.BankingError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BankingError> handlerException(Exception e) {
        log.error(e.getMessage(), e);
       return new ResponseEntity<>(new BankingError(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

}
