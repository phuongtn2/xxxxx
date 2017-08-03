package vn.azteam.tabasupport.modules.cultivation.object.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cropsession.enums.CropPhase;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMaterialSupplement;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMedia;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationDetailService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationDetailServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.model
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @see Serializable
 * @since 1.0.0
 */
public class CultivationDetail extends BaseModel {
	private long id;
	private long registrationId;
	private long cultivationId;
	private long fieldPlotId;
	private String action;
	private String actionName;
	private long actionIndex;
	private Date actionUpdateDate;
	private Date actionDate;
	private float latitude;
	private float longitude;
	private long responsibilityEmployeeId;
	private String target;
	private String resultTarget;

	private List<CropMedia> medias;

	private List<Object> contents;

	private List<CropMaterialSupplement> materialSupplements;

	public List<CropMaterialSupplement> getMaterialSupplements() {
		if (materialSupplements != null) return materialSupplements;

		try {
			final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			materialSupplements = service.getAllCropMaterialSupplementByActionId(id,
					CropMaterialSupplement.PHASE.CULTIVATION.getPhase());
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}

		return materialSupplements;
	}

	public CultivationDetail setMaterialSupplements(List<CropMaterialSupplement> materialSupplements) {
		this.materialSupplements = materialSupplements;
		return this;
	}

	public List<CropMedia> getMedias() {
		if (medias != null) return medias;
		try {
			final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			medias = service.getMedia(id, CropPhase.CULTIVATION.toString(), 0);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return medias;
	}

	public CultivationDetail setMedias(List<CropMedia> medias) {
		this.medias = medias;
		return this;
	}

	public List<Object> getContents() {
		if (contents != null) return contents;
		try {
			final CultivationDetailService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationDetailServiceImpl.class);
			switch (CultivationDetail.ACTION.findAction(action)) {
				case CLOVER_CUTTING:
					contents = service.getAllCloverCuttingByDetailId(id)
							.stream()
							.map(cutting -> (Object) cutting).collect(Collectors.toList());
					break;
				case MANURING:
					contents = service.getAllManuringByDetailId(id)
							.stream()
							.map(manuring -> (Object) manuring).collect(Collectors.toList());
					break;
				case PESTICIDES_SPRAYING:
					contents = service.getAllPesticideSprayingByDetailId(id)
							.stream()
							.map(spraying -> (Object) spraying).collect(Collectors.toList());
					break;
				case PEST:
					contents = service.getAllPestByDetailId(id)
							.stream()
							.map(pest -> (Object) pest).collect(Collectors.toList());
					break;
			}
		} catch (NullPointerException | BeansException | BindingException | NoSuchElementException e) {
			logger.error(e);
		}

		return contents;
	}

	public CultivationDetail setContents(List<Object> contents) {
		this.contents = contents;
		return this;
	}

	public float getLatitude() {
		return latitude;
	}

	public CultivationDetail setLatitude(float latitude) {
		this.latitude = latitude;
		return this;
	}

	public float getLongitude() {
		return longitude;
	}

	public CultivationDetail setLongitude(float longitude) {
		this.longitude = longitude;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public CultivationDetail setTarget(String target) {
		this.target = target;
		return this;
	}

	public String getResultTarget() {
		return resultTarget;
	}

	public CultivationDetail setResultTarget(String resultTarget) {
		this.resultTarget = resultTarget;
		return this;
	}

	public long getId() {
		return id;
	}

	public CultivationDetail setId(long id) {
		this.id = id;
		return this;
	}

	public Date getActionUpdateDate() {
		return actionUpdateDate;
	}

	public CultivationDetail setActionUpdateDate(Date actionUpdateDate) {
		this.actionUpdateDate = actionUpdateDate;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public CultivationDetail setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public long getFieldPlotId() {
		return fieldPlotId;
	}

	public CultivationDetail setFieldPlotId(long fieldPlotId) {
		this.fieldPlotId = fieldPlotId;
		return this;
	}

	public String getAction() {
		return action;
	}

	public CultivationDetail setAction(String action) {
		this.action = action;
		return this;
	}

	public String getActionName() {
		return actionName;
	}

	public CultivationDetail setActionName(String actionName) {
		this.actionName = actionName;
		return this;
	}

	public long getActionIndex() {
		return actionIndex;
	}

	public CultivationDetail setActionIndex(long actionIndex) {
		this.actionIndex = actionIndex;
		return this;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public CultivationDetail setActionDate(Date actionDate) {
		this.actionDate = actionDate;
		return this;
	}

	public long getResponsibilityEmployeeId() {
		return responsibilityEmployeeId;
	}

	public CultivationDetail setResponsibilityEmployeeId(long responsibilityEmployeeId) {
		this.responsibilityEmployeeId = responsibilityEmployeeId;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final CultivationDetail detail = (CultivationDetail) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"action",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "action"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "action"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"actionName",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "actionName"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionName"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"actionIndex",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "actionIndex"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionIndex"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"actionDate",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "actionDate"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionDate"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"responsibilityEmployeeId",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "responsibilityEmployeeId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "responsibilityEmployeeId"));

		if (detail.getMedias() == null || detail.getMedias().isEmpty()) {
			errors.rejectValue("medias",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "medias"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "medias"));
		}

		if (detail.getLatitude() == 0) {
			errors.rejectValue("latitude",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "latitude"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "latitude"));
		}

