package com.ninox.focus.report;

import java.util.Date;
import com.vaadin.addon.tableexport.CsvExport;
import com.vaadin.ui.Table;

public class iERPCSVExporter {
	public iERPCSVExporter(Table table, String sheetName, String headerName) {
		CsvExport excelExport = new CsvExport(table, sheetName);
		StringBuilder filename = new StringBuilder();
		excelExport.setUseTableFormatPropertyValue(false);
		excelExport.excludeCollapsedColumns();
		excelExport.setDisplayTotals(true);
		excelExport.setRowHeaders(false);
		if (sheetName != "") {
			filename.append(sheetName).append("-");
		}
		filename.append(new Date());
		excelExport.setReportTitle(headerName + " " + filename.toString());
		excelExport.setExportFileName(filename.append(".csv").toString());
		excelExport.export();
	}
}
