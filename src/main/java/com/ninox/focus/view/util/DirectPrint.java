package com.ninox.focus.view.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrintManager;
import com.vaadin.server.VaadinService;

public class DirectPrint {
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void print() throws JRException, SQLException {
		Connection connection = null;
		Statement statement = null;
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		//
		connection = Database.getConnection();
		statement = connection.createStatement();
		HashMap parameterMap = new HashMap();
		System.out.println("customerId-->" + 1);
		parameterMap.put("customerId", 1);
		Report rpt = new Report(parameterMap, connection);
		rpt.setReportName(basepath + "/META-INF/view/Certificate");
		// rpt.callReport(basepath, "Certificate Preview");
		//
		long start = System.currentTimeMillis();
		/*
		 * PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		 * printRequestAttributeSet.add(MediaSizeName.ISO_A4); PrintServiceAttributeSet printServiceAttributeSet = new
		 * HashPrintServiceAttributeSet(); printServiceAttributeSet.add(new PrinterName( "Epson Stylus 820 ESC/P 2",
		 * null)); printServiceAttributeSet.add(new PrinterName("hp LaserJet 1320 PCL 6", null));
		 * printServiceAttributeSet.add(new PrinterName("PDFCreator", null)); JRPrintServiceExporter exporter = new
		 * JRPrintServiceExporter(); exporter.setExporterInput(new SimpleExporterInput(basepath +
		 * "/META-INF/view/Certificate")); SimplePrintServiceExporterConfiguration configuration = new
		 * SimplePrintServiceExporterConfiguration();
		 * configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
		 * configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
		 * configuration.setDisplayPageDialog(false); configuration.setDisplayPrintDialog(true);
		 * exporter.setConfiguration(configuration); exporter.exportReport();
		 */
		JasperPrintManager.printPage(basepath + "/META-INF/view/Certificate", 1, false);
		System.err.println("Printing time : " + (System.currentTimeMillis() - start));
	}
}