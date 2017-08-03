package vn.azteam.tabasupport.modules.nursery.object.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cropsession.enums.CropPhase;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMedia;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.nursery.service.NurseryDetailService;
import vn.azteam.tabasupport.modules.nursery.service.impl.NurseryDetailServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.model
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @see Serializable
 * @since 1.0.0
 */
public class NurseryDetail extends BaseModel {
	private long id;
	private long nurseryId;
	private String action;
	private String actionname;
	private int actionIndex;
	private Date actionUpdateDate;
	private Date actionDate;
	private String target;
	private String targetResult;
	private float coordX;
	private float coordY;
	private String actionName;

	private List<CropMedia> medias;

	private List<Object> contents;

	public List<CropMedia> getMedias() {
		if (medias != null) return medias;
		try {
			final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			medias = service.getMedia(id, CropPhase.NURSERY.toString(), 0);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return medias;
	}

	public NurseryDetail setMedias(List<CropMedia> medias) {
		this.medias = medias;
		return this;
	}

	public List<Object> getContents() {
		if (contents != null) {
			return contents;
		}

		try {
			final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);
			switch (ACTION.findAction(action)) {
				case PESTICIDES_SPRAYING:
					contents = service.getAllSprayingByDetailId(id)
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

	public NurseryDetail setContents(List<Object> contents) {
		this.contents = contents;
		return this;
	}

	public long getId() {
		return id;
	}

	public NurseryDetail setId(long id) {
		this.id = id;
		return this;
	}

	public long getNurseryId() {
		return nurseryId;
	}

	public NurseryDetail setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public String getAction() {
		return action;
	}

	public NurseryDetail setAction(String action) {
		this.action = action;
		return this;
	}

	public String getActionname() {
		return actionname;
	}

	public NurseryDetail setActionname(String actionname) {
		this.actionname = actionname;
		return this;
	}

	public String getActionName() {
		return actionname;
	}

	public NurseryDetail setActionName(String actionName) {
		this.actionName = actionName;
		this.actionname = actionName;
		return this;
	}

	public int getActionIndex() {
		return actionIndex;
	}

	public NurseryDetail setActionIndex(int actionIndex) {
		this.actionIndex = actionIndex;
		return this;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public NurseryDetail setActionDate(Date actionDate) {
		this.actionDate = actionDate;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public NurseryDetail setTarget(String target) {
		this.target = target;
		return this;
	}

	public Date getActionUpdateDate() {
		return actionUpdateDate;
	}

	public NurseryDetail setActionUpdateDate(Date actionUpdateDate) {
		this.actionUpdateDate = actionUpdateDate;
		return this;
	}

	public String getTargetResult() {
		return targetResult;
	}

	public NurseryDetail setTargetResult(String targetResult) {
		this.targetResult = targetResult;
		return this;
	}

	public float getCoordX() {
		return coordX;
	}

	public NurseryDetail setCoordX(float coordX) {
		this.coordX = coordX;
		return this;
	}

	public float getCoordY() {
		return coordY;
	}

	public NurseryDetail setCoordY(float coordY) {
		this.coordY = coordY;
		return this;
	}

	@Override
	public String toString() {
		return "NurseryDetail{" +
				"id=" + id +
				", nurseryId=" + nurseryId +
				", action='" + action + '\'' +
				", actionname='" + actionname + '\'' +
				", actionIndex=" + actionIndex +
				", actionDate=" + actionDate +
				", target='" + target + '\'' +
				'}';
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final NurseryDetail detail = (NurseryDetail) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actionname",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "actionname"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionname"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "action",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "action"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "action"));

		if (detail.getNurseryId() < 1) {
			errors.rejectValue("nurseryId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "nurseryId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "nurseryId"));
		}

		if (detail.getActionIndex() < 1) {
			errors.rejectValue("actionIndex",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "actionIndex"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionIndex"));
		}

		if (detail.getActionDate() == null) {
			errors.rejectValue("actionDate",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "actionDate"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionDate"));
		}

		if (detail.getActionUpdateDate() == null) {
			errors.rejectValue("actionUpdateDate",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "actionUpdateDate"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionUpdateDate"));
		}

		if (detail.getCoordX() == 0) {
			errors.rejectValue("coordX",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "coordX"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "coordX"));
		}

		if (detail.getCoordY() == 0) {
			errors.rejectValue("coordY",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "coordY"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "coordY"));
		}

		if (detail.getMedias() == null || detail.getMedias().isEmpty()) {
			errors.rejectValue("medias",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_detail", "medias"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "medias"));
		}
	}

	/**
	 * Save to DB.
	 *
	 * @param account a {@link Account} obj
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);

		switch (getDataScenario()) {
			case INSERT:
				updateId = createId = account.getId();
				insert(service);
				break;
			case UPDATE:
				updateId = account.getId();
				update(service);
				break;
		}
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}

	/**
	 * Insert new detail with contens
	 *
	 * @param service a {@link NurseryDetailService} obj
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	private void insert(NurseryDetailService service) throws NullPointerException, BeansException, ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("nursery_detail_code", getErrors().get(0).getDefaultMessage());
		service.createNurseryDetail(this);

		// Import media
		for (CropMedia media : medias) {
			media.setActionId(id)
					.setPhase(CropPhase.NURSERY.toString())
					.setCreateId(createId)
					.setUpdateId(updateId);
			media.save();
		}

		// create NurseryDetail
		switch (ACTION.findAction(action)) {
			case LEAF_CUTTING:
				break;
			case PESTICIDES_SPRAYING:
				final List<NurseryPesticideSpraying> sprayingList = convertDetailContents();

				for (NurseryPesticideSpraying spraying : sprayingList) {
					spraying.setCreateId(createId)
							.setUpdateId(updateId);
					service.createPesticideSprayingDetail(spraying);
				}
				break;
			case PEST:
				final List<NurseryPest> pestList = convertDetailContents();

				for (NurseryPest pest : pestList) {
					pest.setCreateId(createId)
							.setUpdateId(updateId);
					service.createPestDetail(pest);
				}
				break;
		}
	}

	/**
	 * Update detail and contents.
	 *
	 * @param service a {@link NurseryDetailService} obj
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	private void update(NurseryDetailService service) throws NullPointerException, BeansException, ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("nursery_detail_code", getErrors().get(0).getDefaultMessage());

		// Update media
		for (CropMedia media : medias) {
			media.setActionId(id)
					.setPhase(CropPhase.NURSERY.toString())
					.setUpdateId(updateId);
			media.save();
		}

		switch (ACTION.findAction(action)) {
			case LEAF_CUTTING:
				service.updateNurseryDetail(this, false);
				break;
			case PESTICIDES_SPRAYING:
				service.updateNurseryDetail(this, false);

				final List<NurseryPesticideSpraying> sprayingList = convertDetailContents();

				for (NurseryPesticideSpraying spraying : sprayingList) {
					spraying.setUpdateId(updateId);
					if (spraying.getId() < 1) {
						spraying.setCreateId(createId);
						service.createPesticideSprayingDetail(spraying);
					} else {
						service.updatePesticideSprayingDetail(spraying);
					}
				}
				break;
			case PEST:
				service.createNurseryDetail(this);
				final List<NurseryPest> pestList = convertDetailContents();

				for (NurseryPest pest : pestList) {
					pest.setUpdateId(updateId);
					if (pest.getId() < 1) {
						pest.setCreateId(createId);
						service.createPestDetail(pest);
					} else {
						service.updatePestDetail(pest);
					}
				}
				break;
		}
	}

	/**
	 * Convert contents.
	 *
	 * @return a {@link List<T>} obj
	 * @throws ValidationException
	 * @throws IllegalArgumentException
	 */
	private <T> List<T> convertDetailContents() throws ValidationException, IllegalArgumentException {
		final List<T> beans = new ArrayList<>();
		final ObjectMapper mapper = new ObjectMapper();

		switch (ACTION.findAction(action)) {
			case PESTICIDES_SPRAYING:
				for (Object obj : contents) {
					try {
						final NurseryPesticideSpraying spraying = mapper.convertValue(obj, NurseryPesticideSpraying.class);
						spraying.setActionId(id);
						if (!spraying.getErrors().isEmpty())
							throw new ValidationException("nursery_detail_contents_code", spraying.getErrors().get(0).getDefaultMessage());
						beans.add((T) spraying);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
				break;
			case PEST:
				for (Object obj : contents) {
					try {
						final NurseryPest pest = mapper.convertValue(obj, NurseryPest.class);
						pest.setNurseryId(nurseryId);
						pest.setActionId(id);
						if (!pest.getErrors().isEmpty())
							throw new ValidationException("nursery_detail_contents_code", pest.getErrors().get(0).getDefaultMessage());
						beans.add((T) pest);
					} catch (IllegalArgumentException e) {
						logger.error(e);
					}
				}
				break;
		}
		return beans;
	}

	public enum ACTION {
		UNKNOW("UNKNOW"),
		LEAF_CUTTING("LEAF_CUTTING"),
		PEST("PEST"),
		PESTICIDES_SPRAYING("PESTICIDES_SPRAYING");

		private String action;

		ACTION(String str) {
			action = str;
		}

		public static ACTION findAction(String str) {
			for (ACTION act : values()) {
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
