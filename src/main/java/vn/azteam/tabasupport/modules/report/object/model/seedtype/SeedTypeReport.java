package vn.azteam.tabasupport.modules.report.object.model.seedtype;

import java.util.ArrayList;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.seedtype
 * Created 3/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class SeedTypeReport {

	private long companyId;
	private String companyName;
	private List<SeedTypeCompanyBranch> seedTypeCompanyBranches = new ArrayList<>();

	public long getCompanyId() {
		return companyId;
	}

	public SeedTypeReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public SeedTypeReport setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public List<SeedTypeCompanyBranch> getSeedTypeCompanyBranches() {
		return seedTypeCompanyBranches;
	}

	public SeedTypeReport setSeedTypeCompanyBranches(List<SeedTypeCompanyBranch> seedTypeCompanyBranches) {
		this.seedTypeCompanyBranches = seedTypeCompanyBranches;
		return this;
	}
}
