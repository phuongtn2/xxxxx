package vn.azteam.tabasupport.modules.cropsession.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.modules.file.service.FileService;
import vn.azteam.tabasupport.modules.file.service.impl.FileServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.model
 * created 2/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @see Serializable
 * @since 1.0.0
 */
public class CropMedia extends BaseModel {
	private long id;
	private String phase;
	private long actionId;
	private long fileId;
	private int delFlag;

	private File file;

	public File getFile() {
		if (file != null) return file;
		try {
			final FileService service = ApplicationContextProvider.getApplicationContext().getBean(FileServiceImpl.class);
			file = service.getFileById(fileId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return file;
	}

	public CropMedia setFile(File file) {
		this.file = file;
		return this;
	}

	public long getId() {
		return id;
	}

	public CropMedia setId(long id) {
		this.id = id;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public CropMedia setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public long getFileId() {
		return fileId;
	}

	public CropMedia setFileId(long fileId) {
		this.fileId = fileId;
		return this;
	}

	public String getPhase() {
		return phase;
	}

	public CropMedia setPhase(String phase) {
		this.phase = phase;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public CropMedia setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final CropMedia media = (CropMedia) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phase",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery_media", "phase"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "phase"));

		if (media.getActionId() < 1) {
			errors.rejectValue("actionId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", String.format("%s_media", phase), "actionId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionId"));
		}

		try {
			final FileService service = ApplicationContextProvider.getApplicationContext().getBean(FileServiceImpl.class);
			if (!service.fileExist(fileId)) {
				errors.rejectValue("fileId",
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", String.format("%s_media", phase), "fileId"),
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.notExist", "fileId"));
			}
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
			errors.rejectValue("fileId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", String.format("%s_media", phase), "fileId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.notExist", "fileId"));
		}
	}

	@Override
	public String toString() {
		return "CropMedia{" +
				"id=" + id +
				", phase='" + phase + '\'' +
				", actionId=" + actionId +
				", fileId=" + fileId +
				", delFlag=" + delFlag +
				'}';
	}

	/**
	 * Save media to Db.
	 *
	 * @throws ValidationException
	 */
	public void save() throws ValidationException {
		try {
			final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			if (id < 1) {
				insert(service);
			} else {
				update(service);
			}
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}


	}

	/**
	 * Insert new
	 *
	 * @param sessionService a {@link CropSessionService}
	 * @throws ValidationException
	 */
	private void update(CropSessionService sessionService) throws ValidationException {
		sessionService.updateCropMedia(this);
	}

	/**
	 * Update exists.
	 *
	 * @param sessionService a {@link CropSessionService}
	 * @throws ValidationException
	 */
	private void insert(CropSessionService sessionService) throws ValidationException {
		sessionService.createCropMedia(this);
	}
}
