package com.softserve.hotels.handlers;

/**
 * @author smakhov
 */
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({ NoHandlerFoundException.class, NoSuchRequestHandlingMethodException.class,
            NullPointerException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error404(Exception e, Model model) {
        return handleStatus(e, model, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ BindException.class, HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
            TypeMismatchException.class, })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error400(Exception e, Model model) {
        return handleStatus(e, model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class,
            MissingPathVariableException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error500(Exception e, Model model) {
        return handleStatus(e, model, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String error403(Exception e, Model model) {
        return handleStatus(e, model, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String unexpectedError(Exception e, Model model) {
        return handleStatus(e, model, HttpStatus.I_AM_A_TEAPOT);
    }

    public String handleStatus(Exception e, Model model, HttpStatus errorCode) {
        model.addAttribute("exception", e);
        model.addAttribute("errorCode", errorCode);
        return "errorPage";
    }
}
