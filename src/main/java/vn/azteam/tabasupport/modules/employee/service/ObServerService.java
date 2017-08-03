package vn.azteam.tabasupport.modules.employee.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.ObServer;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.service
 * created 1/25/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface ObServerService {
	List<ObServer> getAllObServer() throws NullPointerException, BindingException;

	ObServer getObServerByCompanyIdAndCultivationId(long companyId, long cultivationId) throws NullPointerException, BindingException, NoSuchElementException;

	long createObserver(ObServer obServer, boolean withoutValidated) throws ValidationException;

	long createObserver(ObServer obServer) throws ValidationException;

	long updateObserver(ObServer obServer, boolean withoutValidated) throws ValidationException;

	long updateObserver(ObServer obServer) throws ValidationException;
}
