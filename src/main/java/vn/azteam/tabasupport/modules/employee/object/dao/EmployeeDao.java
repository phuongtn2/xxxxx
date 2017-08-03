package vn.azteam.tabasupport.modules.employee.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.employee.object.dao
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface EmployeeDao {

	/**
	 * Insert Employee
	 *
	 * @param employee a {@link Employee} object.
	 * @return a long
	 */
	long insertEmployee(@Param("employee") Employee employee);

	/**
	 * Get all Employee
	 * @return a {@link List<Employee>} object.
	 */
	List<Employee> findAll();

	/**
	 * Update Employee
	 * @param employee a {@link Employee} object.
	 */
	void updateEmployee(@Param("employee") Employee employee);
}
