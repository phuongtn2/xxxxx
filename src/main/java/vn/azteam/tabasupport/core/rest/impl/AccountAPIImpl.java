package vn.azteam.tabasupport.core.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.cache.ResetPasswordTokenCache;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.MailTemplate;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.AccountAPI;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.MailTemplateService;
import vn.azteam.tabasupport.core.service.NotificationService;
import vn.azteam.tabasupport.core.service.impl.OAuth2ClientDetailServiceImpl;
import vn.azteam.tabasupport.core.service.impl.OAuth2UserDetailServiceImpl;
import vn.azteam.tabasupport.util.PropertiesParserUtil;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.core.rest.impl
 * created 12/26/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see AccountAPI
 * @since 1.0.0
 */
@Component
public class AccountAPIImpl extends BaseRestImpl implements AccountAPI {
	@Autowired
	private OAuth2ClientDetailServiceImpl clientDetails;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private ResetPasswordTokenCache resetPasswordTokenCache;

	@Autowired
	private AccountService accountService;

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private MailTemplateService mailTemplateService;
	@Autowired
	private OAuth2UserDetailServiceImpl userDetailsService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getResetPassCode(HttpServletRequest request) throws ValidationException, NoSuchElementException {
		final String email = request.getParameter("email");

		if (email == null || email.isEmpty() || !email.matches(PropertiesParserUtil.getProperty("validation.model.pattern.email.regexp")))
			throw new ValidationException("email_code", "Email incorrect.");

		Account account;
		try {
			account = accountService.getAccountByEmail(email);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new ValidationException("account_not_found_code", String.format("Account with email %s not found.", email));
		}

		final String uuid = resetPasswordTokenCache.cache(account);
		final HashMap<String, String> tplArgs = new HashMap<>();

		tplArgs.put("account_name", account.getFullName());
		tplArgs.put("reset_code", uuid);
		tplArgs.put("site_url", PropertiesParserUtil.getProperty("default.site.url"));

		final MailTemplate tpl = mailTemplateService.getMailContent("mail_tpl_reset_password_code", account.getCompanyId(), true, tplArgs);

		if (tpl == null) throw new NullPointerException("Không tìm thấy mail template.");

		notificationService.sendNotifyMailToAccount(account, tpl.getTitle(), tpl.getContent());

		return new SimpleResponse(SimpleResponse.STATUS_COMPLETED, "Success !");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object resetPassword(HttpServletRequest request, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException, NoSuchElementException, InvocationTargetException, IllegalAccessException {
		final ClientDetails clientDetail = clientDetails.loadClientByClientId(request.getParameter("clientId"));
		final String uuid = request.getParameter("code");
		final Account account = resetPasswordTokenCache.getCached(uuid);

		if (clientDetail == null)
			throw new ValidationException("clientId_code", "Client id not found.");

		if (uuid == null || uuid.isEmpty())
			throw new ValidationException("reset_code", "Code incorrect.");

		if (account == null)
			throw new ValidationException("code_not_found_code", "Reset code not found or expired.");

		account.copyPropertiesFromMapper(mapper);

		final List<ObjectError> errors = account.validateResetPassword();

		if (errors.isEmpty()) {
			accountService.changePassword(account);
			resetPasswordTokenCache.remove(uuid);
			final List<OAuth2AccessToken> oAuth2AccessTokens = tokenStore.findTokensByClientIdAndUserName(clientDetail.getClientId(), account.getUsername())
					.parallelStream()
					.collect(Collectors.toList());

			if (!oAuth2AccessTokens.isEmpty()) {
				oAuth2AccessTokens.forEach(token -> tokenStore.removeAccessToken(token));
			}

		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());

		return new SimpleResponse(SimpleResponse.STATUS_COMPLETED, "Success!");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object revokeToken(HttpServletRequest request, OAuth2Authentication auth) {
		Account account = (Account) auth.getPrincipal();
		String pushKey = request.getHeader("pushKey");
		String deviceType = request.getHeader("deviceType");

		if (!StringUtil.isEmpty(pushKey) && (!StringUtil.isEmpty(deviceType))) {
			account.setPushKey("");
			account.setDeviceType("");
			userDetailsService.updatePushInformation(account);
		}
		tokenStore.removeAccessToken(tokenStore.getAccessToken(auth));
		return new SimpleResponse();
	}
}
