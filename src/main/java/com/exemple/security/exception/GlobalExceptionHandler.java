package com.exemple.security.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
