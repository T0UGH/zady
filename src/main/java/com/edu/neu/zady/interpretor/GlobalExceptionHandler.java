package com.edu.neu.zady.interpretor;

import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.DTO;
import com.edu.neu.zady.util.DTOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({BadDataException.class})
    public DTO handleBadDataException(BadDataException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "未知错误";
        }
        return DTOFactory.forbiddenDTO(msg);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({DataIntegrityViolationException.class,})
    public DTO handleDataIntegrityViolationException(DataIntegrityViolationException e){
        String msg = "插入或删除操作缺少必要字段";
        logger.error(msg, e);
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public DTO handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg = "违反唯一约束条件";
        logger.error(msg, e);
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({DuplicateKeyException.class})
    public DTO handleDuplicateKeyException(DuplicateKeyException e){
        String msg = "违反唯一条件约束";
        logger.error(msg, e);
        return DTOFactory.forbiddenDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public DTO handleNotFoundException(NotFoundException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "资源未找到";
        }
        return DTOFactory.notFoundDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoAuthException.class)
    public DTO handleNoAuthException(NoAuthException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "无权限";
        }
        return DTOFactory.unauthorizedDTO(msg);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DefaultException.class)
    public DTO handleDefaultException(DefaultException e){
        String msg = e.getMessage();
        logger.error(msg, e);
        if (msg == null || msg.equals("")) {
            msg = "其他错误";
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
