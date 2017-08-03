package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/23/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @since 1.0.0
 */
public class Error {
	@JsonProperty("error")
	private String errCode;

	@JsonProperty("error_description")
	private String message;

	public Error(String code, String defaultMessage) {
		errCode = code;
		message = defaultMessage;
	}

	public String getErrCode() {
		return errCode;
	}

	public Error setErrCode(String errCode) {
		this.errCode = errCode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Error setMessage(String message) {
		this.message = message;
		return this;
	}
}
