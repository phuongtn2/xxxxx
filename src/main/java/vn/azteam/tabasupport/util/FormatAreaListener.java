package vn.azteam.tabasupport.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jxls.area.XlsArea;
import org.jxls.common.AreaListener;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;

/**
 * Package vn.azteam.tabasupport.util
 * Created 1/10/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class FormatAreaListener implements AreaListener {

	private final CellRef bonusCell = new CellRef("Taba!B6");
	XlsArea area;
	PoiTransformer transformer;

	public FormatAreaListener(XlsArea area) {
		this.area = area;
		transformer = (PoiTransformer) area.getTransformer();
	}

	@Override
	public void beforeApplyAtCell(CellRef cellRef, Context context) {

	}

	@Override
	public void afterApplyAtCell(CellRef cellRef, Context context) {

	}

	@Override
	public void beforeTransformCell(CellRef cellRef, CellRef cellRef1, Context context) {

	}

	@Override
	public void afterTransformCell(CellRef cellRef, CellRef targetCellRef, Context context) {
		Workbook workbook = transformer.getWorkbook();
		Sheet sheet = workbook.getSheet(targetCellRef.getSheetName());
		if (bonusCell.equals(cellRef)) {

			Row currentRow = sheet.getRow(targetCellRef.getRow() - 1);
			Cell currentCell = currentRow.getCell(targetCellRef.getCol());
			String valueCurrentCell = currentCell.getStringCellValue();

			Row targetRow = sheet.getRow(targetCellRef.getRow());
			Cell targetCell = targetRow.getCell(targetCellRef.getCol());
			String valueTargetCell = targetCell.getStringCellValue();

			if (valueCurrentCell.equalsIgnoreCase(valueTargetCell)) {
				sheet.addMergedRegion(new CellRangeAddress(targetCellRef.getRow() - 1, targetCellRef.getRow(), targetCellRef.getCol(), targetCellRef.getCol()));
			}

		}
	}
}
