package ru.sberbank.test.exception;


import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(ConversionFailedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleConversion(ConversionFailedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CounterAlreadyExists.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleCounterAlreadyExists(CounterAlreadyExists ex) {
    return new ResponseEntity<>("Counter already exists", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CounterNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleCounterNotFoundException(CounterNotFoundException ex) {
    return new ResponseEntity<>("Counter not found", HttpStatus.NOT_FOUND);
  }

}
