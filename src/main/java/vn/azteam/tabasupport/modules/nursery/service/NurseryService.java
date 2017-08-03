package vn.azteam.tabasupport.modules.nursery.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.service
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface NurseryService {
	/**
	 * Create new Nursery.
	 *
	 * @param nursery Nursery
	 * @return nurseryId
	 * @throws ValidationException
	 */
	long createNursery(Nursery nursery) throws ValidationException;

	/**
	 * Update exist Nursery.
	 *
	 * @param nursery Nursery
	 * @return nurseryId
	 * @throws ValidationException
	 */
	long updateNursery(Nursery nursery) throws ValidationException;

	/**
	 * Get all nursery by registrationIds.
	 *
	 * @param registrationIds long[]
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<Nursery> getAllNurseryByRegistrationIds(long[] registrationIds) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get all nursery by registrationId.
	 *
	 * @param registrationId long
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<Nursery> getAllNurseryByRegistrationId(long registrationId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get nursery by Id.
	 *
	 * @param nurseryId      long
	 * @param registrationId long
	 * @return a {@link Nursery} object.
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	Nursery getNurseryById(long nurseryId, long registrationId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get all nursery by responsibilityEmployeeId.
	 *
	 * @param responsibilityEmployeeId long
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<Nursery> getAllNurseryByResponsibilityEmployeeId(long responsibilityEmployeeId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * server sync data.
	 *
	 * @param nurseries {@link Nursery} object.
	 * @param account   {@link Account} object.
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<Nursery> syncList(List<Nursery> nurseries, Account account) throws NullPointerException, BindingException, ValidationException;
}
