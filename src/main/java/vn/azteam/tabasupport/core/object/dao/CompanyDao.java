package vn.azteam.tabasupport.core.object.dao;

import vn.azteam.tabasupport.core.object.model.Company;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CompanyDao {
	/**
	 * Get all companies
	 *
	 * @return a {@link List} company
	 */
	List<Company> findAll();
}
