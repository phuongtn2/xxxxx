package vn.azteam.tabasupport.modules.cropsession.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.rest.CropRegistrationApi;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.modules.cropsession.rest.impl
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see CropRegistrationApi
 * @since 1.0.0
 */
@Component
public class CropRegistrationApiImpl extends BaseRestImpl implements CropRegistrationApi {
	@Autowired
	private CropSessionService cropSessionService;
	@Autowired
	private EmployeeService employeeService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object registerFarmer(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = new CropRegistration();

		registration.copyPropertiesInListFromMapper(mapper,
				"farmerId", "zoneId", "exp",
				"responsibilityEmployeeId", "registrationAcreage",
				"householdMembers", "labors", "memo");
		Employee responsibilityEmployee = employeeService.getEmployeeById(registration.getResponsibilityEmployeeId());

		registration.setCompanyId(responsibilityEmployee.getCompanyId())
				.setDepartmentId(responsibilityEmployee.getDepartmentId())
				.setDivisionId(responsibilityEmployee.getDivisionId())
				.setCropId(cropSessionService.getCurrentCropSessionId())
				.setCreateId(account.getId())
				.setUpdateId(account.getId())
				.insertAuthor(auth);

		cropSessionService.createCropRegistration(registration);
		return new SimpleResponse(new Object[]{registration});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateRegisterFarmer(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final Employee employee = account.getEmployee();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, employee.getCompanyId());

		registration.copyPropertiesInListFromMapper(mapper,
				"zoneId", "exp", "responsibilityEmployeeId",
				"registrationAcreage", "householdMembers", "labors", "memo", "isCancel");
		Employee responsibilityEmployee = employeeService.getEmployeeById(registration.getResponsibilityEmployeeId());

		registration.setDepartmentId(responsibilityEmployee.getDepartmentId())
				.setDivisionId(responsibilityEmployee.getDivisionId())
				.setUpdateId(account.getId())
				.insertAuthor(auth);

		cropSessionService.updateCropRegistration(registration);

		return new SimpleResponse(new Object[]{registration});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAllRegisterFarmer(HttpServletRequest request, OAuth2Authentication auth) throws ValidationException {
		List<CropRegistration> cropRegistrations;
		final Account account = (Account) auth.getPrincipal();
		long companyId = account.getCompanyId();
		final Map<String, String[]> paramsMapper = request.getParameterMap();

		if (account.hasGlobalPermission("CROP_SESSION", "VIEW")) {
			if (paramsMapper.containsKey("companyId")) {
				String companyIdString = request.getParameter("companyId");
				companyId = StringUtil.convertStringToLong(companyIdString, 0);
			}
			cropRegistrations = cropSessionService.getAllCropRegistrationInCurrentCropByCompanyId(companyId);
		} else {
			cropRegistrations = cropSessionService.getAllCropRegistrationInCurrentCropByRoles(companyId, account);
		}


		// filter result
		if (paramsMapper.containsKey("isCancel")) {
			String isCancelString = request.getParameter("isCancel");
			if (!StringUtil.isEmpty(isCancelString)) {
				cropSessionService.filterCropRegistrationIsCancel(cropRegistrations, StringUtil.convertStringToUnsignedInt(isCancelString, 0));
			}
		}

		if (paramsMapper.containsKey("farmerCode")) {
			String farmerCode = request.getParameter("farmerCode");
			if (!StringUtil.isEmpty(farmerCode)) {
				cropSessionService.filterCropRegistrationByFarmerCode(cropRegistrations, farmerCode.toLowerCase());
			}
		}

		if (paramsMapper.containsKey("farmerFullName")) {
			String farmerFullName = request.getParameter("farmerFullName");
			if (!StringUtil.isEmpty(farmerFullName)) {
				cropSessionService.filterCropRegistrationByFarmerName(cropRegistrations, farmerFullName);
			}
		}

		if (paramsMapper.containsKey("phone")) {
			String phone = request.getParameter("phone");
			if (!StringUtil.isEmpty(phone)) {
				cropSessionService.filterCropRegistrationByPhone(cropRegistrations, phone);
			}
		}

		if (paramsMapper.containsKey("provinceId")) {
			String provinceIdString = request.getParameter("provinceId");
			if (!StringUtil.isEmpty(provinceIdString)) {
				cropSessionService.filterCropRegistrationByProvinceId(cropRegistrations, StringUtil.convertStringToUnsignedInt(provinceIdString, 0));
			}
		}

		if (paramsMapper.containsKey("districtId")) {
			String districtIdString = request.getParameter("districtId");
			if (!StringUtil.isEmpty(districtIdString)) {
				cropSessionService.filterCropRegistrationByDistrictId(cropRegistrations, StringUtil.convertStringToUnsignedInt(districtIdString, 0));
			}
		}

		if (paramsMapper.containsKey("wardId")) {
			String wardIdString = request.getParameter("wardId");
			if (!StringUtil.isEmpty(wardIdString)) {
				cropSessionService.filterCropRegistrationByWardId(cropRegistrations, StringUtil.convertStringToUnsignedInt(wardIdString, 0));
			}
		}

		//paging
		return paging(request, cropRegistrations);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRegisterFarmer(OAuth2Authentication auth, @PathVariable long registrationId) throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		return cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clientSyncRegistrationFarmer(OAuth2Authentication auth) throws NullPointerException, NoSuchElementException {
		final Account account = (Account) auth.getPrincipal();
		return cropSessionService.getAllCropRegistrationInCurrentCropByRoles(account.getCompanyId(), account).stream()
				.filter(registration -> registration.getIsCancel() < 1)
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object serverSyncRegistrationFarmer(OAuth2Authentication auth, @RequestBody List<CropRegistration> registrations) throws NullPointerException, NoSuchElementException, ValidationException {
		logger.info(registrations);
		for (CropRegistration registration : registrations) {
			if (registration.getId() > 0) {
				// check existed crop registration
				CropRegistration cropRegistration = cropSessionService.getCropRegistrationById(registration.getId());
				registration.setNurseryId(cropRegistration.getNurseryId())
						.setCultivationId(cropRegistration.getCultivationId())
						.setFarmerId(cropRegistration.getFarmerId())
						.setCompanyId(cropRegistration.getCompanyId())
						.setDepartmentId(cropRegistration.getDepartmentId())
						.setDivisionId(cropRegistration.getDivisionId())
						.setCropId(cropRegistration.getCropId())
						.setResponsibilityEmployeeId(cropRegistration.getResponsibilityEmployeeId());
			}
			registration.serverSyncRegistrationData((Account) auth.getPrincipal());
		}
		return registrations;
	}
}
