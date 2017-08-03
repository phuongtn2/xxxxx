package vn.azteam.tabasupport.modules.memo.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.memo.object.model.Memo;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.memo.object.dao
 * Created 12/22/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface MemoDao {

	/**
	 * Insert a memo
	 * @param memo a {@link Memo} object.
	 */
	long insertMemo(@Param("memo") Memo memo);

	/**
	 * Update a memo
	 * @param memo a {@link Memo} object.
	 */
	void updateMemo(@Param("memo") Memo memo);

	/**
	 * delete a memo
	 * @param memoId a long
	 * @param userId a long
	 */
	void deleteMemo(@Param("memoId")long memoId, @Param("userId")long userId);

	/**
	 * Get all memo of user
	 * @param userId a long
	 * @return memo a {@link List<Memo>} object.
	 */
	List<Memo> findAll(@Param("userId") long userId);
}
