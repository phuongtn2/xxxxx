package vn.azteam.tabasupport.core.service;

import vn.azteam.tabasupport.core.object.model.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CompanyService {
	/**
	 * Get all companies
	 *
	 * @return a {@link List} object
	 */
	List<Company> getAll();

	/**
	 * Get company by id
	 *
	 * @param companyId a {@link Long} company id
	 * @return a {@link Company} object
	 */
	Company getCompanyById(long companyId);

	/**
	 * Get child id by parentId
	 *
	 * @param parentId a {@link Long} company id
	 * @return a {@link ArrayList} object
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	List<Long> getChildrenId(long parentId) throws NullPointerException, NoSuchElementException;
}
