package com.gridasovka.aggregator.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Content Item Not Found")
public class ContentItemNotFoundException extends RuntimeException {
}
