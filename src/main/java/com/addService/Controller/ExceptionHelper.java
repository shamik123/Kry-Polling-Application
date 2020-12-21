package com.addService.Controller;

import java.net.ConnectException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.addService.Exceptions.DuplicateRequestException;



@ControllerAdvice
public class ExceptionHelper {

	@ExceptionHandler(value = { ConnectException.class })
	public ResponseEntity<Object> handleInvalidInputException(ConnectException ex) {
		
	    return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	
	    }
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT,
	reason = "Service Name already in use")
    public ResponseEntity<Object> handleDuplicateException(DuplicateRequestException ex) {
		
	    return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	
	    }
	
}
