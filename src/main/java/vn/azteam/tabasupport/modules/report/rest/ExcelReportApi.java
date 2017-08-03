package vn.azteam.tabasupport.modules.report.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletResponse;

/**
 * Package vn.azteam.tabasupport.modules.report.rest
 * Created 3/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/report")
public interface ExcelReportApi {

	/**
	 * Truy xuất sử dụng xe máy
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @param employeeId a long
	 * @param startDateReport a long dateTime
	 * @param endDateReport a long dateTime
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/move/{employeeId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportMove(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId,
	                @RequestParam long startDateReport, @RequestParam long endDateReport) throws ValidationException;

	/**
	 * Json truy xuất sử dụng xe máy
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @param employeeId a long
	 * @param startDateReport a long dateTime
	 * @param endDateReport a long dateTime
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/move/{employeeId:[0-9]+}/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object reportGetMove(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId,
	                     @RequestParam long startDateReport, @RequestParam long endDateReport) throws ValidationException;

	/**
	 * Json Truy xuất ND (xã -huyện)
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/plant-area/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getInfoGrowingArea(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất ND (xã-huyện)
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/plant-area", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportInfoGrowingArea(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Json truy xuất GS BATV
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/zone-observer/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getZoneObserver(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất GS BATV
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/zone-observer", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportGetZoneObserver(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất vườn ươm
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/nursery", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportNursery(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất cập nhật diện tích
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/actual-acreage", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportActualAcreage(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất dự trù năng suất
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/expected-output", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportExpectedOutput(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Json truy xuất kế hoạch làm việc
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/task-plan/{employeeId:[0-9]+}/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object reportTaskPlan(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId) throws ValidationException;

	/**
	 * Truy xuất kế hoạch làm việc
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @param employeeId a long
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/task-plan/{employeeId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportExcelTaskPlan(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId) throws ValidationException;

	/**
	 * Json truy xuất cơ cấu giống
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/seed/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object reportSeedType(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất cơ cấu giống
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/seed/", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportExcelSeedType(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Json truy xuất chi phí ND
	 * @param auth a {@link OAuth2Authentication} object.
	 * @param companyId a long
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/farmer-cost/get/{companyId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object reportFarmerCost(OAuth2Authentication auth, @PathVariable long companyId) throws ValidationException;

	/**
	 * Truy xuất chi phí ND
	 *
	 * @param auth      a {@link OAuth2Authentication} object.
	 * @param companyId a long
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/farmer-cost/{companyId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportExcelFarmerCost(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long companyId) throws ValidationException;

	/**
	 * Json Truy xuất mật độ phân bón
	 *
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/fertilizer/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object reportFertilizer(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Truy xuất mật độ phân bón
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('REPORT', 'VIEW')")
	@RequestMapping(value = "/fertilizer", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	void reportExcelFertilizer(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException;
}
