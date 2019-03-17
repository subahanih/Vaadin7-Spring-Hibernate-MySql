package com.ninox.focus.view.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class Report {
	@SuppressWarnings("rawtypes")
	HashMap hm = null;
	Connection con = null;
	String reportName;
	MyStringRandomGen random = new MyStringRandomGen();
	final String filename = random.generateRandomString();
	private static Logger logger = Logger.getLogger(Report.class);
	
	public Report() {
	}
	
	@SuppressWarnings("rawtypes")
	public Report(HashMap map) {
		this.hm = map;
	}
	
	@SuppressWarnings("rawtypes")
	public Report(HashMap map, Connection con) {
		this.hm = map;
		this.con = con;
	}
	
	public void setReportName(String rptName) {
		this.reportName = rptName;
	}
	
	@SuppressWarnings({ "deprecation", "serial" })
	public void callReport(final String basepath, final String header) {
		JasperPrint jasperPrint = generateReport();
		try {
			System.out.println("Test1---->" + jasperPrint);
			System.out.println("Testpath---->" + basepath + "/META-INF/view/" + filename);
			JasperExportManager.exportReportToPdfFile(jasperPrint, basepath + "/META-INF/view/" + filename);
			Window window = new Window();
			window.setIcon(new ThemeResource("img/Reports.png"));
			window.setWidth("1000");
			window.setHeight("800");
			window.setCaption(header);
			window.center();
			StreamSource s = new StreamResource.StreamSource() {
				@Override
				public FileInputStream getStream() {
					try {
						File f = new File(basepath + "/META-INF/view/" + filename);
						FileInputStream fis = new FileInputStream(f);
						return fis;
					}
					catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			};
			StreamResource r = new StreamResource(s, filename + ".pdf");
			Embedded e = new Embedded();
			e.setWidth("100%");
			e.setHeight("100%");
			e.setType(Embedded.TYPE_BROWSER);
			r.setMIMEType("application/pdf");
			e.setSource(r);
			window.setContent(e);
			UI.getCurrent().addWindow(window);
		}
		catch (Exception e) {
			logger.error("Error 2>", e);
			e.printStackTrace();
		}
	}
	
	public void callConnectionLessReport() {
		JasperPrint jasperPrint = generateEmptyDataSourceReport();
		try {
			System.out.println("Test2---->" + jasperPrint);
			JasperViewer.viewReport(jasperPrint);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeReport() {
	}
	
	/** this method will call the report from data source */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JasperPrint generateReport() {
		try {
			if (con == null) {
				try {
					con = Database.getConnection();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			JasperPrint jasperPrint = null;
			if (hm == null) {
				hm = new HashMap();
			}
			try {
				/**
				 * You can also test this line if you want to display report from any absolute path other than the
				 * project root path
				 */
				jasperPrint = JasperFillManager.fillReport(reportName + ".jasper", hm, con);
			}
			catch (JRException e) {
				logger.error("Error>3", e);
				e.printStackTrace();
			}
			return jasperPrint;
		}
		catch (Exception ex) {
			logger.error("Error>4", ex);
			ex.printStackTrace();
			return null;
		}
	}
	
	/** call this method when your report has an empty data source */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JasperPrint generateEmptyDataSourceReport() {
		try {
			JasperPrint jasperPrint = null;
			if (hm == null) {
				hm = new HashMap();
			}
			try {
				jasperPrint = JasperFillManager.fillReport(reportName + ".jasper", hm, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, filename);
			}
			catch (JRException e) {
				e.printStackTrace();
			}
			return jasperPrint;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
