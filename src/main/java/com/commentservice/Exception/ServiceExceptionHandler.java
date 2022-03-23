package com.commentservice.Exception;

import com.commentservice.Errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommentNotFoundException.class})
    ResponseEntity customerNotFoundHandler(Exception exception, ServletWebRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setCode("404");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
