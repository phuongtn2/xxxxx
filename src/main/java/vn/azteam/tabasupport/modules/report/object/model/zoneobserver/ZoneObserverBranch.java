package vn.azteam.tabasupport.modules.report.object.model.zoneobserver;

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
public class ZoneObserverBranch {
	private long companyId;
	private String companyName;
	private int farmerRegistQuantity;
	private int farmerActualQuantity;
	private float actualAcreage;
	private float registAcreage;

	public long getCompanyId() {
		return companyId;
	}

	public ZoneObserverBranch setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public ZoneObserverBranch setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public int getFarmerRegistQuantity() {
		return farmerRegistQuantity;
	}

	public ZoneObserverBranch setFarmerRegistQuantity(int farmerRegistQuantity) {
		this.farmerRegistQuantity = farmerRegistQuantity;
		return this;
	}

	public int getFarmerActualQuantity() {
		return farmerActualQuantity;
	}

	public ZoneObserverBranch setFarmerActualQuantity(int farmerActualQuantity) {
		this.farmerActualQuantity = farmerActualQuantity;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public ZoneObserverBranch setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public float getRegistAcreage() {
		return registAcreage;
	}

	public ZoneObserverBranch setRegistAcreage(float registAcreage) {
		this.registAcreage = registAcreage;
		return this;
	}
}
