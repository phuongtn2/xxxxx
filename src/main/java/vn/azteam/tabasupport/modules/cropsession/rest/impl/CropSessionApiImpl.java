package vn.azteam.tabasupport.modules.cropsession.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cropsession.object.model.Crop;
import vn.azteam.tabasupport.modules.cropsession.rest.CropSessionApi;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.cropsession.rest.impl
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see CropSessionApi
 * @since 1.0.0
 */
@Component
public class CropSessionApiImpl extends BaseRestImpl implements CropSessionApi {
	@Autowired
	private CropSessionService cropSessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addCropSession(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		final Crop crop = new Crop();
		crop.copyPropertiesFromMapper(mapper);
		crop.insertAuthor(auth);
		cropSessionService.createCropSession(crop);
		return new SimpleResponse(new Object[]{crop});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAllCropSession(HttpServletRequest request, OAuth2Authentication auth) {
		final Map<String, String[]> paramsMapper = request.getParameterMap();

		final List<Crop> cropList = cropSessionService.getAll();

		// filter result
		paramsMapper.forEach((param, values) -> {
			switch (param) {
				case "name":
					String val = values.length > 0 ? values[0] : "";
					if (!val.isEmpty()) {
						cropSessionService.filterByCropName(cropList, val);
					}
					break;
			}
		});

		//paging
		return paging(request, cropList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getCropSession(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long cropId) throws NoSuchElementException {
		return new SimpleResponse(new Object[]{cropSessionService.getCropById(cropId)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateCropSession(OAuth2Authentication auth, @PathVariable long cropId, @RequestBody MultiValueMap<String, String> mapper) throws NoSuchElementException, ValidationException {
		final Crop crop = cropSessionService.getCropById(cropId);
		crop.copyPropertiesInListFromMapper(mapper, "name", "description");
		crop.insertAuthor(auth);
		cropSessionService.updateCrop(crop);
		return new SimpleResponse(new Object[]{crop});
	}
}
