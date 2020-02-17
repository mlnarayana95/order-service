package com.treez.simpleorderservice.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	 
	@ExceptionHandler(NullPointerException.class)
	 public ResponseEntity<ErrorItem> handle(NullPointerException e) {
	  
		   ErrorItem error = new ErrorItem();
	        error.setMessage(e.getMessage());

	        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
    public static class ErrorItem {

        @JsonInclude(JsonInclude.Include.NON_NULL) private String code;

        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}


