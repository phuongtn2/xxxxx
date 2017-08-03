package vn.azteam.tabasupport.modules.sale.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.sale.object.dao.SaleRegistrationDao;
import vn.azteam.tabasupport.modules.sale.object.model.SaleRegistration;
import vn.azteam.tabasupport.modules.sale.service.SaleRegistrationService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.sale.service
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see SaleRegistrationService
 * @since 1.0.0
 */
@Service
public class SaleRegistrationServiceImpl extends BaseServiceImpl implements SaleRegistrationService {

	@Autowired
	private SaleRegistrationDao saleRegistrationDao;

	@Override
	public List<SaleRegistration> getAll(long companyId) {
		return saleRegistrationDao.findAll(companyId);
	}

	@Override
	public List<SaleRegistration> getAllSaleByRegistrationId(long companyId, long registrationId) {
		return getAll(companyId).stream().filter(
				saleRegistration -> saleRegistration.getRegistrationId() == registrationId
		).collect(Collectors.toList());
	}

	@Override
	public List<SaleRegistration> syncGetSaleRegistration(long companyId, long proposerId) {
		return getAll(companyId).stream().filter(
				saleRegistration -> saleRegistration.getProposerId() == proposerId
		).collect(Collectors.toList());
	}

	@Override
	public SaleRegistration syncPostSaleRegistration(long companyId, SaleRegistration saleRegistration) throws ValidationException {
		List<ObjectError> errors = saleRegistration.getErrors();
		if (errors.isEmpty()) {
			if (saleRegistration.getId() < 1) {
				saleRegistrationDao.insertSaleRegistration(saleRegistration);
			} else {
				//check existed saleRegistrationId
				getSaleRegistrationById(companyId, saleRegistration.getProposerId(), saleRegistration.getId());
				saleRegistrationDao.updateSaleRegistration(saleRegistration);
			}
			return saleRegistration;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public SaleRegistration getSaleRegistrationById(long companyId, long proposerId, long saleRegistrationId) throws NoSuchElementException {
		return syncGetSaleRegistration(companyId, proposerId).stream().filter(
				saleRegistration -> saleRegistration.getId() == saleRegistrationId
		).findFirst().get();
	}
}
