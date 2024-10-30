package com.exemple.security.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.exemple.security.playload.AppApiException;
import com.exemple.security.playload.ErrorDetails;
import com.exemple.security.playload.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// handler specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handlerResourceNoutFoundException(ResourceNotFoundException exception,WebRequest webRequest )
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails , HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AppApiException.class)
	public ResponseEntity<ErrorDetails> handlerAppApiException(ResourceNotFoundException exception,WebRequest webRequest )
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails , HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handlerGlobalException(Exception exception , WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }


//	@ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//                  Map<String, String> errors = new HashMap<>();
//                ex.getBindingResult().getAllErrors().forEach((error) -> {
//                    String fieldName = ((FieldError)error).getField();
//                    String message = error.getDefaultMessage();
//                    errors.put(fieldName , message);
//                });
//                return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//
//    }

	   @ExceptionHandler(MethodArgumentNotValidException.class)
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex, WebRequest request) {

	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String message = error.getDefaultMessage();
	            errors.put(fieldName, message);
	        });

	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
}
