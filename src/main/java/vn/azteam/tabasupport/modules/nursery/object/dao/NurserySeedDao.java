package vn.azteam.tabasupport.modules.nursery.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.dao
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NurserySeedDao {
	/**
	 * Insert new Nursery seed.
	 *
	 * @param nursery {@link Nursery} object
	 */
	void insert(@Param("nursery") Nursery nursery);

	/**
	 * Insert exist Nursery seed.
	 *
	 * @param nursery {@link Nursery} object
	 */
	void update(@Param("nursery") Nursery nursery);
}
