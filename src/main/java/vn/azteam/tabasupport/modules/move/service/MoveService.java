package vn.azteam.tabasupport.modules.move.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.move.object.model.Move;
import vn.azteam.tabasupport.modules.report.object.model.MoveReport;

import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.move.service
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface MoveService {

	List<Move> getAllMoveByEmployeeId(long employeeId);

	Move getMoveById(long employeeId, long moveId);

	List<Move> syncGetAllMoveByEmployeeId(long employeeId);

	Move syncPostMove(Move move) throws ValidationException;

	/**
	 * get all move for function reports
	 *
	 * @param companyId  a long
	 * @param employeeId a long
	 * @return a {@link List<Move>} objects
	 */
	List<MoveReport> getAllMoveByCompanyId(long companyId, long employeeId, Date startDateReport, Date endDateReport);
}
