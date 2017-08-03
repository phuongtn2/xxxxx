package vn.azteam.tabasupport.modules.report.rest.impl;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jxls.area.XlsArea;
import org.jxls.command.Command;
import org.jxls.command.EachCommand;
import org.jxls.command.IfCommand;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.service.MaterialService;
import vn.azteam.tabasupport.modules.move.object.model.Move;
import vn.azteam.tabasupport.modules.move.service.MoveService;
import vn.azteam.tabasupport.modules.report.object.model.CompanyBranch;
import vn.azteam.tabasupport.modules.report.object.model.MoveReport;
import vn.azteam.tabasupport.modules.report.object.model.PlantAreaReport;
import vn.azteam.tabasupport.modules.report.object.model.TaskPlanReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.ActionDetailReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.ActionReport;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerTypeReport;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedTypeReport;
import vn.azteam.tabasupport.modules.report.object.model.zoneobserver.ZoneObserverReport;
import vn.azteam.tabasupport.modules.report.rest.ExcelReportApi;
import vn.azteam.tabasupport.modules.report.service.*;
import vn.azteam.tabasupport.util.FormatAreaListener;
import vn.azteam.tabasupport.util.PropertiesParserUtil;
import vn.azteam.tabasupport.util.SeedAreaFormatListener;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.rest.impl
 * Created 3/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see ExcelReportApi
 * @since 1.0.0
 */
@Component
public class ExcelReportApiImpl extends BaseRestImpl implements ExcelReportApi {

	private final static String FILENAME_EXPORT = "Taba.xls";
	private final static String TEMP_FILENAME_EXPORT = "Temp_Taba.xls";
	private final static String TEMP_FILENAME_EXPORT1 = "Temp_Taba1.xls";

	@Autowired
	private PlantAreaService plantAreaService;
	@Autowired
	private ZoneObserverService zoneObserverService;
	@Autowired
	private TaskPlanReportService taskPlanReportService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private SeedTypeReportService seedTypeReportService;
	@Autowired
	private FarmerCostReportService farmerCostReportService;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private FertilizerService fertilizerService;
	@Autowired
	private MoveService moveService;
	@Autowired
	private CompanyService companyService;

	private File catalinaHome = new File(System.getProperty("catalina.home")).getAbsoluteFile();
	private String filePath = catalinaHome + File.separator + "temp" + File.separator + FILENAME_EXPORT;
	private String filePathTemp = catalinaHome + File.separator + "temp" + File.separator + TEMP_FILENAME_EXPORT;
	private String filePathTemp1 = catalinaHome + File.separator + "temp" + File.separator + TEMP_FILENAME_EXPORT1;

	@Override
	public void reportMove(HttpServletResponse response, OAuth2Authentication auth,
	                       @PathVariable long employeeId, @RequestParam long startDateReport,
	                       @RequestParam long endDateReport) throws ValidationException {

		Account account = (Account) auth.getPrincipal();
		Employee employeeAssignee = employeeService.getEmployeeById(employeeId);
		if (account.getCompanyId() != employeeAssignee.getCompanyId()) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company."));
		}

		Date startDate = new Date(startDateReport);
		Date endDate = new Date(endDateReport);

		List<MoveReport> moveReports = moveService.getAllMoveByCompanyId(account.getCompanyId(), employeeId, startDate, endDate);

		if (moveReports.size() > 0) {
			try (InputStream is = getClass().getClassLoader().getResourceAsStream("report/excelTemplate/su_dung_xe_may.xls")) {
				try (OutputStream os = new FileOutputStream(filePath)) {
					Context context = new Context();
					context.putVar("moveReports", moveReports);
					context.putVar("startDateReport", startDate);
					context.putVar("endDateReport", endDate);
					context.putVar("zoneName", moveReports.get(0).getDistrictName());
					context.putVar("employeeName", moveReports.get(0).getEmployeeName());
					context.putVar("licensePlate", moveReports.get(0).getLicensePlate());
					JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Taba!A1");

					// response file excel report to client
					responseExcelToClient(response, filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}
	}

	@Override
	public Object reportGetMove(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId, @RequestParam long startDateReport, @RequestParam long endDateReport) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		Employee employeeAssignee = employeeService.getEmployeeById(employeeId);
		if (account.getCompanyId() != employeeAssignee.getCompanyId()) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company."));
		}

		Date startDate = new Date(startDateReport);
		Date endDate = new Date(endDateReport);
		return moveService.getAllMoveByCompanyId(account.getCompanyId(), employeeId, startDate, endDate);
	}

	@Override
	public Object getInfoGrowingArea(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		return plantAreaService.getAllPlantAreaReportByCompanyIds(companyIds, false);
	}

	@Override
	public void reportInfoGrowingArea(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {

		List<PlantAreaReport> plantAreaReportList;
		final Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}

		plantAreaReportList = plantAreaService.getAllPlantAreaReportByCompanyIds(companyIds, false);

		if (plantAreaReportList == null) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		insertDataIntoExcel(response, plantAreaReportList, "report/excelTemplate/truy_xuat_nong_dan.xls");
	}

