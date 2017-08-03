package vn.azteam.tabasupport.modules.sale.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.cropsession.object.model
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */

public class SaleRegistration extends BaseModel {

	private long id;
	private int saleIndex;
	private long registrationId;
	private long proposerId;
	private float expectedQuantity;
	private int status;
	private Date dateRegistration;

	public long getId() {
		return id;
	}

	public SaleRegistration setId(long id) {
		this.id = id;
		return this;
	}

	public int getSaleIndex() {
		return saleIndex;
	}

	public SaleRegistration setSaleIndex(int saleIndex) {
		this.saleIndex = saleIndex;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public SaleRegistration setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getProposerId() {
		return proposerId;
	}

	public SaleRegistration setProposerId(long proposerId) {
		this.proposerId = proposerId;
		return this;
	}

	public float getExpectedQuantity() {
		return expectedQuantity;
	}

	public SaleRegistration setExpectedQuantity(float expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public SaleRegistration setStatus(int status) {
		this.status = status;
		return this;
	}

	public Date getDateRegistration() {
		return dateRegistration;
	}

	public SaleRegistration setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
		return this;
	}

	@Override
	public String toString() {
		return "CropRegistration{" +
				"id=" + id +
				", saleIndex=" + saleIndex +
				", registrationId=" + registrationId +
				", proposerId=" + proposerId +
				", expectedQuantity=" + expectedQuantity +
				", status=" + status +
				'}';
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SaleRegistration saleRegistration = (SaleRegistration) target;
		if (saleRegistration.getSaleIndex() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "sale_registration", "sale_index"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "sale_index"));
		}
		if (saleRegistration.getExpectedQuantity() <= 0) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "sale_registration", "expected_quantity"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "expected_quantity"));
		}
	}
}
