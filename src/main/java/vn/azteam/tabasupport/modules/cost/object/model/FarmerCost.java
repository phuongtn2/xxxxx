package vn.azteam.tabasupport.modules.cost.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.cost.object.model
 * Created 3/8/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class FarmerCost extends BaseModel {

	private long id;
	private long companyId;
	private long cropId;
	private long registrationId;
	private String title;
	private Date actionDate;
	private String type;
	private String actionType;
	private long actionId;
	private float quantity;
	private int price;

	public long getId() {
		return id;
	}

	public FarmerCost setId(long id) {
		this.id = id;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public FarmerCost setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public FarmerCost setTitle(String title) {
		this.title = title;
		return this;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public FarmerCost setActionDate(Date actionDate) {
		this.actionDate = actionDate;
		return this;
	}

	public String getType() {
		return type;
	}

	public FarmerCost setType(String type) {
		this.type = type;
		return this;
	}

	public String getActionType() {
		return actionType;
	}

	public FarmerCost setActionType(String actionType) {
		this.actionType = actionType;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public FarmerCost setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public FarmerCost setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public FarmerCost setPrice(int price) {
		this.price = price;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public FarmerCost setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public FarmerCost setCropId(long cropId) {
		this.cropId = cropId;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "FarmerCost{" +
				"id=" + id +
				", registrationId='" + registrationId + '\'' +
				", companyId='" + companyId + '\'' +
				", cropId='" + cropId + '\'' +
				", type='" + type + '\'' +
				", actionType='" + actionType + '\'' +
				", actionId='" + actionId + '\'' +
				", quantity='" + quantity + '\'' +
				", price='" + price + '\'' +
				'}';
	}
}
