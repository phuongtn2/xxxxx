package vn.azteam.tabasupport.util;

import org.apache.poi.ss.usermodel.Cell;
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
 * Created 3/27/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class SeedAreaFormatListener implements AreaListener {

	private final CellRef bonusCell = new CellRef("Taba!F2");
	XlsArea area;
	PoiTransformer transformer;

	public SeedAreaFormatListener(XlsArea area) {
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
		int currentRow = targetCellRef.getRow();
		int currentCol = targetCellRef.getCol();

		int bonusCellRow = bonusCell.getRow();
		int bonusCellCol = bonusCell.getCol();

		if (bonusCellRow == currentRow && bonusCellCol != currentCol) {
			sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, bonusCellCol, currentCol));
		}
		if (currentRow == bonusCellRow + 2) {
			Cell cell = sheet.getRow(currentRow).getCell(currentCol);
			CellRef currentCellRef = new CellRef(sheet.getSheetName(), currentRow + 1, currentCol);
			cell.setCellFormula("SUM(" + currentCellRef.getCellName() + ")");
		}
	}
}