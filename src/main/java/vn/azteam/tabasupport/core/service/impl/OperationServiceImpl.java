package vn.azteam.tabasupport.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.OperationDao;
import vn.azteam.tabasupport.core.object.model.Operation;
import vn.azteam.tabasupport.core.service.OperationService;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Service
public class OperationServiceImpl implements OperationService {
	@Autowired
	OperationDao operationDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Operation> getAll() throws NullPointerException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Operation> getOperationByRoleIds(long[] roleIds) throws NullPointerException {
		return operationDao.findOperationByRoleIds(roleIds);
	}
}
