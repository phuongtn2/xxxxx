package vn.azteam.tabasupport.core.object.model;

/**
 * Package vn.azteam.tabasupport.core.object.model
 * Created 2/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class RegistrationPush {
	private long registrationId;
	private String pushKey;
	private String deviceType;
	private String farmerName;
	private long employeeId;
	private long farmerId;

	public long getRegistrationId() {
		return registrationId;
	}

	public RegistrationPush setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public String getPushKey() {
		return pushKey;
	}

	public RegistrationPush setPushKey(String pushKey) {
		this.pushKey = pushKey;
		return this;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public RegistrationPush setDeviceType(String deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public RegistrationPush setFarmerName(String farmerName) {
		this.farmerName = farmerName;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public RegistrationPush setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public RegistrationPush setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}
}
