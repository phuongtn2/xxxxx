package vn.azteam.tabasupport.modules.material.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterial;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterialDetail;

import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.service
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface RequestMaterialService {
	RequestMaterial insertRequestMaterial(RequestMaterial requestMaterial) throws ValidationException;

	RequestMaterialDetail insertRequestMaterialDetail(RequestMaterialDetail requestMaterialDetail) throws ValidationException;

	RequestMaterial updateRequestMaterial(RequestMaterial requestMaterial, Account account) throws ValidationException;

	RequestMaterialDetail updateRequestMaterialDetail(RequestMaterialDetail requestMaterialDetail) throws ValidationException;

	List<RequestMaterial> getAllRequestMaterialByCompanyId(long companyId);

	List<RequestMaterial> getAllRequestMaterialByRole(long companyId, Account account);

	List<RequestMaterial> filterRequestMaterialByStatus(List<RequestMaterial> requestMaterials, int[] status);

	List<RequestMaterial> filterRequestMaterialByDate(List<RequestMaterial> requestMaterials, Date fromDate, Date toDate);

	RequestMaterial getRequestMaterialById(long companyId, Account account, long requestMaterialId);

	List<RequestMaterialDetail> getAllRequestMaterialDetailByRequestId(long requestId);

	RequestMaterialDetail getRequestMaterialDetailById(long requestId, long detailId);
}
