package vn.azteam.tabasupport.modules.nursery.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.nursery.object.dao.NurseryDao;
import vn.azteam.tabasupport.modules.nursery.object.dao.NurserySeedDao;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.service.impl
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see NurseryService
 * @since 1.0.0
 */
@Service
public class NurseryServiceImpl extends BaseServiceImpl implements NurseryService {
	@Autowired
	private NurseryDao nurseryDao;

	@Autowired
	private NurserySeedDao nurserySeedDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createNursery(Nursery nursery) throws ValidationException {
		final List<ObjectError> errors = nursery.getErrors();
		if (errors.isEmpty()) {
			nurseryDao.insert(nursery);
			nurserySeedDao.insert(nursery);
			return nursery.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateNursery(Nursery nursery) throws ValidationException {
		final List<ObjectError> errors = nursery.getErrors();
		if (errors.isEmpty()) {
			nurseryDao.update(nursery);
			nurserySeedDao.update(nursery);
			return nursery.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Nursery> getAllNurseryByRegistrationIds(long[] registrationIds) throws NullPointerException, BindingException, NoSuchElementException {
		return nurseryDao.getAllNurseryByRegistrationIds(registrationIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Nursery> getAllNurseryByRegistrationId(long registrationId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllNurseryByRegistrationIds(new long[]{registrationId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Nursery getNurseryById(long nurseryId, long registrationId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllNurseryByRegistrationId(registrationId)
				.stream()
				.filter(n -> n.getId() == nurseryId)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Nursery> getAllNurseryByResponsibilityEmployeeId(long responsibilityEmployeeId) throws NullPointerException, BindingException, NoSuchElementException {
		return nurseryDao.findAllNurseryByResponsibilityEmployeeId(responsibilityEmployeeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Nursery> syncList(List<Nursery> nurseries, Account account) throws NullPointerException, BindingException, ValidationException {
		for (Nursery nursery : nurseries) {
			try {
				nursery.save(account);
			} catch (NullPointerException | BindingException | ValidationException e) {
				throw new ValidationException("sync_error_code", e.getMessage());
			}
		}

		return nurseries;
	}

}
