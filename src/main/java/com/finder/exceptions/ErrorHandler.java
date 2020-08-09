package com.finder.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    /**
     *
     */
    private static final long serialVersionUID = 6001361189651998848L;

    @ExceptionHandler(ErrorHolder.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List genericExceptionHandler(ErrorHolder errorHolder) {
        return errorHolder.getResponse();
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List missingParameterException(Exception ex) {
        log.error("missing parameter ::: ", ex);
        ErrorHolder errorHolder = new ErrorHolder();
        errorHolder.addError("Issue with payload", ex.getLocalizedMessage());
        return errorHolder.getResponse();
    }

    @ExceptionHandler(value = {JsonParseException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List jsonParseException(Exception ex) {
        log.error("JSON PARSE EXCEPTION ::: ", ex);
        ErrorHolder errorHolder = new ErrorHolder();
        errorHolder.addError("Issue with payload", ex.getLocalizedMessage());
        return errorHolder.getResponse();
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List DBIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("DB ERROR ::: ", ex);
        ErrorHolder errorHolder = new ErrorHolder();
        String constraintName = null;
		/*if ((ex.getCause() != null) && (ex.getCause() instanceof ConstraintViolationException)) {
			constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
		}*/
        errorHolder.addError("db_error", constraintName);
        return errorHolder.getResponse();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    List methodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("METHOD NOT SUPPORTED ::: ", ex);
        ErrorHolder errorHolder = new ErrorHolder();
        errorHolder.addError("Supported Methods", ex.getSupportedHttpMethods().toString());
        return errorHolder.getResponse();
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    List methodArgTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("ARG MIS MATCH EXCEPTION ::: ", ex);
        ErrorHolder errorHolder = new ErrorHolder();
        errorHolder.addError(ex.getName(), "Data type mis-match");
        return errorHolder.getResponse();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String error(Exception ex) {
        log.error("SERVER ERROR ::: ", ex);
        return "Some error occured";
    }
}
