package org.qian.commons.business;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MsgResult implements Serializable{

    public static final String FAIL = "000001";

    public static final String ERROR = "999999";

    private String code;

    private String message;

    private Object data;

    public MsgResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public MsgResult(String code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static MsgResult msg(String code, String message){
        return new MsgResult(code,message);
    }
    public static MsgResult msg(String code, String message, Object data){
        return new MsgResult(code,message,data);
    }
}
