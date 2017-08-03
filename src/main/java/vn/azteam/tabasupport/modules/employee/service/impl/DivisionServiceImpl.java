package vn.azteam.tabasupport.modules.employee.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.dao.DivisionDao;
import vn.azteam.tabasupport.modules.employee.object.model.Division;
import vn.azteam.tabasupport.modules.employee.service.DivisionService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.modules.employee.service.impl
 * created 1/24/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see DivisionService
 * @since 1.0.0
 */
@Service
public class DivisionServiceImpl extends BaseServiceImpl implements DivisionService {
	@Autowired
	private DivisionDao divisionDao;

	@Override
	public List<Division> getAllByCompanyId(long companyId) throws NullPointerException, NoSuchElementException, BindingException {
		return divisionDao.findAllByCompanyId(companyId);
	}

	@Override
	public List<Division> getAllByDepartmentId(long companyId, long departmentId) {
		return getAllByCompanyId(companyId).stream().filter(
				division -> division.getDepartmentId() == departmentId
		).collect(Collectors.toList());
	}

	@Override
	public List<Division> getAllByDepartmentIds(long companyId, long[] departmentIds) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllByCompanyId(companyId)
				.stream()
				.filter(division -> ArrayUtils.contains(departmentIds, division.getDepartmentId()))
				.collect(Collectors.toList());
	}

	@Override
	public Division getDivisionById(long companyId, long divisionId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllByCompanyId(companyId)
				.stream()
				.filter(div -> div.getId() == divisionId)
				.findFirst()
				.get();
	}

	@Override
	public long updateDivision(Division division) {
		divisionDao.update(division);
		return division.getId();
	}
}
