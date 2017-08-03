package vn.azteam.tabasupport.modules.pest.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.pest.object.model.Pest;
import vn.azteam.tabasupport.modules.pest.rest.PestApi;
import vn.azteam.tabasupport.modules.pest.service.PestService;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.pest.rest.impl
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see PestApi
 * @since 1.0.0
 */
@Component
public class PestApiImpl extends BaseRestImpl implements PestApi {
	@Autowired
	private PestService pestService;

	@Override
	public Object getAll(HttpServletRequest request, OAuth2Authentication auth, @RequestParam(value = "paging", defaultValue = "true") boolean isPaging) {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		List<Pest> pests = pestService.getAll();

		List<Pest> pestFilters = new ArrayList<>();
		if (paramsMapper.containsKey("type")) {
			String type = request.getParameter("type");
			if (!StringUtil.isEmpty(type)) {
				pestFilters = pests.stream().filter(
						pest -> pest.getType().equalsIgnoreCase(type)
				).collect(Collectors.toList());
			}
			return isPaging ? paging(request, pestFilters) : pestFilters;
		}

		return isPaging ? paging(request, pests) : pests;
	}
}
