package vn.azteam.tabasupport.modules.material.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.material.object.dao.CompanyMaterialDao;
import vn.azteam.tabasupport.modules.material.object.model.CompanyMaterial;
import vn.azteam.tabasupport.modules.material.service.CompanyMaterialService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.material.service.impl
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class CompanyMaterialServiceImpl extends BaseServiceImpl implements CompanyMaterialService {

	@Autowired
	private CompanyMaterialDao companyMaterialDao;

	@Override
	public long insertCompanyMaterial(CompanyMaterial companyMaterial) throws ValidationException {
		final List<ObjectError> errors = companyMaterial.getErrors();
		if (errors.isEmpty()) {
			companyMaterialDao.insertCompanyMaterial(companyMaterial);
			return companyMaterial.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public void updateCompanyMaterial(CompanyMaterial companyMaterial) throws ValidationException {
		final List<ObjectError> errors = companyMaterial.getErrors();
		if (errors.isEmpty()) {
			companyMaterialDao.updateCompanyMaterial(companyMaterial);
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public void deleteCompanyMaterial(long companyMaterialId) {
		companyMaterialDao.deleteCompanyMaterial(companyMaterialId);
	}

	@Override
	public List<CompanyMaterial> getAllCompanyMaterial(long companyId) {
		return companyMaterialDao.findAllCompanyMaterialById(companyId);
	}

	@Override
	public CompanyMaterial getCompanyMaterialById(long companyId, long companyMaterialId) throws NoSuchElementException {
		final CompanyMaterial companyMaterial = getAllCompanyMaterial(companyId).stream().filter(
				companyMaterial1 -> companyMaterial1.getId() == companyMaterialId
		).findFirst().get();
		return companyMaterial;
	}

	@Override
	public void filterCompanyMaterialByType(List<CompanyMaterial> companyMaterials, String type) {
		companyMaterials.removeIf(
				companyMaterial -> !companyMaterial.getType().equalsIgnoreCase(type)
		);
	}

	@Override
	public void filterCompanyMaterialBySubType(List<CompanyMaterial> companyMaterials, String subType) {
		companyMaterials.removeIf(
				companyMaterial -> !companyMaterial.getSubType().equalsIgnoreCase(subType)
		);
	}

	@Override
	public void filterCompanyMaterialByStatus(List<CompanyMaterial> companyMaterials, String status) {
		companyMaterials.removeIf(
				companyMaterial -> !companyMaterial.getStatus().equalsIgnoreCase(status)
		);
	}

	@Override
	public void filterCompanyMaterialByName(List<CompanyMaterial> companyMaterials, String materialName) {
		companyMaterials.removeIf(
				companyMaterial -> !companyMaterial.getName().toLowerCase().matches(String.format(".*%s.*", materialName).toString())
		);
	}
}
