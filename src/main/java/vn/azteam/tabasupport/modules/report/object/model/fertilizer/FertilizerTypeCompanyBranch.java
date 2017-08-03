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
public class FertilizerTypeCompanyBranch {

	List<FertilizerReport> fertilizerReports = new ArrayList<>();
	private long districtId;
	private String districtName;
	private long provinceId;
	private String provinceName;
	private float acreage;
	private float density;

	public long getDistrictId() {
		return districtId;
	}

	public FertilizerTypeCompanyBranch setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public String getDistrictName() {
		return districtName;
	}

	public FertilizerTypeCompanyBranch setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public FertilizerTypeCompanyBranch setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public FertilizerTypeCompanyBranch setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		return this;
	}

	public float getAcreage() {
		return acreage;
	}

	public FertilizerTypeCompanyBranch setAcreage(float acreage) {
		this.acreage = acreage;
		return this;
	}

	public float getDensity() {
		return density;
	}

	public FertilizerTypeCompanyBranch setDensity(float density) {
		this.density = density;
		return this;
	}

	public List<FertilizerReport> getFertilizerReports() {
		return fertilizerReports;
	}

	public FertilizerTypeCompanyBranch setFertilizerReports(List<FertilizerReport> fertilizerReports) {
		this.fertilizerReports = fertilizerReports;
		return this;
	}
}
