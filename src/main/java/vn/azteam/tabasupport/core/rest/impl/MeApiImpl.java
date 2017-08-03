package vn.azteam.tabasupport.core.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.MeApi;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.file.service.FileService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.rest.impl
 * created 12/26/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see MeApi
 * @since 1.0.0
 */
@Component
public class MeApiImpl extends BaseRestImpl implements MeApi {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private FileService fileService;

	@Autowired
	private TokenStore tokenStore;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getCurrentUser(OAuth2Authentication auth) {
		final Account account = (Account) auth.getPrincipal();
		Employee employee = employeeService.getEmployeeByuId(account.getuId());
		return new SimpleResponse(new Object[]{employee});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateCurrentUser(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws InvocationTargetException, IllegalAccessException, ValidationException, InstantiationException {
		final Employee employee = employeeService.getEmployeeByuId(((Account) auth.getPrincipal()).getuId());

		employee.copyPropertiesFromMapper(mapper, "id", "uId", "employee");
		employee.setFullName(String.format("%s %s", employee.getFirstName(), employee.getLastName())).insertAuthor(auth);

		employeeService.updateEmployee(employee);

		return employee;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object changePassword(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws InvocationTargetException, IllegalAccessException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		account.copyPropertiesFromMapper(mapper);

		final List<ObjectError> errors = account.validateChangePassword();

		if (errors.isEmpty())
			accountService.changePassword(account);
		else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());

		tokenStore.removeAccessToken(tokenStore.getAccessToken(auth));
		return new SimpleResponse(new Object[]{account});
	}
}
