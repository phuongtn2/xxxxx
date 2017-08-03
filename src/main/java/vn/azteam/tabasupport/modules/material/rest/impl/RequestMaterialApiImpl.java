package vn.azteam.tabasupport.modules.material.rest.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterial;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterialDetail;
import vn.azteam.tabasupport.modules.material.rest.RequestMaterialApi;
import vn.azteam.tabasupport.modules.material.service.RequestMaterialService;
import vn.azteam.tabasupport.modules.material.service.constant.ApproveStatus;
import vn.azteam.tabasupport.modules.material.service.impl.RequestMaterialServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Package vn.azteam.tabasupport.modules.material.rest.impl
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class RequestMaterialApiImpl extends BaseRestImpl implements RequestMaterialApi {

	@Autowired
	private RequestMaterialService requestMaterialService;

	@Override
	public Object requestMaterial(OAuth2Authentication auth, @RequestBody List<RequestMaterial> requestMaterials) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		for (RequestMaterial requestMaterial : requestMaterials) {
			requestMaterial
					.setCompanyId(account.getCompanyId())
					.setDepartmentId(employee.getDepartmentId())
					.setDivisionId(employee.getDivisionId())
					.setEmployeeRequestId(employee.getId())
					.insertAuthor(auth);
			requestMaterialService.insertRequestMaterial(requestMaterial);
		}
		return requestMaterials;
	}

	@Override
	public Object getAllRequestMaterial(OAuth2Authentication auth, HttpServletRequest request,
	                                    @RequestParam(value = "paging", defaultValue = "true") boolean isPaging,
	                                    @RequestParam(value = "fromDate", defaultValue = "0") long fromDate,
	                                    @RequestParam(value = "toDate", defaultValue = "0") long toDate) {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		Account account = (Account) auth.getPrincipal();
		List<RequestMaterial> requestMaterials = requestMaterialService.getAllRequestMaterialByRole(account.getCompanyId(), account);
		for (RequestMaterial requestMaterial : requestMaterials) {
			List<RequestMaterialDetail> requestMaterialDetails = new ArrayList<>();
			if (requestMaterial.getId() != 0) {
				RequestMaterialService requestMaterialService;
				try {
					requestMaterialService = ApplicationContextProvider.getApplicationContext().getBean(RequestMaterialServiceImpl.class);
					requestMaterialDetails = requestMaterialService.getAllRequestMaterialDetailByRequestId(requestMaterial.getId());
				} catch (NullPointerException | BeansException | BindingException | NoSuchElementException e) {
					logger.error(e);
				}
			}
			requestMaterial.setRequestMaterialDetails(requestMaterialDetails);
		}

		//filter
		int[] approveStatus;
		if (paramsMapper.containsKey("status")) {
			String[] status = paramsMapper.get("status");
			approveStatus = StringUtil.convertArrayStringToUnsignedInt(status, 1);
			requestMaterials = requestMaterialService.filterRequestMaterialByStatus(requestMaterials, approveStatus);
		}
		if (fromDate != 0 && toDate != 0) {
			Date from = new Date(fromDate);
			Date to = new Date(toDate);
			requestMaterials = requestMaterialService.filterRequestMaterialByDate(requestMaterials, from, to);
		}

		//paging
		if (paramsMapper.containsKey("paging")) {
			if (isPaging) {
				return paging(request, requestMaterials);
			} else {
				return requestMaterials;
			}
		}
		return paging(request, requestMaterials);
	}

	@Override
	public Object approveRequestMaterial(OAuth2Authentication auth, @RequestBody List<RequestMaterial> requestMaterials) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();

		for (RequestMaterial newRequestMaterial : requestMaterials) {
			//check and get existed request material
			RequestMaterial currentRequestMaterial = requestMaterialService.getRequestMaterialById(account.getCompanyId(), account, newRequestMaterial.getId());
			updateRequestMaterial(account, currentRequestMaterial);

			//Update request material detail
			List<RequestMaterialDetail> newRequestMaterialDetails = newRequestMaterial.getRequestMaterialDetails();
			if (newRequestMaterialDetails != null) {
				for (RequestMaterialDetail newRequestMaterialDetail : newRequestMaterialDetails) {
					updateRequestMaterialDetail(employee, currentRequestMaterial, newRequestMaterialDetail);
				}
			}
		}
		return requestMaterials;
	}

	private RequestMaterial updateRequestMaterial(Account account, RequestMaterial currentRequestMaterial) throws ValidationException {
		Employee employee = account.getEmployee();
		if (employee.isMiddleApprove(currentRequestMaterial.getCompanyId(), currentRequestMaterial.getDivisionId())
				&& currentRequestMaterial.getApprovedStatus() == ApproveStatus.NEW_REQUEST.getValue()) {
			currentRequestMaterial.setApprovedStatus(ApproveStatus.MIDDLE_APPROVED.getValue());
		} else if (employee.isLatestApprove(currentRequestMaterial.getCompanyId(), currentRequestMaterial.getDivisionId())) {
			currentRequestMaterial.setApprovedStatus(ApproveStatus.LATEST_APPROVED.getValue());
		} else {
			logger.error("Can not update status approved.");
		}
		requestMaterialService.updateRequestMaterial(currentRequestMaterial, account);
		return currentRequestMaterial;
	}

	private RequestMaterialDetail updateRequestMaterialDetail(Employee employee, RequestMaterial currentRequestMaterial, RequestMaterialDetail newRequestMaterialDetail) throws ValidationException {
		RequestMaterialDetail currentRequestMaterialDetail = requestMaterialService.getRequestMaterialDetailById(newRequestMaterialDetail.getRequestMaterialId(), newRequestMaterialDetail.getId());

		if (employee.isMiddleApprove(currentRequestMaterial.getCompanyId(), currentRequestMaterial.getDivisionId())
				&& currentRequestMaterial.getApprovedStatus() == ApproveStatus.MIDDLE_APPROVED.getValue()) {
			currentRequestMaterialDetail.setApproverLv1Id(employee.getId())
					.setApprovedLv1Date(newRequestMaterialDetail.getApprovedLv1Date())
					.setApproveLv1Quantity(newRequestMaterialDetail.getApproveLv1Quantity());
		} else if (employee.isLatestApprove(currentRequestMaterial.getCompanyId(), currentRequestMaterial.getDivisionId())) {
			currentRequestMaterialDetail.setApproverLv2Id(employee.getId())
					.setApprovedLv2Date(newRequestMaterialDetail.getApprovedLv2Date())
					.setApproveLv2Quantity(newRequestMaterialDetail.getApproveLv2Quantity());
		} else {
			logger.error("Can not update status approved.");
		}
		requestMaterialService.updateRequestMaterialDetail(currentRequestMaterialDetail);
		return currentRequestMaterialDetail;
	}
}
