package vn.azteam.tabasupport.modules.material.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;

/**
 * Package vn.azteam.tabasupport.modules.material.rest
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/material")
public interface CompanyMaterialApi {
	@PreAuthorize("hasPermission('MATERIAL_MANAGER', 'ADD_NEW')")
	@RequestMapping(value = "/{materialId:[0-9]+}/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addCompanyMaterial(OAuth2Authentication auth, @PathVariable long materialId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('MATERIAL_MANAGER', 'ADD_NEW')")
	@RequestMapping(value = "/{companyMaterialId:[0-9]+}/update", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object updateCompanyMaterial(OAuth2Authentication auth, @PathVariable long companyMaterialId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('MATERIAL_MANAGER', 'ADD_NEW')")
	@RequestMapping(value = "/{companyMaterialId:[0-9]+}/delete", method = RequestMethod.DELETE)
	void deleteCompanyMaterial(OAuth2Authentication auth, @PathVariable long companyMaterialId) throws ValidationException;

	@PreAuthorize("hasPermission('MATERIAL_MANAGER', 'VIEW')")
	@RequestMapping(value = "/company/get", method = RequestMethod.GET, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object getCompanyMaterial(HttpServletRequest request, OAuth2Authentication auth) throws ValidationException;

	@PreAuthorize("hasPermission('MATERIAL_MANAGER', 'VIEW')")
	@RequestMapping(value = "/company/sync", method = RequestMethod.GET, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object syncCompanyMaterial(OAuth2Authentication auth) throws ValidationException;
}
