package vn.azteam.tabasupport.modules.file.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.modules.file.service.DirectoryService;
import vn.azteam.tabasupport.modules.file.service.impl.DirectoryServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Package vn.azteam.tabasupport.modules.file.object.model
 * Created 12/19/2016
 *
 * @author hieunc
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class File extends BaseModel {
	private long id;
	private long companyId;
	private long repositoryId;
	private long directoryId;
	private String name;
	private String description;
	private String extension;
	private int status;
	private String type;
	private long size;
	private String version;
	private long owner;

	@JsonIgnore
	private java.io.File file;

	@JsonIgnore
	private MultipartFile multipartFile;

	public static <T extends File> T newInstanceFromHttpServlet(Class<T> aclass, MultipartHttpServletRequest request)
			throws ValidationException {
		T bean;
		try {
			bean = aclass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.java.language.code"),
					PropertiesParserUtil.getProperty("default.exception.java.language.msg"));
		}
		assert bean != null;
		bean.setMultipartFile(request.getFile("file"))
				.setDescription(request.getParameter("description"))
				.setVersion("1.0");
		return bean;
	}

	public File setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
		return this;
	}

	public long getId() {
		return id;
	}

	public File setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public File setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public java.io.File getFile() {
		final String path = getDirectory().getPath() == null
				? null
				: getDirectory().getPath() + System.getProperty("file.separator") +
				name +
				extension +
				System.getProperty("file.separator") +
				version;

		if (file == null && path != null) {
			file = new java.io.File(path);
		}

		return file;
	}

	public File setFile(java.io.File file) {
		this.file = file;
		return this;
	}

	public File parseMultipartFile() {
		version = version == null || version.isEmpty() ? "1.0" : version;

		if (file != null) {
			return this;
		}
		try {
			final int lastIndexOfPoint = multipartFile.getOriginalFilename().lastIndexOf('.') > 0
					? multipartFile.getOriginalFilename().lastIndexOf('.')
					: 0;

			this.name = this.name == null || this.name.isEmpty() ? multipartFile.getOriginalFilename().substring(0, lastIndexOfPoint) : this.name;
			this.extension = lastIndexOfPoint > 0 ? multipartFile.getOriginalFilename().substring(lastIndexOfPoint) : this.extension;

			final String path = getDirectory().getPath() != null ? String.format("%s%s%s%s", getDirectory().getPath(), System.getProperty("file.separator"), this.name, this.extension) : null;
			if (path == null || path.isEmpty()) throw new IOException("Directory path empty.");

			Files.createDirectories(Paths.get(path));

			this.file = new java.io.File(String.format("%s%s%s", path, System.getProperty("file.separator"), version));

			multipartFile.transferTo(this.file);
			this.type = multipartFile.getContentType();
			this.size = multipartFile.getSize();

		} catch (IOException e) {
			logger.error(e);
			this.file = null;
		}
		return this;
	}

	public long getRepositoryId() {
		return repositoryId;
	}

	public File setRepositoryId(long repositoryId) {
		this.repositoryId = repositoryId;
		return this;
	}

	public long getDirectoryId() {
		return directoryId;
	}

	public File setDirectoryId(long directoryId) {
		this.directoryId = directoryId;
		return this;
	}

	public String getName() {
		return name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public File setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getExtension() {
		return extension;
	}

	public File setExtension(String extension) {
		this.extension = extension;
		return this;
	}

	public String getType() {
		return type;
	}

	public File setType(String type) {
		this.type = type;
		return this;
	}

	public long getSize() {
		return size;
	}

	public File setSize(long size) {
		this.size = size;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public File setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public File setVersion(String version) {
		this.version = version;
		return this;
	}

	public long getOwner() {
		return owner;
	}

	public File setOwner(long owner) {
		this.owner = owner;
		return this;
	}

	@JsonIgnore
	public Directory getDirectory() {
		DirectoryService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(DirectoryServiceImpl.class);
			return service.getDirectoryById(directoryId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void beforeValidate() {
		super.beforeValidate();
		final Directory directory = getDirectory();
		owner = createId;
		if (directory != null) {
			companyId = directory.getCompanyId();
			repositoryId = directory.getId();
			if (multipartFile != null) {
				parseMultipartFile();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object o, Errors errors) {
		final File file = (File) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"name",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "directory", "userId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "userId"));

		if (file.getRepositoryId() < 1) {
			errors.rejectValue("repositoryId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "file", "repositoryId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "repositoryId"));
		}

		if (file.getDirectoryId() < 1) {
			errors.rejectValue("directoryId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "file", "directoryId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "directoryId"));
		}

		if (file.getCompanyId() < 1) {
			errors.rejectValue("companyId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "file", "companyId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "file"));
		}

		if (file.getOwner() < 1) {
			errors.rejectValue("owner",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "file", "owner"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "owner"));
		}
	}

	@Override
	public String toString() {
		return "File{" +
				"companyId=" + companyId +
				", repositoryId=" + repositoryId +
				", directoryId=" + directoryId +
				", name='" + name + '\'' +
				", extension='" + extension + '\'' +
				", type='" + type + '\'' +
				", size=" + size +
				", version='" + version + '\'' +
				", owner=" + owner +
				'}';
	}
}
