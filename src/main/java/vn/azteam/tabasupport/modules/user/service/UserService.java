package vn.azteam.tabasupport.modules.user.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;

import java.util.List;

/**
 * package  vn.azteam.tabasupport.modules.user.service
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.user.service.impl.UserServiceImpl
 * @since 1.0.0
 */
public interface UserService {

	/**
	 * Create new User
	 *
	 * @param u a {@link User} object
	 * @return a {@link Long} account Id.
	 * @throws ValidationException
	 */
	long createUser(User u) throws ValidationException;

	/**
	 * Update exist User
	 *
	 * @param u a {@link User} object.
	 * @throws ValidationException
	 */
	void updateUser(User u) throws ValidationException;

	/**
	 * Get all User
	 *
	 * @return a {@link List<User>} object
	 */
	List<User> getAll();

	/**
	 * Filter by companyIds
	 *
	 * @param userList   {@link List} users
	 * @param companyIds {@link Long} array companyIds
	 */
	void filterByCompanyIds(List<? extends User> userList, long[] companyIds);

	/**
	 * Filter by first name
	 *
	 * @param userList {@link List} users
	 * @param str      {@link String} first name
	 */
	void filterByFirstName(List<? extends User> userList, String str);

	/**
	 * Filter by last name
	 *
	 * @param userList {@link List} users
	 * @param str      {@link String} first name
	 */
	void filterByLastName(List<? extends User> userList, String str);

	/**
	 * Filter by full name
	 *
	 * @param userList {@link List} users
	 * @param str      {@link String} first name
	 */
	void filterByFullName(List<Employee> userList, String str);

	/**
	 * Filter by phone
	 *
	 * @param userList {@link List} users
	 * @param str      {@link String} first name
	 */
	void filterByPhone(List<Employee> userList, String str);
}
