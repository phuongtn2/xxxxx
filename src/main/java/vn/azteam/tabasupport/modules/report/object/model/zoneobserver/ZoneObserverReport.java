package vn.azteam.tabasupport.modules.report.object.model.zoneobserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.zoneobserver
 * Created 3/23/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class ZoneObserverReport {
	private long zoneId;
	private long employeeId;
	private String observerName;
	private List<ZoneObserverBranch> zoneObserverBranches = new ArrayList<>();

	public long getZoneId() {
		return zoneId;
	}

	public ZoneObserverReport setZoneId(long zoneId) {
		this.zoneId = zoneId;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public ZoneObserverReport setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public String getObserverName() {
		return observerName;
	}

	public ZoneObserverReport setObserverName(String observerName) {
		this.observerName = observerName;
		return this;
	}

	public List<ZoneObserverBranch> getZoneObserverBranches() {
		return zoneObserverBranches;
	}

	public ZoneObserverReport setZoneObserverBranches(List<ZoneObserverBranch> zoneObserverBranches) {
		this.zoneObserverBranches = zoneObserverBranches;
		return this;
	}
}
