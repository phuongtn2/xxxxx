package vn.azteam.tabasupport.modules.pest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.pest.object.dao.PestDao;
import vn.azteam.tabasupport.modules.pest.object.model.Pest;
import vn.azteam.tabasupport.modules.pest.service.PestService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.pest.service.impl
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see PestService
 * @since 1.0.0
 */
@Service
public class PestServiceImpl extends BaseServiceImpl implements PestService {

	@Autowired
	private PestDao pestDao;

	@Override
	public List<Pest> getAll() {
		return pestDao.findAll();
	}
}
