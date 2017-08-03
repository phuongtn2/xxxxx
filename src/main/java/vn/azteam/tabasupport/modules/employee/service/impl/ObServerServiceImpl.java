package vn.azteam.tabasupport.modules.employee.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.dao.ObServerDao;
import vn.azteam.tabasupport.modules.employee.object.model.ObServer;
import vn.azteam.tabasupport.modules.employee.service.ObServerService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.service.impl
 * created 1/25/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see ObServerService
 * @since 1.0.0
 */
@Service
public class ObServerServiceImpl extends BaseServiceImpl implements ObServerService {
	@Autowired
	private ObServerDao obServerDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObServer> getAllObServer() throws NullPointerException, BindingException {
		return obServerDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObServer getObServerByCompanyIdAndCultivationId(long companyId, long cultivationId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllObServer()
				.stream()
				.filter(ob -> ob.getCompanyId() == companyId && ob.getCultivationZoneId() == cultivationId)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createObserver(ObServer obServer, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			obServerDao.insert(obServer);
			return obServer.getId();
		}
		final List<ObjectError> errors = obServer.getErrors();
		if (errors.isEmpty()) {
			obServerDao.insert(obServer);
			return obServer.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createObserver(ObServer obServer) throws ValidationException {
		return createObserver(obServer, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateObserver(ObServer obServer, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			obServerDao.update(obServer);
			return obServer.getId();
		}
		final List<ObjectError> errors = obServer.getErrors();
		if (errors.isEmpty()) {
			obServerDao.update(obServer);
			return obServer.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateObserver(ObServer obServer) throws ValidationException {
		return updateObserver(obServer, false);
	}
}