		if (detail.getLongitude() == 0) {
			errors.rejectValue("longitude",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "longitude"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "longitude"));
		}

		switch (CultivationDetail.ACTION.findAction(action)) {
			case CLOVER_CUTTING:
				if (contents == null || contents.isEmpty()) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					break;
				}
				try {
					final List<CloverCutting> cloverCuttings = convertDetailContents();
					for (CloverCutting cloverCutting : cloverCuttings) {
						final List<ObjectError> errs = cloverCutting.getErrors();
						if (!errs.isEmpty()) {
							errors.rejectValue("contents",
									PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "contents"),
									errs.get(0).getDefaultMessage());
							break;
						}
					}
				} catch (NullPointerException | ValidationException e) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					logger.error(e);
				}
				break;

			case MANURING:
				if (contents == null || contents.isEmpty()) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					break;
				}
				try {
					final List<CultivationManuring> manurings = convertDetailContents();
					for (CultivationManuring manuring : manurings) {
						final List<ObjectError> errs = manuring.getErrors();
						if (!errs.isEmpty()) {
							errors.rejectValue("contents",
									PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "contents"),
									errs.get(0).getDefaultMessage());
							break;
						}
					}
				} catch (NullPointerException | ValidationException e) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					logger.error(e);
				}
				break;
			case PESTICIDES_SPRAYING:
				if (contents == null || contents.isEmpty()) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					break;
				}
				try {
					final List<PesticideSpraying> sprayings = convertDetailContents();
					for (PesticideSpraying spraying : sprayings) {
						final List<ObjectError> errs = spraying.getErrors();
						if (!errs.isEmpty()) {
							errors.rejectValue("contents",
									PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "contents"),
									errs.get(0).getDefaultMessage());
							break;
						}
					}
				} catch (NullPointerException | ValidationException e) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					logger.error(e);
				}
				break;

			case PEST:
				if (contents == null || contents.isEmpty()) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					break;
				}
				try {
					final List<CultivationPest> pests = convertDetailContents();
					for (CultivationPest pest : pests) {
						final List<ObjectError> errs = pest.getErrors();
						if (!errs.isEmpty()) {
							errors.rejectValue("contents",
									PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivationAction", "contents"),
									errs.get(0).getDefaultMessage());
							break;
						}
					}
				} catch (NullPointerException | ValidationException e) {
					errors.rejectValue("contents",
							"cultivationAction_content_error_code",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "contents"));
					logger.error(e);
				}
				break;
		}
	}

	@Override
	public String toString() {
		return "CultivationDetail{" +
				"id=" + id +
				", registrationId=" + registrationId +
				", cultivationId=" + cultivationId +
				", fieldPlotId=" + fieldPlotId +
				", action='" + action + '\'' +
				", actionName='" + actionName + '\'' +
				", actionIndex=" + actionIndex +
				", actionUpdateDate=" + actionUpdateDate +
				", actionDate=" + actionDate +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", responsibilityEmployeeId=" + responsibilityEmployeeId +
				", target='" + target + '\'' +
				", resultTarget='" + resultTarget + '\'' +
				", medias=" + medias +
				", contents=" + contents +
				", materialSupplements=" + materialSupplements +
				'}';
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}

	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final CultivationDetailService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationDetailServiceImpl.class);
		switch (getDataScenario()) {
			case INSERT:
				updateId = createId = account.getId();
				insert(service, account);
				break;
			case UPDATE:
				updateId = account.getId();
				update(service, account);
				break;
		}

		// Import media
		for (CropMedia media : medias) {
			media.setActionId(id)
					.setPhase(CropPhase.CULTIVATION.toString())
					.setCreateId(createId)
					.setUpdateId(updateId);
			media.save();
		}

		// create CultivationDetail
		switch (CultivationDetail.ACTION.findAction(action)) {
			case IRRIGATION:
				break;
			case CLOVER_CUTTING:
				final List<CloverCutting> cloverCuttings = convertDetailContents();
				for (CloverCutting cloverCutting : cloverCuttings) {
					cloverCutting.save(account);
				}
				break;
			case MANURING:
				final List<CultivationManuring> manurings = convertDetailContents();
				for (CultivationManuring manuring : manurings) {
					manuring.save(account);
				}
				saveMaterialSupplements(account);
				break;
			case PESTICIDES_SPRAYING:
				final List<PesticideSpraying> sprayings = convertDetailContents();
				for (PesticideSpraying spraying : sprayings) {
					spraying.save(account);
				}
				saveMaterialSupplements(account);
				break;
			case PEST:
				final List<CultivationPest> pests = convertDetailContents();
				for (CultivationPest pest : pests) {
					pest.save(account);
				}
				break;
		}
	}

	private void saveMaterialSupplements(Account account) throws ValidationException {
		if (materialSupplements != null && !materialSupplements.isEmpty()) {
			for (CropMaterialSupplement material : materialSupplements) {
				material.setRegistrationId(registrationId)
						.setPhaseId(cultivationId)
						.setActionId(id)
						.setPhase(CropMaterialSupplement.PHASE.CULTIVATION.getPhase());
				material.save(account);
			}
		}
	}

	private void insert(CultivationDetailService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.createDetail(this, true);
	}

	private void update(CultivationDetailService service, Account account) throws ValidationException {
		service.updateDetail(this, true);
	}

	/**
	 * Convert contents.
	 *
	 * @return a {@link List<T>} obj
	 * @throws ValidationException
	 * @throws IllegalArgumentException
	 */
	private <T> List<T> convertDetailContents() throws NullPointerException, ValidationException, IllegalArgumentException {
		final List<T> beans = new ArrayList<>();
		final ObjectMapper mapper = new ObjectMapper();

		switch (CultivationDetail.ACTION.findAction(action)) {
			case CLOVER_CUTTING:
				for (Object obj : contents) {
					try {
						final CloverCutting cloverCutting = mapper.convertValue(obj, CloverCutting.class);
						cloverCutting.setActionId(id);
						beans.add((T) cloverCutting);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
				break;
			case MANURING:
				for (Object obj : contents) {
					try {
						final CultivationManuring manuring = mapper.convertValue(obj, CultivationManuring.class);
						manuring.setActionId(id)
								.setFieldPlotId(fieldPlotId);
						beans.add((T) manuring);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
				break;
			case PESTICIDES_SPRAYING:
				for (Object obj : contents) {
					try {
						final PesticideSpraying spraying = mapper.convertValue(obj, PesticideSpraying.class);
						spraying.setActionId(id)
								.setFieldPlotId(fieldPlotId);
						beans.add((T) spraying);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
				break;
			case PEST:
				for (Object obj : contents) {
					try {
						final CultivationPest pest = mapper.convertValue(obj, CultivationPest.class);
						pest.setActionId(id)
								.setCultivationId(cultivationId)
								.setFieldPlotId(fieldPlotId);
						beans.add((T) pest);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
				break;
		}
		return beans;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public CultivationDetail setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public enum ACTION {
		UNKNOW("UNKNOW"),
		IRRIGATION("IRRIGATION"),
		MANURING("MANURING"),
		CLOVER_CUTTING("CLOVER_CUTTING"),
		PEST("PEST"),
		PESTICIDES_SPRAYING("PESTICIDES_SPRAYING");

		private String action;

		ACTION(String str) {
			action = str;
		}

		public static CultivationDetail.ACTION findAction(String str) {
			for (CultivationDetail.ACTION act : values()) {
				if (act.getAction().equals(str) || act.getActionUri().equals(str.toLowerCase())) {
					return act;
				}
			}
			return UNKNOW;
		}

		public String getAction() {
			return action;
		}

		private String getActionUri() {
			return action.toLowerCase().replaceAll("_", "-").trim();
		}
	}
}
