package com.ninox.focus.report;

import java.util.Date;
import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.ui.Table;

public class iERPExcelExporter {
	public iERPExcelExporter(Table table, String sheetName, String headerName) {
		ExcelExport excelExport = new ExcelExport(table, sheetName);
		StringBuilder filename = new StringBuilder();
		excelExport.setUseTableFormatPropertyValue(false);
		excelExport.excludeCollapsedColumns();
		excelExport.setDisplayTotals(false);
		excelExport.setRowHeaders(false);
		excelExport.setDisplayTotals(true);
		if (sheetName != "") {
			filename.append(sheetName).append("-");
		}
		filename.append(new Date());
		excelExport.setReportTitle(headerName + " " + filename.toString());
		excelExport.setExportFileName(filename.append(".xls").toString());
		excelExport.export();
	}
}
