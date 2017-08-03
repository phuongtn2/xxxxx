package vn.azteam.tabasupport.modules.employee.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.service
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface EmployeeService {
	/**
	 * Create new Employee
	 *
	 * @param employee a {@link Employee} object
	 * @param roleSlug a {@link String} role slug
	 * @throws ValidationException
	 * @retur a {@link Long} employeeId
	 */
	long createEmployee(Employee employee, String roleSlug) throws ValidationException;

	/**
	 * Update exist Employee
	 *
	 * @param employee a {@link Employee} object
	 * @throws ValidationException
	 */
	void updateEmployee(Employee employee) throws ValidationException, NullPointerException;

	/**
	 * Get All Employee
	 *
	 * @return a {@link List<Employee>} object
	 * @throws NullPointerException
	 * @throws BindingException
	 */
	List<Employee> getAll() throws NullPointerException, BindingException;

	/**
	 * Get employee by id
	 *
	 * @param employeeId a {@link Long} employeeId
	 * @return a {@link Employee} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	Employee getEmployeeById(long employeeId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get employee by uId
	 *
	 * @param uId a {@link Long} uid
	 * @return a {@link Employee} object
	 */
	Employee getEmployeeByuId(long uId);

	/**
	 * Filter by EmployeeCode.
	 *
	 * @param listEmployees a {@link List} object
	 * @param str           a {@link String} object
	 */
	void filterByEmployeeCode(List<Employee> listEmployees, String str);
}
