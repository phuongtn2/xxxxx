package vn.azteam.tabasupport.modules.user.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.dao.UserDao;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.user.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.user.service.impl
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see UserService
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createUser(User u) throws ValidationException {
		final List<ObjectError> errors = u.getErrors();
		if (errors.isEmpty()) {
			userDao.insertUser(u);
			return u.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUser(User u) throws ValidationException {
		final List<ObjectError> errors = u.getErrors();
		if (errors.isEmpty()) {
			userDao.updateUser(u);
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getAll() {
		return userDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByCompanyIds(List<? extends User> userList, long[] companyIds) {
		userList.removeIf(user -> user.getCompanyId() == 0 || !Arrays.asList(ArrayUtils.toObject(companyIds)).contains(user.getCompanyId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByFirstName(List<? extends User> userList, String str) {
		userList.removeIf(user -> !user.getFirstName().toLowerCase().matches(String.format(".*%s.*", str).toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByLastName(List<? extends User> userList, String str) {
		userList.removeIf(user -> !user.getLastName().toLowerCase().matches(String.format(".*%s.*", str).toString()));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByFullName(List<Employee> userList, String str) {
		userList.removeIf(user -> !user.getFullName().toLowerCase().matches(String.format(".*%s.*", str).toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByPhone(List<Employee> userList, String str) {
		userList.removeIf(user -> !user.getPhone().toLowerCase().matches(String.format(".*%s.*", str).toString()));
	}
}
