package com.tys.controller;

import com.tys.exception.NoSessionException;
import com.tys.util.vo.ReturnMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-4-26 9:29
 */
@RestControllerAdvice
public class WebExceptionHandle {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoSessionException.class)
    public ReturnMessage noSessionFindException(NoSessionException e) {
        return new ReturnMessage(e.getMessage());
    }

}
