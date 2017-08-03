package vn.azteam.tabasupport.modules.material.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.material.object.model
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class RequestMaterialDetail extends BaseModel {

	private long id;
	private long requestMaterialId;
	private long materialId;
	private int requestQuantity;
	private long approverLv1Id;
	private int approveLv1Quantity;
	private Date approvedLv1Date;
	private long approverLv2Id;
	private int approveLv2Quantity;
	private Date approvedLv2Date;

	public long getId() {
		return id;
	}

	public RequestMaterialDetail setId(long id) {
		this.id = id;
		return this;
	}

	public long getRequestMaterialId() {
		return requestMaterialId;
	}

	public RequestMaterialDetail setRequestMaterialId(long requestMaterialId) {
		this.requestMaterialId = requestMaterialId;
		return this;
	}

	public long getMaterialId() {
		return materialId;
	}

	public RequestMaterialDetail setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public int getRequestQuantity() {
		return requestQuantity;
	}

	public RequestMaterialDetail setRequestQuantity(int requestQuantity) {
		this.requestQuantity = requestQuantity;
		return this;
	}

	public long getApproverLv1Id() {
		return approverLv1Id;
	}

	public RequestMaterialDetail setApproverLv1Id(long approverLv1Id) {
		this.approverLv1Id = approverLv1Id;
		return this;
	}

	public int getApproveLv1Quantity() {
		return approveLv1Quantity;
	}

	public RequestMaterialDetail setApproveLv1Quantity(int approveLv1Quantity) {
		this.approveLv1Quantity = approveLv1Quantity;
		return this;
	}

	public Date getApprovedLv1Date() {
		return approvedLv1Date;
	}

	public RequestMaterialDetail setApprovedLv1Date(Date approvedLv1Date) {
		this.approvedLv1Date = approvedLv1Date;
		return this;
	}

	public long getApproverLv2Id() {
		return approverLv2Id;
	}

	public RequestMaterialDetail setApproverLv2Id(long approverLv2Id) {
		this.approverLv2Id = approverLv2Id;
		return this;
	}

	public int getApproveLv2Quantity() {
		return approveLv2Quantity;
	}

	public RequestMaterialDetail setApproveLv2Quantity(int approveLv2Quantity) {
		this.approveLv2Quantity = approveLv2Quantity;
		return this;
	}

	public Date getApprovedLv2Date() {
		return approvedLv2Date;
	}

	public RequestMaterialDetail setApprovedLv2Date(Date approvedLv2Date) {
		this.approvedLv2Date = approvedLv2Date;
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
		return "RequestMaterialDetail{" +
				"id=" + id +
				", requestMaterialId='" + requestMaterialId + '\'' +
				", materialId='" + materialId + '\'' +
				", requestQuantity='" + requestQuantity + '\'' +
				", approverLv1Id='" + approverLv1Id + '\'' +
				", approveLv1Quantity='" + approveLv1Quantity + '\'' +
				", approvedLv1Date='" + approvedLv1Date + '\'' +
				", approverLv2Id='" + approverLv2Id + '\'' +
				", approveLv2Quantity='" + approveLv2Quantity + '\'' +
				", approvedLv2Date='" + approvedLv2Date + '\'' +
				'}';
	}
}
