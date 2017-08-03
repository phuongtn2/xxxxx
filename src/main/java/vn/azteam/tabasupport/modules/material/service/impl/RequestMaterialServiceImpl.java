package vn.azteam.tabasupport.modules.material.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Division;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;
import vn.azteam.tabasupport.modules.employee.service.DivisionService;
import vn.azteam.tabasupport.modules.material.object.dao.RequestMaterialDao;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterial;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterialDetail;
import vn.azteam.tabasupport.modules.material.service.RequestMaterialService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.material.service.impl
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class RequestMaterialServiceImpl extends BaseServiceImpl implements RequestMaterialService {

	@Autowired
	private RequestMaterialDao requestMaterialDao;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DivisionService divisionService;

	@Override
	public RequestMaterial insertRequestMaterial(RequestMaterial requestMaterial) throws ValidationException {
		final List<ObjectError> errors = requestMaterial.getErrors();
		List<RequestMaterialDetail> requestMaterialDetails = requestMaterial.getRequestMaterialDetails();
		if (errors.isEmpty()) {
			requestMaterialDao.insertRequestMaterial(requestMaterial);

			//insert request material detail
			if (requestMaterialDetails != null) {
				for (RequestMaterialDetail requestMaterialDetail : requestMaterialDetails) {
					if (requestMaterial.getId() != 0) {
						requestMaterialDetail.setRequestMaterialId(requestMaterial.getId());
					}
					insertRequestMaterialDetail(requestMaterialDetail);
				}
			}
			return requestMaterial;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public RequestMaterialDetail insertRequestMaterialDetail(RequestMaterialDetail requestMaterialDetail) throws ValidationException {
		final List<ObjectError> errors = requestMaterialDetail.getErrors();
		if (errors.isEmpty()) {
			requestMaterialDao.insertRequestMaterialDetail(requestMaterialDetail);
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
		return requestMaterialDetail;
	}

	@Override
	public RequestMaterial updateRequestMaterial(RequestMaterial requestMaterial, Account account) throws ValidationException {
		final List<ObjectError> errors = requestMaterial.getErrors();
		if (errors.isEmpty()) {
			//check existed request material
			getRequestMaterialById(requestMaterial.getCompanyId(), account, requestMaterial.getId());
			requestMaterialDao.updateRequestMaterial(requestMaterial);
			return requestMaterial;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public RequestMaterialDetail updateRequestMaterialDetail(RequestMaterialDetail requestMaterialDetail) throws ValidationException {
		final List<ObjectError> errors = requestMaterialDetail.getErrors();
		if (errors.isEmpty()) {
			requestMaterialDao.updateRequestMaterialDetail(requestMaterialDetail);
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
		return requestMaterialDetail;
	}

	@Override
	public List<RequestMaterial> getAllRequestMaterialByCompanyId(long companyId) {
		return requestMaterialDao.findAllRequestMaterialByCompanyId(companyId);
	}

	@Override
	public List<RequestMaterial> getAllRequestMaterialByRole(long companyId, Account account) {
		Employee employee = account.getEmployee();
		if (employee.isDepartmentManager(companyId, employee.getDepartmentId())) {
			//get all managed department
			List<Long> departmentIds = employee.getManagedDepartment(companyId, employee.getDepartmentId());
			departmentIds.add(employee.getDepartmentId());

			return getAllRequestMaterialByCompanyId(companyId).stream().filter(
					requestMaterial -> departmentIds.contains(requestMaterial.getDepartmentId())
			).collect(Collectors.toList());
		} else if (employee.isDivisionManager(companyId, employee.getDivisionId())) {
			//get All child division
			List<Long> departmentIdtList = departmentService.getChildrenId(employee.getCompanyId(), employee.getDepartmentId());
			departmentIdtList.add(employee.getDepartmentId());
			long[] departmentArr = new long[]{departmentIdtList.size()};
			for (int i = 0; i < departmentIdtList.size(); i++) {
				departmentArr[i] = departmentIdtList.get(i);
			}

			List<Division> divisions = divisionService.getAllByDepartmentIds(companyId, departmentArr);
			List<Long> divisionArr = new ArrayList<>();
			for (int i = 0; i < divisions.size(); i++) {
				divisionArr.add(divisions.get(i).getId());
			}
			return getAllRequestMaterialByCompanyId(companyId).stream().filter(
					requestMaterial -> divisionArr.contains(requestMaterial.getDivisionId())
			).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<RequestMaterial> filterRequestMaterialByStatus(List<RequestMaterial> requestMaterials, int[] status) {
		return requestMaterials.stream().filter(
				requestMaterial -> Arrays.asList(ArrayUtils.toObject(status)).contains(requestMaterial.getApprovedStatus())
		).collect(Collectors.toList());
	}

	@Override
	public List<RequestMaterial> filterRequestMaterialByDate(List<RequestMaterial> requestMaterials, Date fromDate, Date toDate) {
		return requestMaterials.stream().filter(
				requestMaterial -> requestMaterial.getRequestDate().after(fromDate) && requestMaterial.getRequestDate().before(toDate)
		).collect(Collectors.toList());
	}

	@Override
	public RequestMaterial getRequestMaterialById(long companyId, Account account, long requestMaterialId) {
		return getAllRequestMaterialByRole(companyId, account).stream().filter(
				requestMaterial -> requestMaterial.getId() == requestMaterialId
		).findFirst().get();
	}

	@Override
	public List<RequestMaterialDetail> getAllRequestMaterialDetailByRequestId(long requestId) {
		return requestMaterialDao.findAllRequestMaterialDetailByRequestId(requestId);
	}

	@Override
	public RequestMaterialDetail getRequestMaterialDetailById(long requestId, long detailId) {
		return getAllRequestMaterialDetailByRequestId(requestId).stream().filter(
				requestMaterialDetail -> requestMaterialDetail.getId() == detailId
		).findFirst().get();
	}
}
