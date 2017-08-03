package vn.azteam.tabasupport.modules.move.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;
import vn.azteam.tabasupport.modules.move.object.model.Move;
import vn.azteam.tabasupport.modules.move.rest.MoveApi;
import vn.azteam.tabasupport.modules.move.service.MoveService;
import vn.azteam.tabasupport.modules.zone.object.model.CompanyZone;
import vn.azteam.tabasupport.modules.zone.service.CompanyZoneService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.move.rest.impl
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class MoveApiImpl extends BaseRestImpl implements MoveApi {

	@Autowired
	private MoveService moveService;
	@Autowired
	private CropSessionService cropSessionService;
	@Autowired
	private CompanyZoneService companyZoneService;

	@Override
	public Object syncGetMoves(OAuth2Authentication auth) {
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		return moveService.syncGetAllMoveByEmployeeId(employee.getId());
	}

	@Override
	public Object syncPostMoves(OAuth2Authentication auth, @RequestBody List<Move> moves) throws ValidationException {
		logger.info(moves);
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		List<Zone> employeeZones = employee.getZones();
		CompanyZone companyZone = null;
		if (employeeZones != null && employeeZones.size() > 0) {
			companyZone = companyZoneService.getCompanyZoneById(employee.getCompanyId(), employeeZones.get(0).getCultivationZoneId());
		}

		for (Move move : moves) {
			move.setCompanyId(employee.getCompanyId());
			move.setEmployeeId(employee.getId());
			move.setCropId(cropSessionService.getCurrentCropSessionId());
			if (companyZone != null) {
				move.setProvinceId(companyZone.getProvinceId())
						.setDistrictId(companyZone.getDistrictId())
						.setWardId(companyZone.getWardId());
			}
			moveService.syncPostMove(move);
		}
		return moves;
	}
}
