package vn.azteam.tabasupport.core.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * package vn.azteam.tabasupport.core.controller
 * created 12/26/2016.
 *
 * @author thidt.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class HomeController extends AbstractController {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
	                                             HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("index");
		return model;
	}
}