	@Override
	public Object getZoneObserver(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		if (account.hasGlobalPermission("REPORT", "VIEW")) {
			return zoneObserverService.getAllZoneObserverReport();
		} else {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "report"));
		}

	}

	@Override
	public void reportGetZoneObserver(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {

		final Account account = (Account) auth.getPrincipal();
		if (!account.hasGlobalPermission("REPORT", "VIEW")) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "report"));
		}

		List<ZoneObserverReport> zoneObserverReports = zoneObserverService.getAllZoneObserverReport();
		if (zoneObserverReports == null || zoneObserverReports.size() == 0) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		insertZoneObserverDataToExcel(response, zoneObserverReports, "report/excelTemplate/truy_xuat_gs_batv.xls");
	}

	@Override
	public void reportNursery(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		List<PlantAreaReport> plantAreaReportList;

		final Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		plantAreaReportList = plantAreaService.getAllPlantAreaReportByCompanyIds(companyIds, true);
		if (plantAreaReportList == null) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		insertDataIntoExcel(response, plantAreaReportList, "report/excelTemplate/truy_xuat_vuon_uom.xls");
	}

	@Override
	public void reportActualAcreage(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		List<PlantAreaReport> plantAreaReportList;

		final Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		plantAreaReportList = plantAreaService.getAllPlantAreaReportByCompanyIds(companyIds, true);

		if (plantAreaReportList == null) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		insertDataIntoExcel(response, plantAreaReportList, "report/excelTemplate/truy_xuat_cap_nhat_dien_tich.xls");
	}

	@Override
	public void reportExpectedOutput(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		List<PlantAreaReport> plantAreaReportList;

		final Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		plantAreaReportList = plantAreaService.getAllPlantAreaReportByCompanyIds(companyIds, true);

		if (plantAreaReportList == null) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		//
		for (PlantAreaReport plantAreaReport : plantAreaReportList) {
			List<CompanyBranch> companyBranches = plantAreaReport.getCompanyBranches();
			if (companyBranches != null && companyBranches.size() > 0) {
				for (CompanyBranch companyBranch : companyBranches) {
					int cultivationQuantity = companyBranch.getCultivationQuantity();
					if (cultivationQuantity > 0) {
						companyBranch.setAverageLeaveInTree(companyBranch.getAverageLeaveInTree() / cultivationQuantity);
						companyBranch.setAverageDriedLeaveInTree(companyBranch.getAverageDriedLeaveInTree() / cultivationQuantity);
						companyBranch.setAverageDensity(companyBranch.getAverageDensity() / cultivationQuantity);
					}
				}
			}
		}

		insertDataIntoExcel(response, plantAreaReportList, "report/excelTemplate/truy_xuat_du_tru_nang_xuat.xls");
	}

	@Override
	public Object reportTaskPlan(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId) throws ValidationException {

		Account account = (Account) auth.getPrincipal();
		Employee employeeAssignee = employeeService.getEmployeeById(employeeId);
		if (account.getCompanyId() != employeeAssignee.getCompanyId()) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company."));
		}

		List<TaskPlanReport> allTaskPlanReports = new ArrayList<>();
		List<TaskPlanReport> currentWeekTaskPlanReports = taskPlanReportService.getAllCurrentWeekTaskPlanReport(employeeId);
		List<TaskPlanReport> nextWeekTaskPlantReports = taskPlanReportService.getAllNextWeekTaskPlanReport(employeeId);
		if (currentWeekTaskPlanReports == null || currentWeekTaskPlanReports.size() == 0) {
			return allTaskPlanReports;
		}
		int currentSpeedometer = 0;
		List<Move> moves = moveService.getAllMoveByEmployeeId(employeeId);
		for (Move move : moves) {
			if (move.getEndSpeedometer() > currentSpeedometer) {
				currentSpeedometer = move.getEndSpeedometer();
			}
		}

		for (TaskPlanReport taskPlanReport : currentWeekTaskPlanReports) {
			taskPlanReport.setCurrentSpeedometer(currentSpeedometer);
			allTaskPlanReports.add(taskPlanReport);
		}

		if (nextWeekTaskPlantReports != null && nextWeekTaskPlantReports.size() > 0) {
			for (TaskPlanReport taskPlanReport : nextWeekTaskPlantReports) {
				taskPlanReport.setCurrentSpeedometer(currentSpeedometer);
				allTaskPlanReports.add(taskPlanReport);
			}
		}

		return allTaskPlanReports;
	}

	@Override
	public Object reportSeedType(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		return seedTypeReportService.getAllSeedTypeReportExcel(companyIds);
	}

	@Override
	public Object reportFarmerCost(OAuth2Authentication auth, @PathVariable long companyId) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		List<Long> companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.contains(companyId)) {
			return farmerCostReportService.getAllActionReport(companyId);
		} else {
			return farmerCostReportService.getAllActionReport(account.getCompanyId());
		}
	}


	/**
	 * @param auth      a {@link OAuth2Authentication} object.
	 * @param companyId a long
	 * @throws ValidationException
	 */

	@Override
	public void reportExcelFarmerCost(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long companyId) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		List<Long> companyIds = companyService.getChildrenId(account.getCompanyId());
		if (!companyIds.contains(companyId)) {
			companyId = account.getCompanyId();
		}

		List<ActionReport> actionReports = farmerCostReportService.getAllActionReport(companyId);
		List<ActionDetailReport> actionDetailReports = farmerCostReportService.getAllActionDetailReport(companyId);

		if (actionDetailReports.size() == 0 || actionReports.size() == 0) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		createExcelFarmerCost(response, actionReports, actionDetailReports);
	}

	@Override
	public Object reportFertilizer(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		return fertilizerService.getAllFertilizerTypeReportExcel(companyIds);
	}

	@Override
	public void reportExcelFertilizer(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		List<Material> materials = materialService.getAllMaterials();
		materials.removeIf(
				material -> !material.getSubType().equalsIgnoreCase("fertilizer")
		);

		Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		List<FertilizerTypeReport> fertilizerTypeReports = fertilizerService.getAllFertilizerTypeReportExcel(companyIds);

		if (fertilizerTypeReports == null || fertilizerTypeReports.size() == 0) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		String templateFilePath = createTemplateForFertilizerReport(response, materials);
		if (!StringUtil.isEmpty(templateFilePath)) {
			insertFertilizerTypeReportToExcel(response, fertilizerTypeReports, templateFilePath);
		}

		//lay so dong can insert du lieu
		ArrayList<Integer> dataTemp = new ArrayList<>();
		int colDataNum = materials.size();
		int rowDataNum = fertilizerTypeReports.size();
		for (FertilizerTypeReport fertilizerTypeReport : fertilizerTypeReports) {
			rowDataNum += fertilizerTypeReport.getFertilizerTypeCompanyBranches().size();
			dataTemp.add(fertilizerTypeReport.getFertilizerTypeCompanyBranches().size());
		}

		try {
			FileInputStream file = new FileInputStream(new File(filePathTemp1));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			//Update the value of cell
			//E5: row = 4 ; col = 4

			int lol = 0;
			for (int rowNum = 4; rowNum < 4 + rowDataNum; ) {
				for (int colNum = 4; colNum < 4 + colDataNum + 1; colNum++) {
					Cell cell = sheet.getRow(rowNum).getCell(colNum);
					CellRef nextCellRef = new CellRef(sheet.getSheetName(), rowNum + 1, colNum);
					CellRef lastCellRef = new CellRef(sheet.getSheetName(), rowNum + dataTemp.get(lol), colNum);
					cell.setCellFormula("SUM(" + nextCellRef.getCellName() + ":" + lastCellRef.getCellName() + ")");

					if (colNum == 4 + colDataNum) {
						Cell cellDensity = sheet.getRow(rowNum).getCell(colNum + 4);
						CellRef nextCellRefDensity = new CellRef(sheet.getSheetName(), rowNum + 1, colNum + 4);
						CellRef lastCellRefDensity = new CellRef(sheet.getSheetName(), rowNum + dataTemp.get(lol), colNum + 4);
						cellDensity.setCellFormula("AVERAGE(" + nextCellRefDensity.getCellName() + ":" + lastCellRefDensity.getCellName() + ")");
					}
				}
				rowNum += dataTemp.get(lol) + 1;
				lol += 1;
			}

			file.close();

			FileOutputStream outFile = new FileOutputStream(new File(filePath));
			workbook.write(outFile);
			outFile.close();
			//export file to client
			responseExcelToClient(response, filePath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reportExcelSeedType(HttpServletResponse response, OAuth2Authentication auth) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		List<Long> companyIds;
		companyIds = companyService.getChildrenId(account.getCompanyId());
		if (companyIds.size() < 1) {
			companyIds.add(account.getCompanyId());
		}
		List<SeedTypeReport> seedTypeReports;
		seedTypeReports = seedTypeReportService.getAllSeedTypeReportExcel(companyIds);

		if (seedTypeReports == null || seedTypeReports.size() == 0) {
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"), PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		List<Material> materials = materialService.getAllMaterials();
		materials.removeIf(
				material -> !material.getSubType().equalsIgnoreCase("seed")
		);

		String templateFilePath = createTemplateForSeedTypeReport(materials, response);
		if (!StringUtil.isEmpty(templateFilePath)) {
			insertSeedTypeReportToExcel(response, seedTypeReports, templateFilePath);
		}

		//lay so dong can insert du lieu
		ArrayList<Integer> dataTemp = new ArrayList<>();
		int colDataNum = materials.size();
		int rowDataNum = seedTypeReports.size();
		for (SeedTypeReport seedTypeReport : seedTypeReports) {
			rowDataNum += seedTypeReport.getSeedTypeCompanyBranches().size();
			dataTemp.add(seedTypeReport.getSeedTypeCompanyBranches().size());
		}

		try {
			FileInputStream file = new FileInputStream(new File(filePathTemp1));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			//Update the value of cell
			//E5: row = 4 ; col = 4
			for (int rowNum = 4; rowNum < 4 + rowDataNum; rowNum++) {
				Cell cell = sheet.getRow(rowNum).getCell(4);
				CellRef nextCellRef = new CellRef(sheet.getSheetName(), rowNum, 4 + 1);
				CellRef lastCellRef = new CellRef(sheet.getSheetName(), rowNum, 4 + colDataNum);
				cell.setCellFormula("SUM(" + nextCellRef.getCellName() + ":" + lastCellRef.getCellName() + ")");
			}
			int lol = 0;
			for (int rowNum = 4; rowNum < 4 + rowDataNum; ) {
				for (int colNum = 5; colNum < 5 + colDataNum; colNum++) {
					Cell cell = sheet.getRow(rowNum).getCell(colNum);
					CellRef nextCellRef = new CellRef(sheet.getSheetName(), rowNum + 1, colNum);
					CellRef lastCellRef = new CellRef(sheet.getSheetName(), rowNum + dataTemp.get(lol), colNum);
					cell.setCellFormula("SUM(" + nextCellRef.getCellName() + ":" + lastCellRef.getCellName() + ")");
				}
				rowNum += dataTemp.get(lol) + 1;
				lol += 1;
			}

			file.close();

			FileOutputStream outFile = new FileOutputStream(new File(filePath));
			workbook.write(outFile);
			outFile.close();
			//export file to client
			responseExcelToClient(response, filePath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reportExcelTaskPlan(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long employeeId) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		Employee employeeAssignee = employeeService.getEmployeeById(employeeId);
		if (account.getCompanyId() != employeeAssignee.getCompanyId()) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company."));
		}

		List<TaskPlanReport> currentWeekTaskPlanReports = taskPlanReportService.getAllCurrentWeekTaskPlanReport(employeeId);
		List<TaskPlanReport> nextWeekTaskPlantReports = taskPlanReportService.getAllNextWeekTaskPlanReport(employeeId);
		String employeeName = "";
		int currentSpeedometer = 0;
		if (currentWeekTaskPlanReports != null) {
			Employee employee = employeeService.getEmployeeById(employeeId);
			employeeName = employee.getFullName();

			List<Move> moves = moveService.getAllMoveByEmployeeId(employeeId);
			for (Move move : moves) {
				if (move.getEndSpeedometer() > currentSpeedometer) {
					currentSpeedometer = move.getEndSpeedometer();
				}
			}
		}

		try (InputStream is = getClass().getClassLoader().getResourceAsStream("report/excelTemplate/truy_xuat_ke_hoach_lam_viec.xls")) {
			try (OutputStream os = new FileOutputStream(filePath)) {
				Context context = new Context();
				context.putVar("currentTasks", currentWeekTaskPlanReports);
				context.putVar("nextTasks", nextWeekTaskPlantReports);
				context.putVar("currentSpeedometer", currentSpeedometer);
				context.putVar("dateReport", currentWeekTaskPlanReports.get(0).getDateReport());
				context.putVar("employeeName", employeeName);
				JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Taba!A1");

				//export file to client
				responseExcelToClient(response, filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertDataIntoExcel(HttpServletResponse response, List<PlantAreaReport> plantAreaReportList, String filePathTemplate) {

		try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePathTemplate)) {

			try (OutputStream os = new FileOutputStream(filePath)) {
				Transformer transformer = TransformerFactory.createTransformer(is, os);
				XlsArea xlsArea = new XlsArea("Taba!A1:M7", transformer);
				XlsArea plantReportArea = new XlsArea("Taba!B5:M6", transformer);
				EachCommand plantReportEachCommand = new EachCommand("plantReport", "plantReports", plantReportArea, EachCommand.Direction.DOWN);
				XlsArea companyBranchArea = new XlsArea("Taba!B6:M6", transformer);

				XlsArea ifArea = new XlsArea("Taba!B6:M6", transformer);
				XlsArea elseArea = new XlsArea("Taba!B6:M6", transformer);
				IfCommand ifCommand = new IfCommand("plantReport.companyId > 0",
						ifArea,
						elseArea);
				ifArea.addAreaListener(new FormatAreaListener(ifArea));
				elseArea.addAreaListener(new FormatAreaListener(elseArea));

				companyBranchArea.addCommand(new AreaRef("Taba!B6:M6"), ifCommand);
				Command companyBranchEachCommand = new EachCommand("companyBranch", "plantReport.companyBranches", companyBranchArea);
				plantReportArea.addCommand(new AreaRef("Taba!B6:M6"), companyBranchEachCommand);
				xlsArea.addCommand(new AreaRef("Taba!B5:M6"), plantReportEachCommand);
				Context context = new Context();
				context.putVar("plantReports", plantAreaReportList);
				xlsArea.applyAt(new CellRef("Taba!A1"), context);
				xlsArea.processFormulas();
				transformer.write();

				//export file to client
				responseExcelToClient(response, filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertZoneObserverDataToExcel(HttpServletResponse response, List<ZoneObserverReport> zoneObserverReports, String filePathTemplate) {

		try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePathTemplate)) {

			try (OutputStream os = new FileOutputStream(filePath)) {
				Transformer transformer = TransformerFactory.createTransformer(is, os);
				XlsArea xlsArea = new XlsArea("Taba!A1:M7", transformer);
				XlsArea zoneObserverReportArea = new XlsArea("Taba!B5:M6", transformer);
				EachCommand zoneObserverReportEachCommand = new EachCommand("zoneObserverReport", "zoneObserverReports", zoneObserverReportArea, EachCommand.Direction.DOWN);
				XlsArea zoneObserverBranchArea = new XlsArea("Taba!B6:M6", transformer);

				XlsArea ifArea = new XlsArea("Taba!B6:M6", transformer);
				XlsArea elseArea = new XlsArea("Taba!B6:M6", transformer);
				IfCommand ifCommand = new IfCommand("1 > 0",
						ifArea,
						elseArea);
				ifArea.addAreaListener(new FormatAreaListener(ifArea));
				elseArea.addAreaListener(new FormatAreaListener(elseArea));

				zoneObserverBranchArea.addCommand(new AreaRef("Taba!B6:M6"), ifCommand);
				Command zoneObserverBranchEachCommand = new EachCommand("zoneObserverBranch", "zoneObserverReport.zoneObserverBranches", zoneObserverBranchArea);
				zoneObserverReportArea.addCommand(new AreaRef("Taba!B6:M6"), zoneObserverBranchEachCommand);
				xlsArea.addCommand(new AreaRef("Taba!B5:M6"), zoneObserverReportEachCommand);
				Context context = new Context();
				context.putVar("zoneObserverReports", zoneObserverReports);
				xlsArea.applyAt(new CellRef("Taba!A1"), context);
				xlsArea.processFormulas();
				transformer.write();

				//export file to client
				responseExcelToClient(response, filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertSeedTypeReportToExcel(HttpServletResponse response, List<SeedTypeReport> seedTypeReports, String filePathTemplate) {

		File f = new File(filePathTemplate);
		try (InputStream is = new FileInputStream(f)) {
			try (OutputStream os = new FileOutputStream(filePathTemp1)) {
				Transformer transformer = TransformerFactory.createTransformer(is, os);
				XlsArea xlsArea = new XlsArea("Taba!A1:O7", transformer);

				// Khai bao vung tong
				XlsArea seedTypeReportArea = new XlsArea("Taba!B5:O6", transformer);
				EachCommand seedTypeReportEachCommand = new EachCommand("seedTypeReport", "seedTypeReports", seedTypeReportArea, EachCommand.Direction.DOWN);

				// khai bao vung moi dong
				XlsArea seedTypeCompanyBranchArea = new XlsArea("Taba!B6:O6", transformer);

				XlsArea ifArea = new XlsArea("Taba!B6:O6", transformer);
				XlsArea elseArea = new XlsArea("Taba!B6:O6", transformer);
				IfCommand ifCommand = new IfCommand("1 > 0",
						ifArea,
						elseArea);
				ifArea.addAreaListener(new FormatAreaListener(ifArea));
				elseArea.addAreaListener(new FormatAreaListener(elseArea));

				seedTypeCompanyBranchArea.addCommand(new AreaRef("Taba!B6:O6"), ifCommand);
				EachCommand seedTypeCompanyBranchEachCommand = new EachCommand("seedTypeCompanyBranch", "seedTypeReport.seedTypeCompanyBranches", seedTypeCompanyBranchArea);

				///
				//khai bao vung con

				XlsArea seedArea = new XlsArea("Taba!F6:O6", transformer);

				XlsArea seedIfArea = new XlsArea("Taba!F6:F6", transformer);
				XlsArea seedElseArea = new XlsArea("Taba!F6:F6", transformer);
				IfCommand seedIfCommand = new IfCommand("1 > 0",
						seedIfArea,
						seedElseArea);
				seedArea.addCommand(new AreaRef("Taba!F6:O6"), seedIfCommand);
				EachCommand seedEachCommand = new EachCommand("seedReport", "seedTypeCompanyBranch.seedReports", seedArea, EachCommand.Direction.RIGHT);
				seedTypeCompanyBranchArea.addCommand(new AreaRef("Taba!F6:O6"), seedEachCommand);
				///

				seedTypeReportArea.addCommand(new AreaRef("Taba!B6:O6"), seedTypeCompanyBranchEachCommand);
				xlsArea.addCommand(new AreaRef("Taba!B5:O6"), seedTypeReportEachCommand);

				Context context = new Context();
				context.putVar("seedTypeReports", seedTypeReports);
				xlsArea.applyAt(new CellRef("Taba!A1"), context);

				xlsArea.processFormulas();
				transformer.write();

				//export file to client
				java.io.File file = new java.io.File(filePathTemp1);
				InputStream inputStream = new FileInputStream(file);
				IOUtils.copy(inputStream, response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String createTemplateForSeedTypeReport(List<Material> materials, HttpServletResponse response) {

		try (InputStream is = getClass().getClassLoader().getResourceAsStream("report/excelTemplate/truy_xuat_co_cau_giong_template.xls")) {

			try (OutputStream os = new FileOutputStream(filePathTemp)) {
				Transformer transformer = TransformerFactory.createTransformer(is, os);
				XlsArea xlsArea = new XlsArea("Taba!A1:R7", transformer);
				String stringB5 = "${seedTypeReport.companyName}";
				String stringB6 = "${seedTypeReport.companyName}";
				String stringC6 = "${seedTypeCompanyBranch.districtName}";
				String stringD6 = "${seedTypeCompanyBranch.provinceName}";
				String stringF6 = "${seedReport.actualAcreage}";

				//
				XlsArea seedTypeReportArea = new XlsArea("Taba!F2:F6", transformer);
				EachCommand seedTypeReportEachCommand = new EachCommand("material", "materials", seedTypeReportArea, EachCommand.Direction.RIGHT);

				XlsArea ifArea = new XlsArea("Taba!F2:F6", transformer);
				XlsArea elseArea = new XlsArea("Taba!F2:F6", transformer);
				IfCommand ifCommand = new IfCommand("1 > 0",
						ifArea,
						elseArea);
				ifArea.addAreaListener(new SeedAreaFormatListener(ifArea));
				elseArea.addAreaListener(new SeedAreaFormatListener(elseArea));
				seedTypeReportArea.addCommand(new AreaRef("Taba!F2:F6"), ifCommand);

				xlsArea.addCommand(new AreaRef("Taba!F2:F6"), seedTypeReportEachCommand);

				Context context = new Context();
				context.putVar("materials", materials);
				context.putVar("stringB5", stringB5);
				context.putVar("stringB6", stringB6);
				context.putVar("stringC6", stringC6);
				context.putVar("stringD6", stringD6);
				context.putVar("stringF6", stringF6);
				xlsArea.applyAt(new CellRef("Taba!A1"), context);
				transformer.write();
				java.io.File file = new java.io.File(filePathTemp);
				InputStream inputStream = new FileInputStream(file);
				IOUtils.copy(inputStream, response.getOutputStream());
				return filePathTemp;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String createTemplateForFertilizerReport(HttpServletResponse response, List<Material> materials) {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("report/excelTemplate/truy_xuat_mat_do_phan_bon.xls")) {

			try (OutputStream os = new FileOutputStream(filePathTemp)) {
				Transformer transformer = TransformerFactory.createTransformer(is, os);
				XlsArea xlsArea = new XlsArea("Taba!A1:R7", transformer);
				String stringB5 = "${fertilizerTypeReport.companyName}";
				String stringB6 = "${fertilizerTypeReport.companyName}";
				String stringC6 = "${fertilizerTypeCompanyBranch.districtName}";
				String stringD6 = "${fertilizerTypeCompanyBranch.provinceName}";
				String stringE6 = "${fertilizerTypeCompanyBranch.acreage}";
				String stringF6 = "${fertilizerReport.materialQuantity}";
				String stringU6 = "${fertilizerTypeCompanyBranch.density}";

				//
				XlsArea seedTypeReportArea = new XlsArea("Taba!F2:F6", transformer);
				EachCommand seedTypeReportEachCommand = new EachCommand("material", "materials", seedTypeReportArea, EachCommand.Direction.RIGHT);

				XlsArea ifArea = new XlsArea("Taba!F2:F6", transformer);
				XlsArea elseArea = new XlsArea("Taba!F2:F6", transformer);
				IfCommand ifCommand = new IfCommand("1 > 0",
						ifArea,
						elseArea);
				ifArea.addAreaListener(new SeedAreaFormatListener(ifArea));
				elseArea.addAreaListener(new SeedAreaFormatListener(elseArea));
				seedTypeReportArea.addCommand(new AreaRef("Taba!F2:F6"), ifCommand);

				xlsArea.addCommand(new AreaRef("Taba!F2:F6"), seedTypeReportEachCommand);

				Context context = new Context();
				context.putVar("materials", materials);
				context.putVar("stringB5", stringB5);
				context.putVar("stringB6", stringB6);
				context.putVar("stringC6", stringC6);
				context.putVar("stringD6", stringD6);
				context.putVar("stringE6", stringE6);
				context.putVar("stringF6", stringF6);
				context.putVar("stringU6", stringU6);
				xlsArea.applyAt(new CellRef("Taba!A1"), context);
				transformer.write();
				java.io.File file = new java.io.File(filePathTemp);
				InputStream inputStream = new FileInputStream(file);
				IOUtils.copy(inputStream, response.getOutputStream());
				return filePathTemp;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private void insertFertilizerTypeReportToExcel(HttpServletResponse response, List<FertilizerTypeReport> fertilizerTypeReports, String filePathTemplate) {

		File f = new File(filePathTemplate);
		try (InputStream is = new FileInputStream(f)) {
			try (OutputStream os = new FileOutputStream(filePathTemp1)) {
				Transformer transformer = TransformerFactory.createTransformer(is, os);
				XlsArea xlsArea = new XlsArea("Taba!A1:Z7", transformer);

				// Khai bao vung tong
				XlsArea fertilizerTypeReportArea = new XlsArea("Taba!B5:Z6", transformer);
				EachCommand fertilizerTypeReportEachCommand = new EachCommand("fertilizerTypeReport", "fertilizerTypeReports", fertilizerTypeReportArea, EachCommand.Direction.DOWN);

				// khai bao vung moi dong
				XlsArea fertilizerTypeCompanyBranchArea = new XlsArea("Taba!B6:Z6", transformer);

				XlsArea ifArea = new XlsArea("Taba!B6:Z6", transformer);
				XlsArea elseArea = new XlsArea("Taba!B6:Z6", transformer);
				IfCommand ifCommand = new IfCommand("1 > 0",
						ifArea,
						elseArea);
				ifArea.addAreaListener(new FormatAreaListener(ifArea));
				elseArea.addAreaListener(new FormatAreaListener(elseArea));

				fertilizerTypeCompanyBranchArea.addCommand(new AreaRef("Taba!B6:Z6"), ifCommand);
				EachCommand fertilizerTypeCompanyBranchEachCommand = new EachCommand("fertilizerTypeCompanyBranch", "fertilizerTypeReport.fertilizerTypeCompanyBranches", fertilizerTypeCompanyBranchArea);

				///
				//khai bao vung con

				XlsArea fertilizerArea = new XlsArea("Taba!F6:Z6", transformer);

				XlsArea fertilizerIfArea = new XlsArea("Taba!F6:F6", transformer);
				XlsArea fertilizerElseArea = new XlsArea("Taba!F6:F6", transformer);
				IfCommand fertilizerIfCommand = new IfCommand("1 > 0",
						fertilizerIfArea,
						fertilizerElseArea);
				fertilizerArea.addCommand(new AreaRef("Taba!F6:Z6"), fertilizerIfCommand);
				EachCommand fertilizerEachCommand = new EachCommand("fertilizerReport", "fertilizerTypeCompanyBranch.fertilizerReports", fertilizerArea, EachCommand.Direction.RIGHT);
				fertilizerTypeCompanyBranchArea.addCommand(new AreaRef("Taba!F6:Z6"), fertilizerEachCommand);
				///

				fertilizerTypeReportArea.addCommand(new AreaRef("Taba!B6:Z6"), fertilizerTypeCompanyBranchEachCommand);
				xlsArea.addCommand(new AreaRef("Taba!B5:Z6"), fertilizerTypeReportEachCommand);

				Context context = new Context();
				context.putVar("fertilizerTypeReports", fertilizerTypeReports);
				xlsArea.applyAt(new CellRef("Taba!A1"), context);

				xlsArea.processFormulas();
				transformer.write();

				//export file to client
				java.io.File file = new java.io.File(filePathTemp1);
				InputStream inputStream = new FileInputStream(file);
				IOUtils.copy(inputStream, response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String createExcelFarmerCost(HttpServletResponse response, List<ActionReport> actionReports, List<ActionDetailReport> actionDetailReports) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("report/excelTemplate/chi_phi_nong_dan.xls").getFile());
			FileInputStream fileInputStream = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			sheet.setDisplayGridlines(false);
			HSSFCellStyle styleBold = workbook.createCellStyle();
			HSSFCellStyle style = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			Font font = workbook.createFont();
			Font fontBold = workbook.createFont();

			Cell cellCropSeason = sheet.getRow(0).createCell(0);
			cellCropSeason.setCellValue("Mùa vụ: " + actionReports.get(0).getCropName());
			Cell cellCompany = sheet.getRow(1).createCell(0);
			cellCompany.setCellValue("Tên đơn vị: " + actionReports.get(0).getCompanyName());

			// A5: row 4; col = 0
			int titleStartRow = 4;

			boolean isCreatedLaborCost = false;
			boolean isCreatedLaborCultivation = false;
			boolean isCreatedLaborHarvest = false;
			boolean isCreatedLaborNursery = false;

			boolean isCreatedMaterial = false;
			boolean isCreatedMaterialCultivation = false;
			boolean isCreatedMaterialHarvest = false;
			boolean isCreatedMaterialNursery = false;

			boolean isCreatedOther = false;

			// tinh tong moi chi phi
			Cell cellPriceLaborCultivation = null;
			Cell cellPriceLaborNursery = null;
			Cell cellPriceLaborHarvest = null;
			Cell cellPriceMaterialCultivation = null;
			Cell cellPriceMaterialNursery = null;
			Cell cellPriceMaterialHarvest = null;
			Cell cellPriceOther = null;

			Cell cellPriceLabor = null;
			String cellPriceLaborFormula = "";
			Cell cellPriceMaterial = null;
			String cellPriceMaterialFormula = "";
			Cell cellTotal = sheet.getRow(3).createCell(3);
			String totalFormula = "";

			//Tính số row cho mỗi loại dữ liệu :labor_cost, farmer, other
			int[] data = getNumberOfData(actionReports);

			for (ActionReport actionReport : actionReports) {

				int titleStartCol = 0;
				if (actionReport.getType().equalsIgnoreCase("LABOR_COST")) {
					if (!isCreatedLaborCost) {
						cellPriceLabor = sheet.getRow(titleStartRow).createCell(3);
						CellRef cellLaborRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
						Cell cellLabor = sheet.getRow(titleStartRow).createCell(titleStartCol);
						cellLabor.setCellValue("A. CÔNG LAO ĐỘNG");
						titleStartRow++;
						isCreatedLaborCost = true;

						totalFormula += "+" + cellLaborRef;
						cellTotal.setCellFormula(totalFormula);
					}

					if (actionReport.getActionType().equalsIgnoreCase("CULTIVATION")) {
						if (!isCreatedLaborCultivation) {
							Cell cellCultivation = sheet.getRow(titleStartRow).createCell(titleStartCol);
							CellRef cellCultivationRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
							cellPriceLaborFormula += "+" + cellCultivationRef;
							cellPriceLabor.setCellFormula(cellPriceLaborFormula);

							CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
							CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[0], 3);
							cellPriceLaborCultivation = sheet.getRow(titleStartRow).createCell(3);
							cellPriceLaborCultivation.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

							cellCultivation.setCellValue("1. RUỘNG TRỒNG");
							titleStartRow++;
							isCreatedLaborCultivation = true;
						}

					} else if (actionReport.getActionType().equalsIgnoreCase("HARVEST")) {
						if (!isCreatedLaborHarvest) {
							Cell cellHarvest = sheet.getRow(titleStartRow).createCell(titleStartCol);
							CellRef cellHarvestRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
							cellPriceLaborFormula += "+" + cellHarvestRef;
							cellPriceLabor.setCellFormula(cellPriceLaborFormula);

							CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
							CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[1], 3);
							cellPriceLaborHarvest = sheet.getRow(titleStartRow).createCell(3);
							cellPriceLaborHarvest.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

							cellHarvest.setCellValue("2. THU HOẠCH VÀ LÒ SẤY");
							titleStartRow++;
							isCreatedLaborHarvest = true;
						}

					} else if (actionReport.getActionType().equalsIgnoreCase("NURSERY")) {
						if (!isCreatedLaborNursery) {
							Cell cellNursery = sheet.getRow(titleStartRow).createCell(titleStartCol);
							CellRef cellNurseryRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
							cellPriceLaborFormula += "+" + cellNurseryRef;
							cellPriceLabor.setCellFormula(cellPriceLaborFormula);

							CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
							CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[2], 3);
							cellPriceLaborNursery = sheet.getRow(titleStartRow).createCell(3);
							cellPriceLaborNursery.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

							cellNursery.setCellValue("3. VƯỜN ƯƠM");
							titleStartRow++;
							isCreatedLaborNursery = true;
						}
					}
				}

				if (actionReport.getType().equalsIgnoreCase("MATERIAL")) {
					if (!isCreatedMaterial) {
						cellPriceMaterial = sheet.getRow(titleStartRow).createCell(3);
						CellRef cellMaterialRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
						Cell cellMaterial = sheet.getRow(titleStartRow).createCell(titleStartCol);
						cellMaterial.setCellValue("B. VẬT TƯ");
						titleStartRow++;
						isCreatedMaterial = true;

						totalFormula += "+" + cellMaterialRef;
						cellTotal.setCellFormula(totalFormula);
					}
					if (actionReport.getActionType().equalsIgnoreCase("CULTIVATION")) {
						if (!isCreatedMaterialCultivation) {
							Cell cellCultivation = sheet.getRow(titleStartRow).createCell(titleStartCol);
							CellRef cellCultivationRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
							cellPriceMaterialFormula += "+" + cellCultivationRef;
							cellPriceMaterial.setCellFormula(cellPriceMaterialFormula);

							CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
							CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[3], 3);
							cellPriceMaterialCultivation = sheet.getRow(titleStartRow).createCell(3);
							cellPriceMaterialCultivation.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

							cellCultivation.setCellValue("1. RUỘNG TRỒNG");
							titleStartRow++;
							isCreatedMaterialCultivation = true;
						}
					} else if (actionReport.getActionType().equalsIgnoreCase("HARVEST")) {
						if (!isCreatedMaterialHarvest) {
							Cell cellHarvest = sheet.getRow(titleStartRow).createCell(titleStartCol);
							CellRef cellHarvestRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
							cellPriceMaterialFormula += "+" + cellHarvestRef;
							cellPriceMaterial.setCellFormula(cellPriceMaterialFormula);

							CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
							CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[4], 3);
							cellPriceMaterialHarvest = sheet.getRow(titleStartRow).createCell(3);
							cellPriceMaterialHarvest.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

							cellHarvest.setCellValue("2. THU HOẠCH VÀ LÒ SẤY");
							titleStartRow++;
							isCreatedMaterialHarvest = true;
						}
					} else if (actionReport.getActionType().equalsIgnoreCase("NURSERY")) {
						if (!isCreatedMaterialNursery) {
							Cell cellNursery = sheet.getRow(titleStartRow).createCell(titleStartCol);
							CellRef cellNurseryRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);
							cellPriceMaterialFormula += "+" + cellNurseryRef;
							cellPriceMaterial.setCellFormula(cellPriceMaterialFormula);

							CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
							CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[5], 3);

							cellPriceMaterialNursery = sheet.getRow(titleStartRow).createCell(3);
							cellPriceMaterialNursery.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

							cellNursery.setCellValue("3.VƯỜN ƯƠM");
							titleStartRow++;
							isCreatedMaterialNursery = true;
						}
					}
				}

				if (actionReport.getType().equalsIgnoreCase("OTHER")) {
					if (!isCreatedOther) {
						Cell cellOther = sheet.getRow(titleStartRow).createCell(titleStartCol);
						CellRef cellOtherRef = new CellRef(sheet.getSheetName(), titleStartRow, 3);

						CellRef startCellRef = new CellRef(sheet.getSheetName(), titleStartRow + 1, 3);
						CellRef endCellRef = new CellRef(sheet.getSheetName(), titleStartRow + data[6], 3);
						cellPriceOther = sheet.getRow(titleStartRow).createCell(3);
						cellPriceOther.setCellFormula("SUM(" + startCellRef + ":" + endCellRef + ")");

						cellOther.setCellValue("C. CHI PHÍ KHÁC");
						titleStartRow++;
						isCreatedOther = true;

						totalFormula += "+" + cellOtherRef;
						cellTotal.setCellFormula(totalFormula);
					}
				}

				Cell cellCultivationTitle = sheet.getRow(titleStartRow).createCell(titleStartCol);
				cellCultivationTitle.setCellValue(actionReport.getTitle());


				//Gio lao dong
				Cell cellSumHour = sheet.getRow(titleStartRow).createCell(titleStartCol + 1);
				CellRef cellSumHourRef = new CellRef(sheet.getSheetName(), titleStartRow, titleStartCol + 1);
				int colHour = 3; //D7
				String hourFormula = "";
				for (int i = 0; i < actionDetailReports.size(); i++) {
					colHour += 3;
					CellRef cellAverageHourRef = new CellRef(sheet.getSheetName(), titleStartRow, colHour);
					if (i != 0) {
						hourFormula += "," + cellAverageHourRef;
					} else {
						hourFormula += cellAverageHourRef;
					}
				}
				cellSumHour.setCellFormula("AVERAGE(" + hourFormula + ")");
				//Don gia
				Cell cellSumPrice = sheet.getRow(titleStartRow).createCell(titleStartCol + 2);
				CellRef cellSumPriceRef = new CellRef(sheet.getSheetName(), titleStartRow, titleStartCol + 2);

				int colPrice = 4; //E7
				String priceFormula = "";
				for (int i = 0; i < actionDetailReports.size(); i++) {
					colPrice += 3;
					CellRef cellAverageHourRef = new CellRef(sheet.getSheetName(), titleStartRow, colPrice);
					if (i != 0) {
						priceFormula += "," + cellAverageHourRef;
					} else {
						priceFormula += cellAverageHourRef;
					}
				}
				cellSumPrice.setCellFormula("AVERAGE(" + priceFormula + ")");
				//Thanh tien
				Cell cellSumTotal = sheet.getRow(titleStartRow).createCell(titleStartCol + 3);
				cellSumTotal.setCellFormula(cellSumHourRef + "*" + cellSumPriceRef);


				// insert value farmer cost
				titleStartCol = 5;
				for (ActionDetailReport actionDetailReport : actionReport.getActionDetailReports()) {
					Cell cellHour = sheet.getRow(titleStartRow).createCell(titleStartCol);
					cellHour.setCellValue(actionDetailReport.getQuantity());
					titleStartCol += 1;

					// So lieu qui ve
					Cell cellPriceCal = sheet.getRow(titleStartRow).createCell(titleStartCol);
					CellRef acreageCellRef = new CellRef(sheet.getSheetName(), 1, titleStartCol + 1);
					CellRef hourCellRef = new CellRef(sheet.getSheetName(), titleStartRow, titleStartCol - 1);
					cellPriceCal.setCellFormula(hourCellRef + "/" + acreageCellRef);

					titleStartCol += 1;

					Cell cellPrice = sheet.getRow(titleStartRow).createCell(titleStartCol);
					cellPrice.setCellValue(actionDetailReport.getPrice());
					titleStartCol += 1;
				}
				titleStartRow++;
			}

			//---Create header----//
			createHeaderExcelFarmerReport(workbook, sheet, actionDetailReports);

			// Style for detail cost
			for (int j = 4; j < titleStartRow; j++) {
				for (int i = 0; i < actionDetailReports.size() * 3 + 5; i++) {
					Cell cell = sheet.getRow(j).getCell(i);
					if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
						cell = sheet.getRow(j).createCell(i);
					}

					if (cellPriceLaborCultivation != null && j == cellPriceLaborCultivation.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceLaborNursery != null && j == cellPriceLaborNursery.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceLaborHarvest != null && j == cellPriceLaborHarvest.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceMaterialCultivation != null && j == cellPriceMaterialCultivation.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceMaterialNursery != null && j == cellPriceMaterialNursery.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceMaterialHarvest != null && j == cellPriceMaterialHarvest.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceOther != null && j == cellPriceOther.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceLabor != null && j == cellPriceLabor.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else if (cellPriceMaterial != null && j == cellPriceMaterial.getRow().getRowNum()) {
						cell.setCellStyle(boldStyle(styleBold, format, fontBold));
					} else {
						cell.setCellStyle(normalStyle(style, format, font));
					}
				}
			}

			//export file to client
			FileOutputStream outFile = new FileOutputStream(new File(filePath));
			workbook.write(outFile);
			outFile.close();
			responseExcelToClient(response, filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private void createHeaderExcelFarmerReport(HSSFWorkbook workbook, HSSFSheet sheet, List<ActionDetailReport> actionDetailReports) {
		DataFormat format = workbook.createDataFormat();
		HSSFCellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontName("Times New Roman");
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setDataFormat(format.getFormat("0.00"));
		style.setFont(font);
		style.setWrapText(true);
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		//F2: row = 1 ; col = 5
		int startRow = 1;
		int startCol = 5;

		for (ActionDetailReport actionDetailReport : actionDetailReports) {
			//inset row dien tich thuc te
			Cell cell = sheet.getRow(startRow).createCell(startCol);
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, startCol, startCol + 1));
			cell.setCellValue("Diện tích thực tế");

			// insert dien tich
			Cell cellAcreage = sheet.getRow(startRow).createCell(startCol + 2);
			cellAcreage.setCellValue(actionDetailReport.getAcreage());
			//insert imployee
			Cell cellEmployee = sheet.getRow(startRow + 1).createCell(startCol);
			sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 1, startCol, startCol + 2));
			cellEmployee.setCellValue(actionDetailReport.getEmployeeName());
			//insert Farmer
			Cell cellFarmer = sheet.getRow(startRow + 2).createCell(startCol);
			sheet.addMergedRegion(new CellRangeAddress(startRow + 2, startRow + 2, startCol, startCol + 2));
			cellFarmer.setCellValue(actionDetailReport.getFarmerName());
			//so gio lam
			Cell cellHour = sheet.getRow(startRow + 3).createCell(startCol);
			sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 4, startCol, startCol));
			cellHour.setCellValue("Số giờ làm");
			//so lieu qui ve
			Cell cellCal = sheet.getRow(startRow + 3).createCell(startCol + 1);
			sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 4, startCol + 1, startCol + 1));
			cellCal.setCellValue("Số liệu qui về");
			//don gia
			Cell cellPrice = sheet.getRow(startRow + 3).createCell(startCol + 2);
			sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 4, startCol + 2, startCol + 2));
			cellPrice.setCellValue("Đơn giá (đ/giờ)");
			startCol += 3;
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < actionDetailReports.size() * 3 + 5; i++) {
				Cell cell = sheet.getRow(j).getCell(i);
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					cell = sheet.getRow(j).createCell(i);
				}
				cell.setCellStyle(style);
			}
		}
	}

	private HSSFCellStyle boldStyle(HSSFCellStyle styleBold, DataFormat format, Font fontBold) {
		fontBold.setBold(true);
		fontBold.setFontName("Times New Roman");

		styleBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleBold.setDataFormat(format.getFormat("0.00"));
		styleBold.setFont(fontBold);
		styleBold.setWrapText(true);
		styleBold.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		styleBold.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return styleBold;
	}

	private HSSFCellStyle normalStyle(HSSFCellStyle style, DataFormat format, Font font) {
		font.setBold(false);
		font.setFontName("Times New Roman");
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setDataFormat(format.getFormat("0.00"));
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}

	//Tính số row cho mỗi loại dữ liệu :labor_cost, farmer, other
	private int[] getNumberOfData(List<ActionReport> actionReports) {
		int[] data = new int[]{0, 0, 0, 0, 0, 0, 0};
		for (ActionReport actionReport : actionReports) {
			if (actionReport.getType().equalsIgnoreCase("LABOR_COST")
					&& actionReport.getActionType().equalsIgnoreCase("CULTIVATION")) {
				data[0] += 1;
			}
			if (actionReport.getType().equalsIgnoreCase("LABOR_COST")
					&& actionReport.getActionType().equalsIgnoreCase("HARVEST")) {
				data[1] += 1;
			}
			if (actionReport.getType().equalsIgnoreCase("LABOR_COST")
					&& actionReport.getActionType().equalsIgnoreCase("NURSERY")) {
				data[2] += 1;
			}
			if (actionReport.getType().equalsIgnoreCase("MATERIAL")
					&& actionReport.getActionType().equalsIgnoreCase("CULTIVATION")) {
				data[3] += 1;
			}
			if (actionReport.getType().equalsIgnoreCase("MATERIAL")
					&& actionReport.getActionType().equalsIgnoreCase("HARVEST")) {
				data[4] += 1;
			}
			if (actionReport.getType().equalsIgnoreCase("MATERIAL")
					&& actionReport.getActionType().equalsIgnoreCase("NURSERY")) {
				data[5] += 1;
			}
			if (actionReport.getType().equalsIgnoreCase("OTHER")) {
				data[6] += 1;
			}
		}
		return data;
	}
}
