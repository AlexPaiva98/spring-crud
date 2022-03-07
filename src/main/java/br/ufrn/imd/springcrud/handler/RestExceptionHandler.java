package br.ufrn.imd.springcrud.handler;

import br.ufrn.imd.springcrud.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.hibernate.JDBCException;

import javax.validation.constraints.NotNull;

import br.ufrn.imd.springcrud.util.MessageException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException exception, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "JSON formation issue"));
    }

    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<Object> handlerGenericException(GenericException genericException) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MessageException.getMessage(genericException));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handlerEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, MessageException.getMessage(entityNotFoundException));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handlerBusinessException(BusinessException businessException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, MessageException.getMessage(businessException));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handlerValidationException(ValidationException validationException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, MessageException.getMessage(validationException));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(JDBCException.class)
    protected ResponseEntity<Object> handlerConstraintViolationException(JDBCException jdbcException) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MessageException.getMessage(jdbcException));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<Object> handlerClientException(ClientException clientException) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MessageException.getMessage(clientException));
        return buildResponseEntity(apiError);
    }
}
