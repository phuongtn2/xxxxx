package vn.azteam.tabasupport.modules.report.object.model.cost;

import java.util.ArrayList;
import java.util.List;

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
public class ActionReport {
	List<ActionDetailReport> actionDetailReports = new ArrayList<>();
	private long companyId;
	private String companyName;
	private long cropId;
	private String cropName;
	private String type;
	private String title;
	private String actionType;

	public long getCompanyId() {
		return companyId;
	}

	public ActionReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public ActionReport setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public ActionReport setCropId(long cropId) {
		this.cropId = cropId;
		return this;
	}

	public String getCropName() {
		return cropName;
	}

	public ActionReport setCropName(String cropName) {
		this.cropName = cropName;
		return this;
	}

	public String getType() {
		return type;
	}

	public ActionReport setType(String type) {
		this.type = type;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ActionReport setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getActionType() {
		return actionType;
	}

	public ActionReport setActionType(String actionType) {
		this.actionType = actionType;
		return this;
	}

	public List<ActionDetailReport> getActionDetailReports() {
		return actionDetailReports;
	}

	public ActionReport setActionDetailReports(List<ActionDetailReport> actionDetailReports) {
		this.actionDetailReports = actionDetailReports;
		return this;
	}
}
