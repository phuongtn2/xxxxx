package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.PesticideSpraying;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 1/18/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface PesticideSprayingDao {

	long insertPesticideSpraying(@Param("pesticideSpraying") PesticideSpraying pesticideSpraying);

	/**
	 * Update PesticideSpraying
	 *
	 * @param pesticideSpraying PesticideSpraying
	 */
	void update(@Param("pesticideSpraying") PesticideSpraying pesticideSpraying);

	/**
	 * Find all  PesticideSpraying by detailId
	 *
	 * @param detailId long
	 * @return a {@link List}
	 */
	List<PesticideSpraying> findAllPesticideSprayingByDetailId(@Param("detailId") long detailId);
}
