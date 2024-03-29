package com.ninox.focus.report;

import com.vaadin.data.Container;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class CSVExporter extends Exporter {
	public CSVExporter() {
		super();
	}
	
	public CSVExporter(Table table) {
		super(table);
	}
	
	public CSVExporter(Container container, Object[] visibleColumns) {
		super(container, visibleColumns);
	}
	
	public CSVExporter(Container container) {
		super(container);
	}
	
	@Override
	protected FileBuilder createFileBuilder(Container container) {
		return new CSVFileBuilder(container);
	}
	
	@Override
	protected String getDownloadFileName() {
		if (downloadFileName == null) {
			return "exported-csv.csv";
		}
		if (downloadFileName.endsWith(".csv")) {
			return downloadFileName;
		} else {
			return downloadFileName + ".csv";
		}
	}
}
