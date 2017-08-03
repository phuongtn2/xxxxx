package vn.azteam.tabasupport.modules.file.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.modules.file.service.DirectoryService;
import vn.azteam.tabasupport.modules.file.service.RepositoryService;
import vn.azteam.tabasupport.modules.file.service.impl.DirectoryServiceImpl;
import vn.azteam.tabasupport.modules.file.service.impl.RepositoryServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.file.object.model
 * created 12/28/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class Directory extends BaseModel {
	private long id;
	private long parentId;
	private long companyId;
	private long repositoryId;
	private String name;
	private int status;
	private long owner;

	@JsonIgnore
	private String path;

	public long getId() {
		return id;
	}

	public Directory setId(long id) {
		this.id = id;
		return this;
	}

	public long getParentId() {
		return parentId;
	}

	public Directory setParentId(long parentId) {
		this.parentId = parentId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Directory setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getRepositoryId() {
		return repositoryId;
	}

	public Directory setRepositoryId(long repositoryId) {
		this.repositoryId = repositoryId;
		return this;
	}

	public String getName() {
		return name;
	}

	public Directory setName(String name) {
		this.name = name;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public Directory setStatus(int status) {
		this.status = status;
		return this;
	}

	public long getOwner() {
		return owner;
	}

	public Directory setOwner(long owner) {
		this.owner = owner;
		return this;
	}

	public String getPath() {
		DirectoryService directoryService;
		try {
			directoryService = ApplicationContextProvider.getApplicationContext().getBean(DirectoryServiceImpl.class);
			return directoryService.getAbsoluteStringPath(this);
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public Directory setPath(String path) {
		this.path = path;
		return this;
	}

	@JsonIgnore
	public Repository getRepository() {
		RepositoryService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(RepositoryServiceImpl.class);
			return service.getRepositoryById(repositoryId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@JsonIgnore
	public String getRepositoryPath() {
		final Repository repo = getRepository();
		return repo == null ? null : repo.getPath();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void beforeValidate() {
		super.beforeValidate();
		owner = createId;
		status = 1;
		repositoryId = companyId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		final Directory directory = (Directory) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"name",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "directory", "userId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "userId"));

		if (directory.getRepositoryId() < 1) {
			errors.rejectValue("repositoryId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "directory", "repositoryId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "repositoryId"));
		}

		if (directory.getCompanyId() < 1) {
			errors.rejectValue("companyId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "directory", "companyId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "companyId"));
		}

		if (directory.getOwner() < 1) {
			errors.rejectValue("owner",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "directory", "owner"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "owner"));
		}

	}

	@Override
	public String toString() {
		return "Directory{" +
				"id=" + id +
				", parentId=" + parentId +
				", companyId=" + companyId +
				", repositoryId=" + repositoryId +
				", name='" + name + '\'' +
				", status=" + status +
				", owner=" + owner +
				", path=" + path +
				'}';
	}
}
