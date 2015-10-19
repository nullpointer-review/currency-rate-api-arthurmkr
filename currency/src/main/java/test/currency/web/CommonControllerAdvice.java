package test.currency.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import test.currency.exception.ApplicationException;
import test.currency.web.dto.ErrorResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class CommonControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(CommonControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception e) {
        log.error("Unexpected Error", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleApplicationException(Exception e) {
        log.error("Application Error", e);
        return new ErrorResponse(e.getMessage());
    }
}
