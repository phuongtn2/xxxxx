package vn.azteam.tabasupport.modules.material.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.modules.material.service.constant.StatusMaterial;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * Package vn.azteam.tabasupport.modules.material.object.model
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class CompanyMaterial extends Material {

	private long id;
	private long companyId;
	private long materialId;
	private String status;
	private int price;
	private String memo;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public CompanyMaterial setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public CompanyMaterial setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getMaterialId() {
		return materialId;
	}

	public CompanyMaterial setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public CompanyMaterial setStatus(String status) {
		this.status = status;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public CompanyMaterial setPrice(int price) {
		this.price = price;
		return this;
	}

	public String getMemo() {
		return memo;
	}

	public CompanyMaterial setMemo(String memo) {
		this.memo = memo;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		super.validate(target, errors);
		final CompanyMaterial companyMaterial = (CompanyMaterial) target;
		try {
			StatusMaterial.valueOf(companyMaterial.getStatus().toUpperCase());
		} catch (IllegalArgumentException e) {
			errors.rejectValue("status",
					PropertiesParserUtil.parsePropertyByFormat("validation.request.default.code"),
					PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

	}
}
