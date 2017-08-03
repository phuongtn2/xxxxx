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
public class SeedTypeCompanyBranch {

	List<SeedReport> seedReports = new ArrayList<>();
	private long districtId;
	private String districtName;
	private long provinceId;
	private String provinceName;

	public long getDistrictId() {
		return districtId;
	}

	public SeedTypeCompanyBranch setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public String getDistrictName() {
		return districtName;
	}

	public SeedTypeCompanyBranch setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public SeedTypeCompanyBranch setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public SeedTypeCompanyBranch setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		return this;
	}

	public List<SeedReport> getSeedReports() {
		return seedReports;
	}

	public SeedTypeCompanyBranch setSeedReports(List<SeedReport> seedReports) {
		this.seedReports = seedReports;
		return this;
	}
}
