package vn.azteam.tabasupport.modules.report.object.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 1/10/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class PlantAreaReport {
	private long companyId;
	private String companyName;
	private List<CompanyBranch> companyBranches = new ArrayList<>();

	public String getCompanyName() {
		return companyName;
	}

	public PlantAreaReport setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public PlantAreaReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public List<CompanyBranch> getCompanyBranches() {
		return companyBranches;
	}

	public PlantAreaReport setCompanyBranches(List<CompanyBranch> companyBranches) {
		this.companyBranches = companyBranches;
		return this;
	}
}
