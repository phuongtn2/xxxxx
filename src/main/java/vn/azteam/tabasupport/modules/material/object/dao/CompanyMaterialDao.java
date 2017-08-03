package vn.azteam.tabasupport.modules.material.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.material.object.model.CompanyMaterial;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.object.dao
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CompanyMaterialDao {

	long insertCompanyMaterial(@Param("companyMaterial") CompanyMaterial companyMaterial);

	void updateCompanyMaterial(@Param("companyMaterial") CompanyMaterial companyMaterial);

	void deleteCompanyMaterial(@Param("companyMaterialId") long companyMaterialId);

	List<CompanyMaterial> findAllCompanyMaterialById(@Param("companyId") long companyId);
}
