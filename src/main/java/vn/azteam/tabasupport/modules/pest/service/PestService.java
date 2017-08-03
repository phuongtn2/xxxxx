package vn.azteam.tabasupport.modules.pest.service;

import vn.azteam.tabasupport.modules.pest.object.model.Pest;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.pest.service
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface PestService {

	List<Pest> getAll();
}
