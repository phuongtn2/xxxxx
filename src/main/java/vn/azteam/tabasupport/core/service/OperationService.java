package vn.azteam.tabasupport.core.service;

import vn.azteam.tabasupport.core.object.model.Operation;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.core.service.impl.OperationServiceImpl
 * @since 1.0.0
 */
public interface OperationService {
	/**
	 * Get all Operations
	 *
	 * @return a {@link List} Operation Object
	 * @throws NullPointerException
	 */
	List<Operation> getAll() throws NullPointerException;

	/**
	 * Get Operations by roleIds
	 *
	 * @return a {@link List} Operation Object
	 * @throws NullPointerException
	 */
	List<Operation> getOperationByRoleIds(long[] roleIds) throws NullPointerException;
}
