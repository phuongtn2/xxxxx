package vn.azteam.tabasupport.plugin.push;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

/**
 * Package vn.azteam.tabasupport.plugin.push
 * Created 4/7/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class IOSPushNotificationService {

	ApnsService apnsService;

	public IOSPushNotificationService() {
		apnsService = APNS.newService()
				.withCert(getClass().getClassLoader().getResourceAsStream("key/ios/product_Certificates.p12"), "123456")
				.withProductionDestination().build();
	}

	public void pushIos(String token, String message) {
		String payLoad = APNS.newPayload().alertBody(message).build();
		byte[] deviceToken;
		byte[] payload;
		deviceToken = com.notnoop.apns.internal.Utilities.decodeHex(token);
		payload = com.notnoop.apns.internal.Utilities.toUTF8Bytes(payLoad);
		apnsService.push(deviceToken, payload);
	}
}
