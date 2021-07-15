package com.diamon.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already exists")
public class UserAlreadyExistsException
	extends NoStackTraceException {
}
