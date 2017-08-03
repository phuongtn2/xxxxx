package vn.azteam.tabasupport.modules.employee.rest.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Division;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.rest.DivisionApi;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;
import vn.azteam.tabasupport.modules.employee.service.DivisionService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest.impl
 * created 3/8/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see DivisionApi
 * @since 1.0.0
 */
@Component
public class DivisionApiImpl extends BaseRestImpl implements DivisionApi {
	static final String MODULE_ID = "EMPLOYEE_MANAGER";

	@Autowired
	private DivisionService divisionService;
	@Autowired
	private DepartmentService departmentService;

	@Override
	public Object updateDivision(OAuth2Authentication auth, @RequestBody Division division) throws ValidationException, NoSuchElementException {
		final Account account = (Account) auth.getPrincipal();
		final Employee employee = account.getEmployee();

		// NOT Admin
		if (!(division.getCompanyId() != account.getCompanyId() && account.hasGlobalPermission("EMPLOYEE_MANAGER", "DIVISION_UPDATE")))
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));

		// not director, sub_director
		if (!employee.isDepartmentManager(division.getCompanyId(), division.getDepartmentId())) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "division"));
		}
		divisionService.updateDivision(division);

		return new SimpleResponse(new Object[]{division});
	}

	@Override
	public Object getDivisions(HttpServletRequest request, OAuth2Authentication auth,
	                           @RequestParam(value = "companyId", defaultValue = "-1") long companyId,
	                           @RequestParam(value = "departmentId", defaultValue = "-1") long departmentId,
	                           @RequestParam(value = "paging", defaultValue = "true") boolean isPaging)
			throws ValidationException, NoSuchElementException {
		final Account account = (Account) auth.getPrincipal();
		final Employee employee = account.getEmployee();
		companyId = companyId < 1 ? account.getCompanyId() : companyId;
		departmentId = departmentId < 1 ? employee.getDepartmentId() : departmentId;
		if (companyId != account.getCompanyId() &&
				(!account.hasGlobalPermission(MODULE_ID, "VIEW"))) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
		}

		//admin
		if (account.hasGlobalPermission(MODULE_ID, "VIEW")) {
			return divisionService.getAllByDepartmentId(companyId, departmentId);
		}

		List<Long> departmentIdtList = departmentService.getChildrenId(employee.getCompanyId(), employee.getDepartmentId());
		departmentIdtList.add(employee.getDepartmentId());

		List<Division> divisions = new ArrayList<>();
		long[] departmentArr = new long[1];
		for (int i = 0; i < departmentIdtList.size(); i++) {
			if (departmentIdtList.get(i) == departmentId) {
				departmentArr[0] = departmentIdtList.get(i);
			}
		}
		try {
			divisions = divisionService.getAllByDepartmentIds(companyId, departmentArr);
		} catch (NullPointerException | NoSuchElementException | BindingException e) {
			logger.error(e);
		}

		return divisions;
	}
}
