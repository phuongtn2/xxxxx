package vn.azteam.tabasupport.modules.sale.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.sale.object.model.SaleRegistration;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cropsession.object.dao
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface SaleRegistrationDao {

	List<SaleRegistration> findAll(@Param("companyId") long companyId);

	long insertSaleRegistration(@Param("saleRegistration") SaleRegistration saleRegistration);

	void updateSaleRegistration(@Param("saleRegistration") SaleRegistration saleRegistration);
}
