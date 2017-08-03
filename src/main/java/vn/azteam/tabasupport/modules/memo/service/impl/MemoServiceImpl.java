package vn.azteam.tabasupport.modules.memo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.memo.object.dao.MemoDao;
import vn.azteam.tabasupport.modules.memo.object.model.Memo;
import vn.azteam.tabasupport.modules.memo.service.MemoService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.memo.service.impl
 * Created 12/22/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Service
public class MemoServiceImpl extends BaseServiceImpl implements MemoService {

	@Autowired
	protected MemoDao memoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createMemo(Memo memo) throws ValidationException {
		final List<ObjectError> errors = memo.getErrors();
		if (errors.isEmpty()){
			memoDao.insertMemo(memo);
			return memo.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateMemo(long memoId, Memo memo) throws ValidationException {
		final List<ObjectError> errors = memo.getErrors();
		if (errors.isEmpty()){
			memoDao.updateMemo(memo);
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public void deleteMemo(long memoId, long userId) throws ValidationException {
		memoDao.deleteMemo(memoId, userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Memo getMemoById(long memoId, long userId) {
		Memo memo = null;
		try {
			memo = getAllMemo(userId).stream().filter(
					memo1 -> memo1.getId() == memoId
			).findFirst().get();
		} catch (Exception e) {
			logger.error(e);
		}
		return memo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Memo> getAllMemo(long userId) {
		return memoDao.findAll(userId);
	}
}
