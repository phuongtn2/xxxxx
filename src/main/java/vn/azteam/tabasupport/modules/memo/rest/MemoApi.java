package vn.azteam.tabasupport.modules.memo.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.memo.object.model.Memo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.memo.rest
 * Created 12/22/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.memo.rest.impl.MemoApiImpl
 * @since 1.0.0
 */
@RestController
@RequestMapping("/memo")
public interface MemoApi {

	/**
	 * Insert a memo
	 * @param auth {@link OAuth2Authentication} object
	 * @param memoMap a {@link Memo} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('MEMO_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object addMemo(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> memoMap) throws ValidationException;

	/**
	 * Update a memo
	 * @param auth {@link OAuth2Authentication} object
	 * @param memoId a long
	 * @param mapper a {@link Memo} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('MEMO_MANAGER','UPDATE')")
	@RequestMapping(value = "/update/{memoId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object updateMemoById(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long memoId, @RequestBody MultiValueMap<String, String> mapper)
			throws NullPointerException, ValidationException, IllegalAccessException, InstantiationException, InvocationTargetException;

	/**
	 * Delete a memo
	 * @param auth {@link OAuth2Authentication} object
	 * @param memoId a long
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('MEMO_MANAGER','DELETE')")
	@RequestMapping(value = "/delete/{memoId}", method = RequestMethod.DELETE, headers = "Accept=application/json", produces = {"application/json"})
	void deleteMemoById(OAuth2Authentication auth, @PathVariable("memoId") long memoId) throws ValidationException;

	/**
	 * Get all memo of user
	 * @param request a {@link HttpServletRequest} object
	 * @param auth {@link OAuth2Authentication} object
	 * @return a {@link List<Memo>} object
	 */
	@PreAuthorize("hasPermission('MEMO_MANAGER','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllMemo(HttpServletRequest request, OAuth2Authentication auth);

	/**
	 * Get a memo by id
	 * @param auth {@link OAuth2Authentication} object
	 * @param memoId a long
	 * @return a {@link Memo} object
	 */
	@PreAuthorize("hasPermission('MEMO_MANAGER','VIEW')")
	@RequestMapping(value = "/{memoId}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Memo getMemoById(OAuth2Authentication auth, @PathVariable("memoId") long memoId);
}
