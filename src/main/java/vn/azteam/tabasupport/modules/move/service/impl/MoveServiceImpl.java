package vn.azteam.tabasupport.modules.move.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.move.object.dao.MoveDao;
import vn.azteam.tabasupport.modules.move.object.model.Move;
import vn.azteam.tabasupport.modules.move.service.MoveService;
import vn.azteam.tabasupport.modules.report.object.dao.ExportDao;
import vn.azteam.tabasupport.modules.report.object.model.MoveReport;

import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.move.service.impl
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class MoveServiceImpl extends BaseServiceImpl implements MoveService {

	@Autowired
	private MoveDao moveDao;
	@Autowired
	private ExportDao exportDao;

	@Override
	public List<Move> getAllMoveByEmployeeId(long employeeId) {
		return moveDao.findAllMove(employeeId);
	}

	@Override
	public Move getMoveById(long employeeId, long moveId) {
		return getAllMoveByEmployeeId(employeeId).stream().filter(
				move -> move.getId() == moveId
		).findFirst().get();
	}

	@Override
	public List<Move> syncGetAllMoveByEmployeeId(long employeeId) {
		return getAllMoveByEmployeeId(employeeId);
	}

	@Override
	public Move syncPostMove(Move move) throws ValidationException {
		List<ObjectError> errors = move.getErrors();
		if (!errors.isEmpty()) {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
		if (move.getId() > 0) {
			//check move existed
			getMoveById(move.getEmployeeId(), move.getId());
			moveDao.updateMove(move);
		} else {
			moveDao.insertMove(move);
		}
		return move;
	}

	@Override
	public List<MoveReport> getAllMoveByCompanyId(long companyId, long employeeId, Date startDateReport, Date endDateReport) {
		return exportDao.findAllMoveReport(companyId, employeeId, startDateReport, endDateReport);
	}
}
