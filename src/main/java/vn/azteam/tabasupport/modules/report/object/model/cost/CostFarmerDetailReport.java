package vn.azteam.tabasupport.modules.report.object.model.cost;

import org.springframework.beans.BeansException;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.impl.CompanyServiceImpl;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.cost
 * Created 4/10/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class CostFarmerDetailReport {

	private String type;
	private String title;
	private String actionType;
	private long actionId;
	private float quantity;
	private int price;
	private long companyId;
	private long cropId;
	private String companyName;
	private String cropName;

	public String getType() {
		return type;
	}

	public CostFarmerDetailReport setType(String type) {
		this.type = type;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public CostFarmerDetailReport setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getActionType() {
		return actionType;
	}

	public CostFarmerDetailReport setActionType(String actionType) {
		this.actionType = actionType;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public CostFarmerDetailReport setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public CostFarmerDetailReport setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public CostFarmerDetailReport setPrice(int price) {
		this.price = price;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public CostFarmerDetailReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public CostFarmerDetailReport setCropId(long cropId) {
		this.cropId = cropId;
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

	public CostFarmerDetailReport setCompanyName(String companyName) {
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

	public CostFarmerDetailReport setCropName(String cropName) {
		this.cropName = cropName;
		return this;
	}
}
