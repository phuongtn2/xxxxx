package vn.azteam.tabasupport.modules.material.rest.impl;

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
import vn.azteam.tabasupport.modules.material.object.model.CompanyMaterial;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.rest.CompanyMaterialApi;
import vn.azteam.tabasupport.modules.material.service.CompanyMaterialService;
import vn.azteam.tabasupport.modules.material.service.MaterialService;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Package vn.azteam.tabasupport.modules.material.rest.impl
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class CompanyMaterialApiImpl extends BaseRestImpl implements CompanyMaterialApi {
	@Autowired
	private CompanyMaterialService companyMaterialService;
	@Autowired
	private MaterialService materialService;

	@Override
	public Object addCompanyMaterial(OAuth2Authentication auth, @PathVariable long materialId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		final Material material = materialService.getMaterialById(materialId);
		Account account = (Account) auth.getPrincipal();
		final CompanyMaterial companyMaterial = new CompanyMaterial();
		companyMaterial.insertAuthor(auth);
		companyMaterial.setCompanyId(account.getCompanyId());
		companyMaterial.setMaterialId(materialId);
		companyMaterial.copyPropertiesInListFromMapper(mapper, "status", "price", "memo");
		long companyMaterialId = companyMaterialService.insertCompanyMaterial(companyMaterial);
		companyMaterial.setId(companyMaterialId);
		// copy field from parent Class
		companyMaterial.setSku(material.getSku());
		companyMaterial.setName(material.getName());
		companyMaterial.setUnit(material.getUnit());
		companyMaterial.setVolumeWeight(material.getVolumeWeight());
		companyMaterial.setType(material.getType());
		companyMaterial.setSubType(material.getSubType());
		return new SimpleResponse(new Object[]{companyMaterial});
	}

	@Override
	public Object updateCompanyMaterial(OAuth2Authentication auth, @PathVariable long companyMaterialId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		final CompanyMaterial companyMaterial = companyMaterialService.getCompanyMaterialById(account.getCompanyId(), companyMaterialId);

		companyMaterial.insertAuthor(auth);
		companyMaterial.copyPropertiesInListFromMapper(mapper, "status", "price", "memo");
		companyMaterialService.updateCompanyMaterial(companyMaterial);
		return new SimpleResponse(new Object[]{companyMaterial});
	}

	@Override
	public void deleteCompanyMaterial(OAuth2Authentication auth, @PathVariable long companyMaterialId) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		//check existed companyMaterialId
		companyMaterialService.getCompanyMaterialById(account.getCompanyId(), companyMaterialId);
		companyMaterialService.deleteCompanyMaterial(companyMaterialId);
	}

	@Override
	public Object getCompanyMaterial(HttpServletRequest request, OAuth2Authentication auth) throws ValidationException {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		Account account = (Account) auth.getPrincipal();
		final List<CompanyMaterial> companyMaterials = companyMaterialService.getAllCompanyMaterial(account.getCompanyId());

		// filter result
		if (paramsMapper.containsKey("type")) {
			String type = request.getParameter("type");
			if (!StringUtil.isEmpty(type)) {
				companyMaterialService.filterCompanyMaterialByType(companyMaterials, type);
			}
		}

		if (paramsMapper.containsKey("subType")) {
			String subType = request.getParameter("subType");
			if (!StringUtil.isEmpty(subType)) {
				companyMaterialService.filterCompanyMaterialBySubType(companyMaterials, subType);
			}
		}

		if (paramsMapper.containsKey("status")) {
			String status = request.getParameter("status");
			if (!StringUtil.isEmpty(status)) {
				companyMaterialService.filterCompanyMaterialByStatus(companyMaterials, status);
			}
		}

		if (paramsMapper.containsKey("name")) {
			String materialName = request.getParameter("name").toLowerCase();
			if (!StringUtil.isEmpty(materialName)) {
				companyMaterialService.filterCompanyMaterialByName(companyMaterials, materialName);
			}
		}
		return paging(request, companyMaterials);
	}

	@Override
	public Object syncCompanyMaterial(OAuth2Authentication auth) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		return companyMaterialService.getAllCompanyMaterial(account.getCompanyId());
	}
}
