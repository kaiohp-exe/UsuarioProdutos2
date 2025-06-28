package com.caio.PedidoProduto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedUserNameException extends RuntimeException {
    public DuplicatedUserNameException(String msg){
        super(msg);
    }
}
