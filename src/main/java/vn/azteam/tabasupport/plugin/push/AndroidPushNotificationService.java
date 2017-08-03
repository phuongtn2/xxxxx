package vn.azteam.tabasupport.plugin.push;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.azteam.tabasupport.core.object.model.FirebaseResponse;
import vn.azteam.tabasupport.core.object.model.PushNotification;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Package vn.azteam.tabasupport.modules.notification.service
 * Created 2/21/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class AndroidPushNotificationService extends BaseServiceImpl {
	private String serverPushKey;
	private String serverAddress;

	public String getServerAddress() {
		return serverAddress;
	}

	public AndroidPushNotificationService setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
		return this;
	}

	public String getServerPushKey() {
		return serverPushKey;
	}

	public AndroidPushNotificationService setServerPushKey(String serverPushKey) {
		this.serverPushKey = serverPushKey;
		return this;
	}

	@Async
	public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		try {
			FirebaseResponse firebaseResponse = restTemplate.postForObject(serverAddress, entity, FirebaseResponse.class);
			return CompletableFuture.completedFuture(firebaseResponse);
		} catch (Exception e) {
			logger.error("error send notification android" + e);
		}
		return null;
	}

	public boolean pushNotification(PushNotification pushNotifications) {
		JSONObject body = new JSONObject();
		body.put("to", pushNotifications.getPushKey());
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("body", pushNotifications.getBodyPush());
		notification.put("title", pushNotifications.getTitlePush());

		JSONObject data = new JSONObject();
		data.put("farmerId", pushNotifications.getFarmerId());
		data.put("registrationId", pushNotifications.getRegistrationId());
		data.put("nurseryId", pushNotifications.getNurseryId());
		data.put("cultivationId", pushNotifications.getCultivationId());

		body.put("notification", notification);
		body.put("data", data);


		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("Authorization", "key=" + serverPushKey);

		HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

		CompletableFuture<FirebaseResponse> pushNotification = send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			FirebaseResponse firebaseResponse = pushNotification.get();
			if (firebaseResponse.getSuccess() == 1) {
				logger.info("push notification sent ok!");
				return true;
			} else {
				logger.error("error sending push notifications: " + firebaseResponse.toString());
				return false;
			}

		} catch (InterruptedException | ExecutionException e) {
			logger.error(e);
		}
		return false;
	}

}
