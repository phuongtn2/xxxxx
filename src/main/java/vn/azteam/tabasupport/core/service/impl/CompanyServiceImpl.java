package vn.azteam.tabasupport.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.CompanyDao;
import vn.azteam.tabasupport.core.object.model.Company;
import vn.azteam.tabasupport.core.service.CompanyService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see CompanyService
 * @since 1.0.0
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {
	@Autowired
	private CompanyDao companyDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Company> getAll() {
		return companyDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Company getCompanyById(long companyId) {
		Company company = null;
		try {
			company = getAll().stream().filter(c -> c.getId() == companyId).findFirst().get();
		} catch (NoSuchElementException e) {
			logger.error(e);
		}
		return company;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Long> getChildrenId(long parentId) throws NullPointerException, NoSuchElementException {
		final List<Long> companyIds = new ArrayList<>();
		final List<Company> companies = getAll();

		companies.sort((c1, c2) -> Long.compare(c1.getParentId(), c2.getParentId()));
		companies.forEach(c -> {
			if (c.getParentId() == parentId
					|| companyIds.contains(c.getParentId())) {
				companyIds.add(c.getId());
			}
		});
		return companyIds;
	}
}
