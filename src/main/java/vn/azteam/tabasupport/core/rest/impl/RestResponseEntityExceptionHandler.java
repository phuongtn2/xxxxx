package vn.azteam.tabasupport.core.rest.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Error;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.rest.impl
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see ResponseEntityExceptionHandler
 * @since 1.0.0
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private Logger logger = LogManager.getLogger(getClass());

	@ExceptionHandler(value = {ClientRegistrationException.class})
	public Object clientRegistrationExceptionHandler(ClientRegistrationException ex, WebRequest request) {
		logger.error(ex);
		request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
		return new ResponseEntity<Object>(new Error("client_error_code", ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = {ValidationException.class})
	public Object validationExceptionHandler(ValidationException ex, WebRequest request) {
		logger.error(ex);
		request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
		return new ResponseEntity<Object>(new Error(ex.getErrCode(), ex.getMessage()), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = {NullPointerException.class, org.apache.ibatis.binding.BindingException.class, NoSuchElementException.class})
	public Object NoSuchElementExceptionHandler(Exception ex, WebRequest request) {
		logger.error(ex);
		request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
		return new ResponseEntity<Object>(new Error(PropertiesParserUtil.getProperty("default.http.status.not_found.code"), PropertiesParserUtil.getProperty("default.http.status.not_found.msg")), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {MyBatisSystemException.class, org.springframework.dao.DataIntegrityViolationException.class, org.springframework.dao.DuplicateKeyException.class})
	public Object DatabaseExceptionHandler(Exception ex, WebRequest request) {
		logger.error(ex);
		request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
		return new ResponseEntity<Object>(new Error(PropertiesParserUtil.getProperty("default.exception.jdbc.code"), ex.getMessage()), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
