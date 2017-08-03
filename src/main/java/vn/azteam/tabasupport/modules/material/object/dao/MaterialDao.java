package vn.azteam.tabasupport.modules.material.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.material.object.model.Material;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.object.dao
 * Created 12/16/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface MaterialDao {
    /**
     * Insert Material
     *
     * @param material a {@link Material} object.
     * @return a {@link Material} object.
     */
    void insertMaterial(@Param("material") Material material);

    /**
     * Update Material
     *
     * @param materialId material a {@link Material} object.
     * @param material   a long
     * @return a {@link Material} object.
     */
    void updateMaterial(@Param("materialId") long materialId, @Param("material") Material material);

    /**
     * Find a material by id
     *
     * @param id a long
     * @return a {@link Material} object.
     */
    Material findMaterialById(@Param("materialId") long id);

    /**
     * Find all material
     *
     * @return a {@link List<Material>} object.
     */
    List<Material> findAllMaterial();
}