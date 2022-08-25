package com.juannobert.modelo.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.juannobert.modelo.services.exceptions.DatabaseIntegrityException;
import com.juannobert.modelo.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		err.setStatus(status.value());
		err.setPath(request.getRequestURI());
		err.setError("Resourcer not found");
		err.setMessage(e.getMessage());
		err.setTimestamp(Instant.now());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseIntegrityException.class)
	public ResponseEntity<StandardError> databaseIntegrity(DatabaseIntegrityException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		err.setStatus(status.value());
		err.setPath(request.getRequestURI());
		err.setError("Database integrity violation");
		err.setMessage(e.getMessage());
		err.setTimestamp(Instant.now());
		return ResponseEntity.status(status).body(err);
	}
	
	

}
