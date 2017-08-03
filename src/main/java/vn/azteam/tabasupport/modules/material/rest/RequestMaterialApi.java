package vn.azteam.tabasupport.modules.material.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterial;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.rest
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/material")
public interface RequestMaterialApi {

	@PreAuthorize("hasPermission('MATERIAL_MANAGER','REQUEST')")
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
	Object requestMaterial(OAuth2Authentication auth, @RequestBody List<RequestMaterial> requestMaterials) throws ValidationException;

	@PreAuthorize("hasPermission('MATERIAL_MANAGER','APPROVE')")
	@RequestMapping(value = "/approve", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
	Object approveRequestMaterial(OAuth2Authentication auth, @RequestBody List<RequestMaterial> requestMaterials) throws ValidationException;

	@PreAuthorize("hasPermission('MATERIAL_MANAGER','VIEW_REQUEST')")
	@RequestMapping(value = "/request/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object getAllRequestMaterial(OAuth2Authentication auth, HttpServletRequest request,
	                             @RequestParam(value = "paging", defaultValue = "true") boolean isPaging,
	                             @RequestParam(value = "fromDate", defaultValue = "0") long fromDate,
	                             @RequestParam(value = "toDate", defaultValue = "0") long toDate);
}
