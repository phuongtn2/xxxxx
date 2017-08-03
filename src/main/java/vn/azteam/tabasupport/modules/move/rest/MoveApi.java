package vn.azteam.tabasupport.modules.move.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.move.object.model.Move;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.move.rest
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/move")
public interface MoveApi {

	@PreAuthorize("hasPermission('MOVE_MANAGER','VIEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object syncGetMoves(OAuth2Authentication auth);

	@PreAuthorize("hasPermission('MOVE_MANAGER','UPDATE')")
	@RequestMapping(value = "/sync", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object syncPostMoves(OAuth2Authentication auth, @RequestBody List<Move> moves) throws ValidationException;
}
