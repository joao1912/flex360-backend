package com.flex360.api_flex360.exceptions;

public class ErroAoSalvarException extends RuntimeException {
    
    public ErroAoSalvarException(String mensagemException) {
        super(mensagemException);
    }

    public ErroAoSalvarException(String mensagemException, Throwable e) {
        super(mensagemException, e);
    }
}