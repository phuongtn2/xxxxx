package vn.azteam.tabasupport.core.object.model;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/27/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class SimpleResponse {
	public static final String STATUS_COMPLETED = "request_completed";
	public static final String MESSAGE_COMPLETED = "Request completed !";

	private String code;
	private String message;
	private Object[] data;

	public SimpleResponse() {
		this.code = STATUS_COMPLETED;
		this.message = MESSAGE_COMPLETED;
		this.data = new Object[]{};
	}

	public SimpleResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public SimpleResponse(Object[] data) {
		this.code = STATUS_COMPLETED;
		this.message = MESSAGE_COMPLETED;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public SimpleResponse setCode(String code) {
		this.code = code;
		return this;
	}

	public Object[] getData() {
		return data;
	}

	public SimpleResponse setData(Object[] data) {
		this.data = data;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public SimpleResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return "SimpleResponse{" +
				"code='" + code + '\'' +
				", data='" + data + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
