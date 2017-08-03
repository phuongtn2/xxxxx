package vn.azteam.tabasupport.modules.material.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.material.object.dao.MaterialDao;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.service.MaterialService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.material.service.impl
 * Created 12/16/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    protected MaterialDao materialDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertMaterial(Material material) throws ValidationException{
        final List<ObjectError> errors = material.getErrors();
        if (errors.isEmpty()){
            materialDao.insertMaterial(material);
        } else {
            throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMaterial(long materialId, Material material) throws ValidationException{
        final List<ObjectError> errors = material.getErrors();
        if (errors.isEmpty()){
            materialDao.updateMaterial(materialId, material);
        } else {
            throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Material getMaterialById(long materialId) throws NoSuchElementException {
	    final Material material = getAllMaterials().stream().filter(
			    material1 -> material1.getId() == materialId
	    ).findFirst().get();
	    return material;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Material> getAllMaterials() {
        return materialDao.findAllMaterial();
    }
}