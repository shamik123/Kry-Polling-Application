package com.addService.Controller;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

import com.addService.Exceptions.CustomErrorMessage;
import com.addService.Exceptions.DuplicateRequestException;



@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = { ConnectException.class })
	public ResponseEntity<Object> handleInvalidInputException(ConnectException ex) {
		
	    return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	
	    }
	
	//@ResponseBody
	//@ResponseStatus(value = HttpStatus.CONFLICT,
	//reason = "Service Name already in use")
	@ExceptionHandler(value = {DuplicateRequestException.class})
    public ResponseEntity<Object> handleDuplicateException(DuplicateRequestException ex) {
		
	    return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
	
	    }
	
	//@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
	//@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleMethodArgumentNotValid(
	  MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
	      List<String> errorList = ex
	                .getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(fieldError -> fieldError.getDefaultMessage())
	                .collect(Collectors.toList());
	        CustomErrorMessage errorDetails = new CustomErrorMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
	        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
	    }
	}

