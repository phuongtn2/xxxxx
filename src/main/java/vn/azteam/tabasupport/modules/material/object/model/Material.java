package vn.azteam.tabasupport.modules.material.object.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * Package vn.azteam.tabasupport.modules.material.object.model
 * Created 12/16/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@JsonIgnoreProperties("isActive")
public class Material extends BaseModel {

	private long id;
	private String sku;
	private String name;
	private long isActive;
	private String unit;
	private String volumeWeight;
	private String type;
	private String subType;

	public long getId() {
		return id;
	}

	public Material setId(long id) {
		this.id = id;
		return this;
	}

	public String getSku() {
		return sku;
	}

	public Material setSku(String sku) {
		this.sku = sku;
		return this;
	}

	public String getName() {
		return name;
	}

	public Material setName(String name) {
		this.name = name;
		return this;
	}

	public long getIsActive() {
		return isActive;
	}

	public Material setIsActive(long isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public Material setUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public String getVolumeWeight() {
		return volumeWeight;
	}

	public Material setVolumeWeight(String volumeWeight) {
		this.volumeWeight = volumeWeight;
		return this;
	}

	public String getType() {
		return type;
	}

	public Material setType(String type) {
		this.type = type;
		return this;
	}

	public String getSubType() {
		return subType;
	}

	public Material setSubType(String subType) {
		this.subType = subType;
		return this;
	}


	@Override
	public boolean supports(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Material material = (Material) o;
	}


	@Override
	public String toString() {
		return "Material{" +
				"id=" + id +
				", sku='" + sku + '\'' +
				", materialName='" + name + '\'' +
				", type='" + type + '\'' +
				", subType='" + subType + '\'' +
				", volumeWeight='" + volumeWeight + '\'' +
				", unit='" + unit + '\'' +
				", created=" + created +
				", updated=" + updated +
				", createId=" + createId +
				", updateId=" + updateId +
				'}';
	}
}