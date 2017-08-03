package vn.azteam.tabasupport.modules.material.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.material.object.model.Material;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.service.impl
 * Created 12/16/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.material.rest.impl.MaterialApiImpl
 * @since 1.0.0
 */
@RestController
@RequestMapping("/material")
public interface MaterialApi {
	/**
	 * Get All materials
	 * @param auth {@link OAuth2Authentication} object
	 * @return a {@link List<Material>} object.
	 */
	@PreAuthorize("hasPermission('MATERIAL_MANAGER','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllMaterial(HttpServletRequest request, OAuth2Authentication auth);

	/**
	 *  Get a material by id
	 * @param auth {@link OAuth2Authentication} object
	 * @param materialId a long
	 * @return a {@link Material} object.
	 */
	@PreAuthorize("hasPermission('MATERIAL_MANAGER','VIEW')")
    @RequestMapping(value = "/{materialId}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    Material getMaterialById(OAuth2Authentication auth, @PathVariable("materialId") long materialId);

	/**
	 * Insert a material
	 * @param auth {@link OAuth2Authentication} object
	 * @param materialMap a {@link Material} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('MATERIAL_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    void insertMaterial(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> materialMap) throws ValidationException;

	/**
	 *  Update Material by id
	 * @param auth auth {@link OAuth2Authentication} object
	 * @param materialId a long
	 * @param materialMap a {@link Material} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('MATERIAL_MANAGER','UPDATE')")
    @RequestMapping(value = "/update/{materialId}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    void updateMaterialById(OAuth2Authentication auth, @PathVariable("materialId") long materialId, @RequestBody MultiValueMap<String, String> materialMap) throws ValidationException;
}