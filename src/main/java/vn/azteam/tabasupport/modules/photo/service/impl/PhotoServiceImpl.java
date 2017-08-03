package vn.azteam.tabasupport.modules.photo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.file.service.FileService;
import vn.azteam.tabasupport.modules.photo.object.dao.PhotoDao;
import vn.azteam.tabasupport.modules.photo.object.model.Photo;
import vn.azteam.tabasupport.modules.photo.service.PhotoService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.photo.service.impl
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.photo.service.PhotoService
 * @since 1.0.0
 */
@Service
public class PhotoServiceImpl extends BaseServiceImpl implements PhotoService {

	@Autowired
	private PhotoDao photoDao;

	@Autowired
	private FileService fileService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long uploadPhoto(Photo photo) throws ValidationException {
		List<ObjectError> errorList = photo.getErrors();
		if (errorList.isEmpty()) {
			long fileId = fileService.createFile(photo);
			photo.setFileId(fileId);
			photoDao.insertPhoto(photo);
			return photo.getId();
		} else throw new ValidationException(errorList.get(0).getCode(), errorList.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Photo> getAll() {
		return photoDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Photo getPhotoById(long photoId) {
		Photo photo = null;
		try {
			photo = getAll().stream().filter(
					photo1 -> photo1.getId() == photoId
			).findFirst().get();
		} catch (Exception e) {
			logger.error(e);
		}
		return photo;
	}
}
