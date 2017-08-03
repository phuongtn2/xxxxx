package vn.azteam.tabasupport.modules.sale.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.sale.object.model.SaleRegistration;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.sale.service
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface SaleRegistrationService {

	List<SaleRegistration> getAll(long companyId);

	List<SaleRegistration> getAllSaleByRegistrationId(long companyId, long registrationId);

	List<SaleRegistration> syncGetSaleRegistration(long companyId, long proposerId);

	SaleRegistration syncPostSaleRegistration(long companyId, SaleRegistration saleRegistration) throws ValidationException;

	SaleRegistration getSaleRegistrationById(long companyId, long proposerId, long saleRegistrationId) throws NoSuchElementException;
}
