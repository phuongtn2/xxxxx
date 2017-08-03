package vn.azteam.tabasupport.modules.report.object.model.fertilizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.fertilizer
 * Created 4/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class FertilizerTypeReport {
	List<FertilizerTypeCompanyBranch> fertilizerTypeCompanyBranches = new ArrayList<>();
	private long companyId;
	private String companyName;

	public long getCompanyId() {
		return companyId;
	}

	public FertilizerTypeReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public FertilizerTypeReport setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public List<FertilizerTypeCompanyBranch> getFertilizerTypeCompanyBranches() {
		return fertilizerTypeCompanyBranches;
	}

	public FertilizerTypeReport setFertilizerTypeCompanyBranches(List<FertilizerTypeCompanyBranch> fertilizerTypeCompanyBranches) {
		this.fertilizerTypeCompanyBranches = fertilizerTypeCompanyBranches;
		return this;
	}
}
