package com.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StatusNotFoundException extends GeneralException {

  public StatusNotFoundException(String statusRecebido) {
    super("Status inválido. O status '"+statusRecebido+"' não existe.", HttpStatus.NOT_FOUND);
  }

}

