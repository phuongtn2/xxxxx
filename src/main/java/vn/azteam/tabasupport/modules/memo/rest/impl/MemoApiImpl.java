package vn.azteam.tabasupport.modules.memo.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.memo.object.model.Memo;
import vn.azteam.tabasupport.modules.memo.rest.MemoApi;
import vn.azteam.tabasupport.modules.memo.service.MemoService;
import vn.azteam.tabasupport.util.MapBeanConverter;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.memo.rest.impl
 * Created 12/22/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Component
public class MemoApiImpl extends BaseRestImpl implements MemoApi {
	@Autowired
	private MemoService memoService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addMemo(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> memoMap) throws ValidationException {
		final Memo memo = MapBeanConverter.convertMapToDtoClass(Memo.class, memoMap);
		assert memo != null;
		memo.insertAuthor(auth);
		long memoId = memoService.createMemo(memo);
		memo.setId(memoId);
		return memo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateMemoById(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long memoId, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final Memo memo = memoService.getMemoById(memoId, account.getId());

		logger.info(mapper);

		if (memo == null) {
			throw new ValidationException(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "Memo", "id"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "Memo"));
		}
		memo.copyPropertiesFromMapper(mapper, "id");
		memo.insertAuthor(auth);
		memoService.updateMemo(memoId, memo);
		return memo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteMemoById(OAuth2Authentication auth, @PathVariable("memoId") long memoId) throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		Memo existedMemo = memoService.getMemoById(memoId, account.getId());
		if (existedMemo != null) {
			memoService.deleteMemo(memoId, account.getId());
		} else {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "memo another"));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAllMemo(HttpServletRequest request, OAuth2Authentication auth) {
		final Account account = (Account) auth.getPrincipal();
		final List<Memo> memoList = memoService.getAllMemo(account.getId());
		return paging(request, memoList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Memo getMemoById(OAuth2Authentication auth, @PathVariable("memoId") long memoId) {
		final Account account = (Account) auth.getPrincipal();
		return memoService.getMemoById(memoId, account.getId());
	}
}
