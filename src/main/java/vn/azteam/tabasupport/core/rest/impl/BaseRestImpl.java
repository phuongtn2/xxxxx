package vn.azteam.tabasupport.core.rest.impl;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.core.object.model.BasePagination;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.util.PropertiesParserUtil;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * package vn.azteam.tabasupport.core.rest.impl
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public abstract class BaseRestImpl {
	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Get list object with paging
	 *
	 * @param request    a {@link HttpServletRequest} object.
	 * @param objectList a object extend {@link BaseModel}.
	 * @return a {@link BasePagination} object.
	 */
	protected BasePagination paging(HttpServletRequest request, List<? extends BaseModel> objectList) {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		BasePagination pagination = new BasePagination(objectList);
		if (paramsMapper.containsKey("offset")) {
			int offset = StringUtil.convertStringToUnsignedInt(request.getParameter("offset"), 0);
			pagination.setOffset(offset);
		}

		if (paramsMapper.containsKey("limit")) {
			int limit = StringUtil.convertStringToUnsignedInt(request.getParameter("limit"), 0);
			pagination.setLimit(limit);
		}

		return pagination;
	}

	/**
	 * Function response document, photo, video to client
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param obj      a {@link File} object.
	 * @param <T>      a object extend {@link File}.
	 * @throws ValidationException
	 */
	protected <T extends File> void responseToClient(HttpServletResponse response, T obj) throws ValidationException {
		if (obj != null) {
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "filename=\"" + obj.getName() + obj.getExtension() + "\"");
			response.setContentType(obj.getType());
			response.setContentLength((int) obj.getSize());
			try (InputStream inputStream = new FileInputStream(obj.getFile())) {
				IOUtils.copy(inputStream, response.getOutputStream());
			} catch (IOException e) {
				throw new ValidationException(PropertiesParserUtil.getProperty("validation.file.empty.code"), PropertiesParserUtil.getProperty("validation.file.empty.msg"));
			}
		} else {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.file.empty.code"), PropertiesParserUtil.getProperty("validation.file.empty.msg"));
		}
	}

	protected void responseExcelToClient(HttpServletResponse response, String filePath) throws IOException {
		java.io.File file = new java.io.File(filePath);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "filename=" + file.getName());
		InputStream inputStream = new FileInputStream(file);
		IOUtils.copy(inputStream, response.getOutputStream());
		file.delete();
	}
}
