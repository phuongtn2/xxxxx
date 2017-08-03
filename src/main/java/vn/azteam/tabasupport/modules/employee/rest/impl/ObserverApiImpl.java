package vn.azteam.tabasupport.modules.employee.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.employee.object.model.ObServer;
import vn.azteam.tabasupport.modules.employee.rest.ObserverApi;
import vn.azteam.tabasupport.modules.employee.service.ObServerService;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest.impl
 * created 3/9/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see ObserverApi
 * @since 1.0.0
 */
@Component
public class ObserverApiImpl extends BaseRestImpl implements ObserverApi {
	@Autowired
	private ObServerService obServerService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addObserver(OAuth2Authentication auth, @RequestBody ObServer obServer) throws ValidationException {
		obServer.insertAuthor(auth);
		obServerService.createObserver(obServer);
		return new SimpleResponse(new Object[]{obServer});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateObserver(OAuth2Authentication auth, @RequestBody ObServer obServer) throws ValidationException {
		obServer.insertAuthor(auth);
		obServerService.updateObserver(obServer);
		return new SimpleResponse(new Object[]{obServer});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAll(HttpServletRequest request, @RequestParam(name = "paging", defaultValue = "true") boolean isPaging) throws NoSuchElementException {
		return isPaging ? paging(request, obServerService.getAllObServer()) : obServerService.getAllObServer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getObServer(OAuth2Authentication auth, @PathVariable("observerId") long observerId) throws NoSuchElementException {
		return new SimpleResponse(new Object[]{obServerService.getAllObServer()
				.stream().filter(o -> o.getId() == observerId).findFirst().get()});
	}
}
