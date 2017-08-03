package vn.azteam.tabasupport.modules.memo.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.memo.object.model.Memo;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.memo.service
 * Created 12/22/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.memo.service.impl.MemoServiceImpl
 * @since 1.0.0
 */
public interface MemoService {
	/**
	 * Insert a memo
	 * @param memo a {@link Memo} object.
	 * @throws ValidationException
	 */
	long createMemo(Memo memo) throws ValidationException;

	/**
	 * Update a memo
	 * @param memoId a long
	 * @param memo a {@link Memo} object.
	 * @throws ValidationException
	 */
	void updateMemo(long memoId, Memo memo) throws ValidationException;

	/**
	 * Delete a memo
	 * @param memoId a long
	 * @param userId a long
	 * @throws ValidationException
	 */
	void deleteMemo(long memoId, long userId) throws ValidationException;

	/**
	 * Get a memo by id
	 * @param memoId a long
	 * @param userId a long
	 * @return memo a {@link Memo} object.
	 */
	Memo getMemoById(long memoId, long userId);

	/**
	 * Get all memo of user
	 * @param userId a long
	 * @return a {@link List<Memo>} object.
	 */
	List<Memo> getAllMemo(long userId);
}
