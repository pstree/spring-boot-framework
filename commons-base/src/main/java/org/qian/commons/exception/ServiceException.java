package org.qian.commons.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException{

    private String code;

    private Object data;

    public ServiceException(String code,String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code,String message,Object data){
        super(message);
        this.code = code;
        this.data = data;
    }
}
