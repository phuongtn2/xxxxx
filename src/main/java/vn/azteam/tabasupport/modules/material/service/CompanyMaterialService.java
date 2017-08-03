package vn.azteam.tabasupport.modules.material.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.material.object.model.CompanyMaterial;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.material.service
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CompanyMaterialService {

	long insertCompanyMaterial(CompanyMaterial companyMaterial) throws ValidationException;

	void updateCompanyMaterial(CompanyMaterial companyMaterial) throws ValidationException;

	void deleteCompanyMaterial(long companyMaterialId);

	List<CompanyMaterial> getAllCompanyMaterial(long companyId);

	CompanyMaterial getCompanyMaterialById(long companyId, long companyMaterialId) throws NoSuchElementException;

	void filterCompanyMaterialByType(List<CompanyMaterial> companyMaterials, String type);

	void filterCompanyMaterialBySubType(List<CompanyMaterial> companyMaterials, String subType);

	void filterCompanyMaterialByStatus(List<CompanyMaterial> companyMaterials, String status);//INVESTMENT OR SELF_SUPPLY

	void filterCompanyMaterialByName(List<CompanyMaterial> companyMaterials, String materialName);
}
