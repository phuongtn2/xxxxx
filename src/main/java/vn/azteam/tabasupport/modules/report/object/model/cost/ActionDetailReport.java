package vn.azteam.tabasupport.modules.report.object.model.cost;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.cost
 * Created 4/11/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class ActionDetailReport {
	private long actionId;
	private float quantity;
	private int price;
	private long employeeId;
	private long farmerId;
	private float acreage;
	private String employeeName;
	private String farmerName;

	public long getActionId() {
		return actionId;
	}

	public ActionDetailReport setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public ActionDetailReport setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public ActionDetailReport setPrice(int price) {
		this.price = price;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public ActionDetailReport setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public ActionDetailReport setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}

	public float getAcreage() {
		return acreage;
	}

	public ActionDetailReport setAcreage(float acreage) {
		this.acreage = acreage;
		return this;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public ActionDetailReport setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
		return this;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public ActionDetailReport setFarmerName(String farmerName) {
		this.farmerName = farmerName;
		return this;
	}
}
