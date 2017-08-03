package vn.azteam.tabasupport.core.object.model;

import org.springframework.validation.Errors;

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
public class PushNotification extends BaseModel {

	private long id;
	private String titlePush;
	private String bodyPush;
	private String pushKey;
	private String deviceType;
	private long nurseryId;
	private long cultivationId;
	private long farmerId;
	private long registrationId;
	private long responsibilityEmployeeId;

	public long getResponsibilityEmployeeId() {
		return responsibilityEmployeeId;
	}

	public PushNotification setResponsibilityEmployeeId(long responsibilityEmployeeId) {
		this.responsibilityEmployeeId = responsibilityEmployeeId;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public PushNotification setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public PushNotification setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getId() {
		return id;
	}

	public PushNotification setId(long id) {
		this.id = id;
		return this;
	}

	public String getTitlePush() {
		return titlePush;
	}

	public PushNotification setTitlePush(String titlePush) {
		this.titlePush = titlePush;
		return this;
	}

	public String getBodyPush() {
		return bodyPush;
	}

	public PushNotification setBodyPush(String bodyPush) {
		this.bodyPush = bodyPush;
		return this;
	}

	public String getPushKey() {
		return pushKey;
	}

	public PushNotification setPushKey(String pushKey) {
		this.pushKey = pushKey;
		return this;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public PushNotification setDeviceType(String deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public long getNurseryId() {
		return nurseryId;
	}

	public PushNotification setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public PushNotification setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
