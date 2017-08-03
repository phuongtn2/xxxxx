package vn.azteam.tabasupport.core.object.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * package vn.azteam.tabasupport.core.object.exception
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see Exception
 * @since 1.0.0
 */
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage"})
public class ValidationException extends Exception {
	@JsonProperty("error")
	private String errCode;

	@JsonProperty("error_description")
	private String message;

	public ValidationException(String code, String defaultMessage) {
		super(defaultMessage);
		errCode = code;
		message = defaultMessage;
	}

	public String getErrCode() {
		return errCode;
	}

	public ValidationException setErrCode(String errCode) {
		this.errCode = errCode;
		return this;
	}

	@Override
	public String toString() {
		return "ValidationException{" +
				"errCode='" + errCode + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
