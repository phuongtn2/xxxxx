package vn.azteam.tabasupport.modules.employee.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.employee.object.model.Division;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.employee.object.dao
 * created 1/24/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface DivisionDao {
	List<Division> findAllByCompanyId(@Param("companyId") long companyId);

	void update(@Param("division") Division division);
}
