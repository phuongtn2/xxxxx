package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Package vn.azteam.tabasupport.modules.notification.object
 * Created 2/21/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirebaseResponse {

	private long multicast_id;
	private Integer success;
	private Integer failure;
	private Integer canonical_ids;
	private Object results;

	public long getMulticast_id() {
		return multicast_id;
	}

	public FirebaseResponse setMulticast_id(long multicast_id) {
		this.multicast_id = multicast_id;
		return this;
	}

	public Integer getSuccess() {
		return success;
	}

	public FirebaseResponse setSuccess(Integer success) {
		this.success = success;
		return this;
	}

	public Integer getFailure() {
		return failure;
	}

	public FirebaseResponse setFailure(Integer failure) {
		this.failure = failure;
		return this;
	}

	public Integer getCanonical_ids() {
		return canonical_ids;
	}

	public FirebaseResponse setCanonical_ids(Integer canonical_ids) {
		this.canonical_ids = canonical_ids;
		return this;
	}

	public Object getResults() {
		return results;
	}

	public FirebaseResponse setResults(Object results) {
		this.results = results;
		return this;
	}

	@Override
	public String toString() {
		return "FirebaseResponse{" +
				"multicast_id=" + multicast_id +
				", success=" + success +
				", failure=" + failure +
				", canonical_ids=" + canonical_ids +
				", results=" + results +
				'}';
	}
}
