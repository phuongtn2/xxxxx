package vn.azteam.tabasupport.modules.employee.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.modules.employee.object.model.Division;

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
public interface DivisionService {
	List<Division> getAllByCompanyId(long companyId) throws NullPointerException, NoSuchElementException, BindingException;

	List<Division> getAllByDepartmentId(long companyId, long departmentId);

	List<Division> getAllByDepartmentIds(long companyId, long[] departmentIds) throws NullPointerException, BindingException, NoSuchElementException;

	Division getDivisionById(long companyId, long divisionId) throws NullPointerException, BindingException, NoSuchElementException;

	long updateDivision(Division division);
}
