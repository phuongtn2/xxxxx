package vn.azteam.tabasupport.modules.employee.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.Department;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.service
 * created 1/24/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface DepartmentService {
	List<Department> getAllByCompanyId(long companyId) throws NullPointerException, BindingException;

	Department getDepartmentById(long companyId, long departmentId) throws NullPointerException, BindingException, NoSuchElementException;

	List<Long> getChildrenId(long companyId, long parentId);

	long updateDepartment(Department department, boolean withoutValidated) throws ValidationException;

	long updateDepartment(Department department) throws ValidationException;
}
