package com.edu.neu.zady.interpretor;

import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.DTO;
import com.edu.neu.zady.util.DTOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadDataException.class)
    public DTO handleBadDataException(BadDataException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "未知错误";
        }
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BadDataException.class)
    public DTO handleNotFoundException(NotFoundException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "未知错误";
        }
        return DTOFactory.notFoundDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadDataException.class)
    public DTO handleNotFoundException(NoAuthException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "未知错误";
        }
        return DTOFactory.unauthorizedDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadDataException.class)
    public DTO handleNotFoundException(DefaultException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "未知错误";
        }
        return DTOFactory.unKnownErrorDTO(msg);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public DTO handleException(Exception e) {
        String msg = e.getMessage();
        logger.error(msg, e);
        msg = "服务器未知错误";
        return DTOFactory.unKnownErrorDTO(msg);
    }
}
