package vn.azteam.tabasupport.core.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.core.object.model.PushNotification;
import vn.azteam.tabasupport.core.object.model.RegistrationPush;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.core.object.dao
 * Created 2/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface PushNotificationDao {

	List<PushNotification> findAll();

	long insertPushNotification(@Param("pushNotification") PushNotification pushNotification);

	void deletePushNotification(@Param("pushNotification") PushNotification pushNotification);

	List<RegistrationPush> findAllRegistrationPush();
}
