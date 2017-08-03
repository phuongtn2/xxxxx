package vn.azteam.tabasupport.modules.report.object.model.cost;

import org.springframework.beans.BeansException;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.impl.CompanyServiceImpl;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.employee.service.impl.EmployeeServiceImpl;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;
import vn.azteam.tabasupport.modules.farmer.service.impl.FarmerServiceImpl;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.service.MaterialService;
import vn.azteam.tabasupport.modules.material.service.impl.MaterialServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 3/23/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class DataCostReport {
	private long id;
	private long companyId;
	private long cropId;
	private long registrationId;
	private String type;
	private String title;
	private String actionType;
	private long actionId;
	private float quantity;
	private int price;
	private long farmerId;
	private float actualAcreage;
	private long employeeId;

	private String companyName;
	private String cropName;
	private String employeeName;
	private String farmerName;
	private Material material;

	public long getId() {
		return id;
	}

	public DataCostReport setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public DataCostReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public DataCostReport setCropId(long cropId) {
		this.cropId = cropId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public DataCostReport setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public String getType() {
		return type;
	}

	public DataCostReport setType(String type) {
		this.type = type;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public DataCostReport setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getActionType() {
		return actionType;
	}

	public DataCostReport setActionType(String actionType) {
		this.actionType = actionType;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public DataCostReport setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public DataCostReport setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public DataCostReport setPrice(int price) {
		this.price = price;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public DataCostReport setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public DataCostReport setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public DataCostReport setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public String getCompanyName() {
		CompanyService companyService;
		try {
			companyService = ApplicationContextProvider.getApplicationContext().getBean(CompanyServiceImpl.class);
			companyName = companyService.getCompanyById(companyId).getName();
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			e.printStackTrace();
		}
		return companyName;
	}

	public DataCostReport setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public String getCropName() {
		CropSessionService cropSessionService;
		try {
			cropSessionService = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			cropName = cropSessionService.getCurrentCropSession().getName();
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			e.printStackTrace();
		}
		return cropName;
	}

	public DataCostReport setCropName(String cropName) {
		this.cropName = cropName;
		return this;
	}

	public String getEmployeeName() {
		EmployeeService employeeService;
		try {
			employeeService = ApplicationContextProvider.getApplicationContext().getBean(EmployeeServiceImpl.class);
			employeeName = employeeService.getEmployeeById(employeeId).getFullName();
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			e.printStackTrace();
		}
		return employeeName;
	}

	public DataCostReport setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
		return this;
	}

	public String getFarmerName() {
		FarmerService farmerService;
		try {
			farmerService = ApplicationContextProvider.getApplicationContext().getBean(FarmerServiceImpl.class);
			farmerName = farmerService.getFarmerById(farmerId).getFullName();
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			e.printStackTrace();
		}
		return farmerName;
	}

	public DataCostReport setFarmerName(String farmerName) {
		this.farmerName = farmerName;
		return this;
	}

	public Material getMaterial() {
		if (actionId == 0) return null;

		MaterialService materialService;
		try {
			materialService = ApplicationContextProvider.getApplicationContext().getBean(MaterialServiceImpl.class);
			material = materialService.getMaterialById(actionId);
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			e.printStackTrace();
		}
		return material;
	}

	public DataCostReport setMaterial(Material material) {
		this.material = material;
		return this;
	}
}
