package com.ninox.focus.report;

import java.util.Date;
import com.ninox.focus.util.DateUtils;
import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.ui.Table;

public class CrystalCRMExcelExporter {
	@SuppressWarnings("static-access")
	public CrystalCRMExcelExporter(Table table, String sheetName, String headerName, boolean displayTotal) {
		ExcelExport excelExport = new ExcelExport(table, sheetName);
		DateUtils expFormat = new DateUtils();
		StringBuilder filename = new StringBuilder();
		excelExport.setDateDataFormat(expFormat.dateToString(new Date()));
		excelExport.setUseTableFormatPropertyValue(false);
		excelExport.excludeCollapsedColumns();
		if (displayTotal) {
			excelExport.setDisplayTotals(true);
		} else {
			excelExport.setDisplayTotals(false);
		}
		excelExport.setRowHeaders(false);
		if (sheetName != "") {
			filename.append(sheetName).append("-");
		}
		filename.append(expFormat.dateToStringTime(new Date()));
		excelExport.setReportTitle(headerName + " " + filename.toString());
		excelExport.setExportFileName(filename.append(".ods").toString());
		excelExport.export();
	}
}
