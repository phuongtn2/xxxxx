package vn.azteam.tabasupport.modules.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.file.service.FileService;
import vn.azteam.tabasupport.modules.video.object.dao.VideoDao;
import vn.azteam.tabasupport.modules.video.object.model.Video;
import vn.azteam.tabasupport.modules.video.service.VideoService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.video.service.impl
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.video.service.VideoService
 * @since 1.0.0
 */
@Service
public class VideoServiceImpl extends BaseServiceImpl implements VideoService {
	@Autowired
	private VideoDao videoDao;

	@Autowired
	private FileService fileService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long uploadVideo(Video video) throws ValidationException {
		List<ObjectError> errorList = video.getErrors();
		if (errorList.isEmpty()) {
			long fileId = fileService.createFile(video);
			video.setFileId(fileId);
			videoDao.insertVideo(video);
			return video.getId();
		} else throw new ValidationException(errorList.get(0).getCode(), errorList.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Video> getAll() {
		return videoDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Video getVideoById(long videoId) {
		Video video = null;
		try {
			video = getAll().stream().filter(
					video1 -> video1.getId() == videoId
			).findFirst().get();
		} catch (Exception e) {
			logger.error(e);
		}
		return video;
	}
}
