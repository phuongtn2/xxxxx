package vn.azteam.tabasupport.modules.material.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.material.object.model.Material;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.material.service.impl
 * Created 12/16/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.material.service.impl.MaterialServiceImpl
 * @since 1.0.0
 */
public interface MaterialService {
    /**
     * Insert material
     * @param material a {@link Material} object.
     * @throws ValidationException
     */
    void insertMaterial(Material material) throws ValidationException;

    /**
     * Update material
     * @param materialId a long
     * @param material a {@link Material} object.
     * @throws ValidationException
     */
    void updateMaterial(long materialId, Material material) throws ValidationException;

    /**
     * Get a material by id
     * @param materialId a long
     * @return a {@link Material} object.
     */
    Material getMaterialById(long materialId) throws NoSuchElementException;

    /**
     * Get all material
     * @return a {@link List<Material>} object.
     */
    List<Material> getAllMaterials();
}