package vn.azteam.tabasupport.modules.report.object.model.zoneobserver;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 3/14/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class ZoneObserver {
	private long zoneId;
	private long employeeId;
	private long companyId;
	private String observerName;
	private String companyName;
	private int farmerRegistQuantity;
	private int farmerCancelQuantity;
	private int farmerActualQuantity;
	private float actualAcreage;
	private float registAcreage;

	public long getZoneId() {
		return zoneId;
	}

	public ZoneObserver setZoneId(long zoneId) {
		this.zoneId = zoneId;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public ZoneObserver setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public ZoneObserver setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getObserverName() {
		return observerName;
	}

	public ZoneObserver setObserverName(String observerName) {
		this.observerName = observerName;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public ZoneObserver setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public int getFarmerRegistQuantity() {
		return farmerRegistQuantity;
	}

	public ZoneObserver setFarmerRegistQuantity(int farmerRegistQuantity) {
		this.farmerRegistQuantity = farmerRegistQuantity;
		return this;
	}

	public int getFarmerCancelQuantity() {
		return farmerCancelQuantity;
	}

	public ZoneObserver setFarmerCancelQuantity(int farmerCancelQuantity) {
		this.farmerCancelQuantity = farmerCancelQuantity;
		return this;
	}

	public int getFarmerActualQuantity() {
		return farmerRegistQuantity - farmerCancelQuantity;
	}

	public ZoneObserver setFarmerActualQuantity(int farmerActualQuantity) {
		this.farmerActualQuantity = farmerActualQuantity;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public ZoneObserver setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public float getRegistAcreage() {
		return registAcreage;
	}

	public ZoneObserver setRegistAcreage(float registAcreage) {
		this.registAcreage = registAcreage;
		return this;
	}
}
