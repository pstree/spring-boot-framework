package org.qian.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.qian.commons.business.MsgResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalErrorController {

    private static final String SPACE = " ";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    MsgResult bindExceptionHandler(MethodArgumentNotValidException e) {
        log.info("参数校验失败:" + e.getBindingResult().getFieldError().getField());
        return new MsgResult(MsgResult.FAIL,e.getBindingResult().getFieldError().getField()+SPACE+e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public @ResponseBody
    MsgResult serviceException(ServiceException e) {
        log.info(e.getCode() + e.getMessage() + e.getData());
        return new MsgResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    MsgResult exception(Exception e) {
        log.error("未知错误",e);
        return new MsgResult(MsgResult.ERROR,"未知错误");
    }
}

