package com.blinker.atom.config.error;

import com.blinker.atom.config.CodeValue;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final String message;

    private final CodeValue code;

    private final Object data;

    public CustomException(String message) {
        super(message);
        this.message = message;
        if (message.equals(ErrorValue.ACCOUNT_NOT_FOUND.getMessage())) {
            this.code = CodeValue.NO_USER_FROM_TOKEN;
        }
        else this.code = CodeValue.FAILURE_MODAL;
        this.data = null;
    }

    public CustomException(String message, CodeValue code) {
        super(message);
        this.message = message;
        this.code = code;
        this.data = null;
    }

    public CustomException(String message, CodeValue code, Object data) {
        super(message);
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
