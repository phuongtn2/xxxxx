package vn.azteam.tabasupport.modules.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.report.object.dao.ExportDao;
import vn.azteam.tabasupport.modules.report.object.model.zoneobserver.ZoneObserver;
import vn.azteam.tabasupport.modules.report.object.model.zoneobserver.ZoneObserverBranch;
import vn.azteam.tabasupport.modules.report.object.model.zoneobserver.ZoneObserverReport;
import vn.azteam.tabasupport.modules.report.service.ZoneObserverService;

import java.util.ArrayList;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service.impl
 * Created 3/14/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class ZoneObserverServiceImpl extends BaseServiceImpl implements ZoneObserverService {

	@Autowired
	private ExportDao exportDao;

	@Override
	public List<ZoneObserverReport> getAllZoneObserverReport() {
		List<ZoneObserver> zoneObservers = exportDao.findAllZoneObserverReport();

		List<ZoneObserverReport> zoneObserverReports = new ArrayList<>();

		ZoneObserverReport currentZoneObserverReport = new ZoneObserverReport();
		ZoneObserverBranch currentZoneObserverBranch = new ZoneObserverBranch();
		long currentObserverId = 0;
		long currentZoneId = 0;
		long currentCompanyId = 0;
		float currentRegistAcreage = 0;
		float currentActualAcreage = 0;

		for (ZoneObserver zoneObserver : zoneObservers) {
			//khac vung trong
			if (currentZoneId != zoneObserver.getZoneId()) {
				//tao vung
				ZoneObserverReport zoneObserverReport = new ZoneObserverReport();
				zoneObserverReport.setZoneId(zoneObserver.getZoneId())
						.setEmployeeId(zoneObserver.getEmployeeId())
						.setObserverName(zoneObserver.getObserverName());
				currentZoneObserverReport = zoneObserverReport;

				// tao cong ty
				currentActualAcreage = zoneObserver.getActualAcreage();
				currentRegistAcreage = zoneObserver.getRegistAcreage();
				ZoneObserverBranch zoneObserverBranch = new ZoneObserverBranch();
				zoneObserverBranch.setCompanyId(zoneObserver.getCompanyId())
						.setCompanyName(zoneObserver.getCompanyName())
						.setFarmerRegistQuantity(zoneObserver.getFarmerRegistQuantity())
						.setFarmerActualQuantity(zoneObserver.getFarmerActualQuantity())
						.setActualAcreage(currentActualAcreage)
						.setRegistAcreage(currentRegistAcreage);
				currentZoneObserverBranch = zoneObserverBranch;

				// gan tcong ty vao vung
				currentZoneObserverReport.getZoneObserverBranches().add(currentZoneObserverBranch);
				zoneObserverReports.add(currentZoneObserverReport);
				// tao dien tich

				//update vung, giam sat
				currentObserverId = zoneObserver.getEmployeeId();
				currentZoneId = zoneObserver.getZoneId();
			} else { // cung vung trong
				//khac giam sat
				if (currentObserverId != zoneObserver.getEmployeeId()) {
					//tao vung
					ZoneObserverReport zoneObserverReport = new ZoneObserverReport();
					zoneObserverReport.setZoneId(zoneObserver.getZoneId())
							.setEmployeeId(zoneObserver.getEmployeeId())
							.setObserverName(zoneObserver.getObserverName());
					currentZoneObserverReport = zoneObserverReport;

					// tao cong ty
					currentActualAcreage = zoneObserver.getActualAcreage();
					currentRegistAcreage = zoneObserver.getRegistAcreage();
					ZoneObserverBranch zoneObserverBranch = new ZoneObserverBranch();
					zoneObserverBranch.setCompanyId(zoneObserver.getCompanyId())
							.setCompanyName(zoneObserver.getCompanyName())
							.setFarmerRegistQuantity(zoneObserver.getFarmerRegistQuantity())
							.setFarmerActualQuantity(zoneObserver.getFarmerActualQuantity())
							.setActualAcreage(currentActualAcreage)
							.setRegistAcreage(currentRegistAcreage);
					currentZoneObserverBranch = zoneObserverBranch;

					// gan tcong ty vao vung
					currentZoneObserverReport.getZoneObserverBranches().add(currentZoneObserverBranch);
					zoneObserverReports.add(currentZoneObserverReport);
					// tao dien tich

					//update vung, giam sat
					currentObserverId = zoneObserver.getEmployeeId();
					currentZoneId = zoneObserver.getZoneId();
				} else { // cung giam sat
					//khac cong ty
					if (currentCompanyId != zoneObserver.getCompanyId()) {
						// tao cong ty
						currentActualAcreage = zoneObserver.getActualAcreage();
						currentRegistAcreage = zoneObserver.getRegistAcreage();
						ZoneObserverBranch zoneObserverBranch = new ZoneObserverBranch();
						zoneObserverBranch.setCompanyId(zoneObserver.getCompanyId())
								.setCompanyName(zoneObserver.getCompanyName())
								.setFarmerRegistQuantity(zoneObserver.getFarmerRegistQuantity())
								.setFarmerActualQuantity(zoneObserver.getFarmerActualQuantity())
								.setActualAcreage(currentActualAcreage)
								.setRegistAcreage(currentRegistAcreage);
						currentZoneObserverBranch = zoneObserverBranch;
						// gan tcong ty vao vung
						currentZoneObserverReport.getZoneObserverBranches().add(currentZoneObserverBranch);
					} else { // cung cong ty
						//cong dien tich
						currentActualAcreage += zoneObserver.getActualAcreage();
						currentRegistAcreage += zoneObserver.getRegistAcreage();
					}

				}
			}
			currentCompanyId = zoneObserver.getCompanyId();
		}


		return zoneObserverReports;
	}
}