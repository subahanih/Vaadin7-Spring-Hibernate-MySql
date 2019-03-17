package com.ninox.focus.view.rpt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.ninox.focus.domain.EmployeeDM;
import com.ninox.focus.exception.FocusException;
import com.ninox.focus.exception.FocusException.NoDataFoundException;
import com.ninox.focus.report.CrystalCRMExcelExporter;
import com.ninox.focus.service.EmployeeService;
import com.ninox.focus.view.components.FocusAddEditHLayout;
import com.ninox.focus.view.components.FocusButton;
import com.ninox.focus.view.components.FocusComboBox;
import com.ninox.focus.view.components.FocusPanelGenerator;
import com.ninox.focus.view.components.FocusTextField;
import com.ninox.focus.view.util.BaseReportUI;
import com.ninox.focus.view.util.SpringContextHelper;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class ReportEmployee extends BaseReportUI {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ReportEmployee.class);
	private EmployeeService serviceEmployee = (EmployeeService) SpringContextHelper.getBean("employee");
	private BeanItemContainer<EmployeeDM> beanEmployee = null;
	// Search Control Layout
	private HorizontalLayout hlSearchLayout = new FocusAddEditHLayout();
	private HorizontalLayout hlPlanSerachComponents;
	private PopupDateField pdfStartDate, pdfEndDate;
	// Plan Panel Components
	private FormLayout flSearchPlan1, flSearchPlan2, flSearchPlan3, flSearchPlan4, flSearchPlan5;
	private ComboBox cbEmployeetatus;
	private TextField tfEmployeeCode, tfFirstName, tfLastName;
	private int recordCnt = 0;
	private String userName, companyName, userRole;
	private StringBuffer headerName;
	private Button btnExcel = new FocusButton("Excel", "downloadexcel", this);
	private Long companyId;
	
	public ReportEmployee() {
		userName = UI.getCurrent().getSession().getAttribute("userName").toString();
		companyId = Long.valueOf(UI.getCurrent().getSession().getAttribute("companyId").toString());
		userRole = UI.getCurrent().getSession().getAttribute("userRole").toString();
		companyName = UI.getCurrent().getSession().getAttribute("companyName").toString();
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > "
				+ "Inside ApprovalSchema() constructor");
		buildView();
	}
	
	public void buildView() {
		pdfStartDate = new PopupDateField("Start Date");
		pdfStartDate.setDateFormat("dd/MM/yyyy");
		pdfEndDate = new PopupDateField("End Date");
		pdfEndDate.setDateFormat("dd/MM/yyyy");
		tfEmployeeCode = new FocusTextField("Employee Code");
		tfFirstName = new FocusTextField("First Name");
		tfLastName = new FocusTextField("Last Name");
		cbEmployeetatus = new FocusComboBox("Status", "PlanSequenceDM", "Seq_Status");
		headerName = new StringBuffer();
		headerName.append(companyName.toString() + "\n");
		assembleSearchLayout();
		hlSrchContainer.addComponent(FocusPanelGenerator.createPanel(hlSearchLayout));
		resetFields();
		loadSrchRslt();
		hlCmdBtnLayout.addComponent(btnExcel);
		hlCmdBtnLayout.setComponentAlignment(btnExcel, Alignment.MIDDLE_RIGHT);
		btnExcel.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				excelReport();
			}
		});
	}
	
	@Override
	protected void searchDetails() throws NoDataFoundException {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + " Invoking search");
		loadSrchRslt();
		if (recordCnt == 0) {
			logger.info("Company ID : " + companyId + " | User Name : " + companyId + " > "
					+ "No data for the search. throwing ERPException.NoDataFoundException");
			throw new FocusException.NoDataFoundException();
		} else {
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
		}
	}
	
	@Override
	protected void resetSearchDetails() {
		System.out.println("resetSearchDetails()");
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > "
				+ "Resetting search fields and reloading the result");
		// reset the field valued to default
		cbEmployeetatus.setValue(cbEmployeetatus.getItemIds().iterator().next());
		tfEmployeeCode.setValue("");
		tfFirstName.setValue("");
		tfLastName.setValue("");
		pdfStartDate.setValue(null);
		pdfEndDate.setValue(null);
		lblNotification.setIcon(null);
		lblNotification.setCaption("");
		// reload the search using the defaults
		loadSrchRslt();
	}
	
	@Override
	protected void resetFields() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Resetting the UI controls");
		cbEmployeetatus.setValue(cbEmployeetatus.getItemIds().iterator().next());
	}
	
	protected void assembleSearchLayout() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Assembling search layout");
		// Remove all components in User Search Layout
		hlSearchLayout.removeAllComponents();
		flSearchPlan1 = new FormLayout();
		flSearchPlan2 = new FormLayout();
		flSearchPlan3 = new FormLayout();
		flSearchPlan4 = new FormLayout();
		flSearchPlan5 = new FormLayout();
		flSearchPlan1.addComponent(tfFirstName);
		flSearchPlan1.addComponent(tfLastName);
		flSearchPlan1.addComponent(tfEmployeeCode);
		flSearchPlan3.addComponent(pdfStartDate);
		flSearchPlan3.addComponent(pdfEndDate);
		flSearchPlan4.addComponent(cbEmployeetatus);
		hlPlanSerachComponents = new HorizontalLayout();
		hlPlanSerachComponents.addComponent(flSearchPlan1);
		hlPlanSerachComponents.addComponent(flSearchPlan2);
		hlPlanSerachComponents.addComponent(flSearchPlan3);
		hlPlanSerachComponents.addComponent(flSearchPlan4);
		hlPlanSerachComponents.addComponent(flSearchPlan5);
		hlPlanSerachComponents.setMargin(true);
		hlPlanSerachComponents.setSpacing(true);
		hlSearchLayout.addComponent(hlPlanSerachComponents);
		hlSearchLayout.setComponentAlignment(hlPlanSerachComponents, Alignment.MIDDLE_LEFT);
	}
	
	protected void loadSrchRslt() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Loading Search...");
		tblMstScrSrchRslt.removeAllItems();
		tblMstScrSrchRslt.setPageLength(16);
		List<EmployeeDM> employeeList = new ArrayList<EmployeeDM>();
		employeeList = serviceEmployee.getEmployeeReportList(null, tfEmployeeCode.getValue(), tfFirstName.getValue(),
				tfLastName.getValue(), null, null, (String) cbEmployeetatus.getValue(),
				null, (Date) pdfStartDate.getValue(),
				(Date) pdfEndDate.getValue());
		recordCnt = employeeList.size();
		beanEmployee = new BeanItemContainer<EmployeeDM>(EmployeeDM.class);
		beanEmployee.addAll(employeeList);
		tblMstScrSrchRslt.setContainerDataSource(beanEmployee);
		tblMstScrSrchRslt.setVisibleColumns(new Object[] { "employeeId", "employeeCode", "firstName", "lastName",
				"primaryPhone", "doj", "branchName", "empStatus" });
		tblMstScrSrchRslt.setColumnHeaders(new String[] { "Ref Id", "Employee Code", "First Name", "Last Name",
				"Mobile No", "Date of Join", "Branch Name", "Status" });
		tblMstScrSrchRslt.setColumnAlignment("employeeId", Align.RIGHT);
		tblMstScrSrchRslt.setColumnFooter("empStatus", "No. of Records: " + recordCnt);
	}
	
	protected void excelReport() {
		new CrystalCRMExcelExporter(tblMstScrSrchRslt, "Employee_Details", headerName.toString(), false);
	}
	
}
