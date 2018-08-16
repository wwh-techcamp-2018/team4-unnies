package com.baemin.nanumchan.security;

import com.baemin.nanumchan.exception.UnAuthenticationException;
import com.baemin.nanumchan.utils.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestResponse<?> emptyResultData(EntityNotFoundException exception) {
        RestResponse.ErrorResponseBuilder errorResponseBuilder = RestResponse.error();
        errorResponseBuilder.appendError("Entity", exception.getMessage());
        return errorResponseBuilder.build();
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public RestResponse<?> unAuthentication(UnAuthenticationException exception) {
        RestResponse.ErrorResponseBuilder errorResponseBuilder = RestResponse.error();
        errorResponseBuilder.appendError(exception.getField(), exception.getMessage());
        return errorResponseBuilder.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public RestResponse<?> methodArgumentValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        RestResponse.ErrorResponseBuilder errorResponseBuilder = RestResponse.error();
        for (ObjectError objectError : errors) {
            log.info("object error : {}", objectError);
            FieldError fieldError = (FieldError) objectError;
            errorResponseBuilder.appendError(fieldError.getField(), getErrorMessage(fieldError));
        }
        return errorResponseBuilder.build();
    }

    private String getErrorMessage(FieldError fieldError) {
        Optional<String> code = getFirstCode(fieldError);
        if (!code.isPresent()) {
            return null;
        }

        String errorMessage = messageSourceAccessor.getMessage(code.get(), fieldError.getArguments(), fieldError.getDefaultMessage());
        log.info("error message: {}", errorMessage);
        return errorMessage;
    }

    private Optional<String> getFirstCode(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        if (codes == null || codes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(codes[0]);
    }

}