package vn.azteam.tabasupport.modules.material.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterial;
import vn.azteam.tabasupport.modules.material.object.model.RequestMaterialDetail;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.object.dao
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface RequestMaterialDao {

	long insertRequestMaterial(@Param("requestMaterial") RequestMaterial requestMaterial);

	long insertRequestMaterialDetail(@Param("requestMaterialDetail") RequestMaterialDetail requestMaterialDetail);

	void updateRequestMaterial(@Param("requestMaterial") RequestMaterial requestMaterial);

	void updateRequestMaterialDetail(@Param("requestMaterialDetail") RequestMaterialDetail requestMaterialDetail);

	List<RequestMaterial> findAllRequestMaterialByCompanyId(@Param("companyId") long companyId);

	List<RequestMaterialDetail> findAllRequestMaterialDetailByRequestId(@Param("requestId") long requestId);
}
