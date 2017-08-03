package vn.azteam.tabasupport.modules.employee.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.dao.DepartmentDao;
import vn.azteam.tabasupport.modules.employee.object.model.Department;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.service.impl
 * created 1/24/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see DepartmentService
 * @since 1.0.0
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentDao departmentDao;

	public List<Department> getAllByCompanyId(long companyId) throws NullPointerException, BindingException {
		return departmentDao.findAllByCompanyId(companyId);
	}

	@Override
	public Department getDepartmentById(long companyId, long departmentId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllByCompanyId(companyId)
				.stream()
				.filter(d -> d.getId() == departmentId)
				.findFirst()
				.get();
	}

	@Override
	public List<Long> getChildrenId(long companyId, long parentId) {
		final List<Long> departmentIds = new ArrayList<>();
		final List<Department> departments = getAllByCompanyId(companyId);

		departments.sort((c1, c2) -> Long.compare(c1.getParentId(), c2.getParentId()));
		departments.forEach(c -> {
			if (c.getParentId() == parentId
					|| departmentIds.contains(c.getParentId())) {
				departmentIds.add(c.getId());
			}
		});

		return departmentIds;
	}

	@Override
	public long updateDepartment(Department department, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			departmentDao.update(department);
			return department.getId();
		}
		final List<ObjectError> errors = department.getErrors();
		if (errors.isEmpty()) {
			departmentDao.update(department);
			return department.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	@Override
	public long updateDepartment(Department department) throws ValidationException {
		return updateDepartment(department, false);
	}
}
