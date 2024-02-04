package com.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends GeneralException {
  public NotFoundException(String message, HttpStatus statusCode) {
    super(message, statusCode);
  }

}

