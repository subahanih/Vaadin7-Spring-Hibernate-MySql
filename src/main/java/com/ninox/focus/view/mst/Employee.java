package com.ninox.focus.view.mst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.ninox.focus.domain.EmployeeDM;
import com.ninox.focus.domain.EmployeeDocumentDM;
import com.ninox.focus.domain.SlnoGenDM;
import com.ninox.focus.exception.FocusException;
import com.ninox.focus.exception.FocusException.NoDataFoundException;
import com.ninox.focus.exception.FocusException.SaveException;
import com.ninox.focus.exception.FocusException.ValidationException;
import com.ninox.focus.service.EmployeeDocumentService;
import com.ninox.focus.service.EmployeeService;
import com.ninox.focus.service.SlnoGenService;
import com.ninox.focus.view.components.FocusAddEditHLayout;
import com.ninox.focus.view.components.FocusButton;
import com.ninox.focus.view.components.FocusComboBox;
import com.ninox.focus.view.components.FocusFailureNotification;
import com.ninox.focus.view.components.FocusPanelGenerator;
import com.ninox.focus.view.components.FocusPopupDateField;
import com.ninox.focus.view.components.FocusSuccessNotification;
import com.ninox.focus.view.components.FocusTable;
import com.ninox.focus.view.components.FocusTextField;
import com.ninox.focus.view.util.BaseUI;
import com.ninox.focus.view.util.DateUtil;
import com.ninox.focus.view.util.FocusConstants;
import com.ninox.focus.view.util.SpringContextHelper;
import com.ninox.focus.view.util.UploadDocumentUI;
import com.ninox.focus.view.util.UploadUI;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Employee extends BaseUI {
	private static final long serialVersionUID = 8624818597477342436L;
	private EmployeeService serviceEmployee = (EmployeeService) SpringContextHelper.getBean("employee");
	private SlnoGenService serviceSlnoGen = (SlnoGenService) SpringContextHelper.getBean("slNoGen");
	private EmployeeDocumentService serviceEmployeeDocumentService = (EmployeeDocumentService) SpringContextHelper
			.getBean("empDocument");
	private List<EmployeeDocumentDM> employeeDocList;
	private BeanItemContainer<EmployeeDocumentDM> employeeDocBean = null;
	// form layout for input controls
	private FormLayout flFirstName, flEmployeeCode, flStatus;
	// Parent layout for all the input controls
	private FocusAddEditHLayout hlUserInputLayout = new FocusAddEditHLayout();
	// Search Control Layout
	private FocusAddEditHLayout hlSearchLayout;
	private FocusTextField tfFirstname, tflastname, tfPhoneNo, tfEmailId, tfempCode, tfDocName, tfSalary;
	private FocusComboBox cbGender, cbStatus, cbTitle, cbDocStatus, cbEmpRole;
	private Table tblEmployeDocument;
	public Button btndelete, btnAddDtl;
	private CheckBox chlogAcs;
	private FocusPopupDateField pdDOB, pdDoJ;
	private TabSheet tabEmployee;
	// local variables declaration
	private Long loginCompanyId, employeeId = 0L;
	private int recordCnt = 0, recordDocCnt;
	private String loginUserName, loginUserRole;
	private Logger logger = Logger.getLogger(Employee.class);
	private BeanItemContainer<EmployeeDM> beanEmployee = null;
	private HorizontalLayout hlimage = new HorizontalLayout();
	private VerticalLayout vlDocument = new VerticalLayout();
	public static boolean filevalue = false;
	
	public Employee() {
		loginUserName = UI.getCurrent().getSession().getAttribute("userName").toString();
		loginCompanyId = Long.valueOf(UI.getCurrent().getSession().getAttribute("companyId").toString());
		loginUserRole = UI.getCurrent().getSession().getAttribute("userRole").toString();
		buildview();
	}
	
	private void buildview() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Painting employee UI");
		chSelectAll.setVisible(false);
		btnDownload.setVisible(false);
		btnEdit.setVisible(true);
		hlCmdBtnLayout.setExpandRatio(btnEdit, 1);
		tfFirstname = new FocusTextField("First Name");
		tflastname = new FocusTextField("Last Name");
		tfempCode = new FocusTextField("Employee Code");
		tfPhoneNo = new FocusTextField("Phone No");
		tfEmailId = new FocusTextField("EmailId");
		tfSalary = new FocusTextField("Salary");
		cbEmpRole = new FocusComboBox("Designation", "EmployeeDM", "EmployeeRole");
		//
		tfFirstname.setMaxLength(30);
		tflastname.setMaxLength(30);
		tfempCode.setMaxLength(15);
		tfPhoneNo.setMaxLength(15);
		tfEmailId.setMaxLength(45);
		tfSalary.setMaxLength(10);
		//
		chlogAcs = new CheckBox("Login Access Y/N");
		cbTitle = new FocusComboBox("Title", "EmployeeDM", "EmployeeSalut");
		cbGender = new FocusComboBox("Gender", "EmployeeDM", "Gender");
		cbGender.setWidth("150");
		cbStatus = new FocusComboBox("Status", "EmployeeDM", "EmployeeStatus");
		pdDOB = new FocusPopupDateField("Date of Birth");
		pdDOB.setWidth("170");
		pdDOB.setImmediate(true);
		pdDoJ = new FocusPopupDateField("Date of Join");
		pdDoJ.setWidth("170");
		pdDoJ.setImmediate(true);
		
		tfPhoneNo.setWidth("149");
		// Document Components
		btnAddDtl = new FocusButton("Add", "add");
		btndelete = new FocusButton("Delete", "delete");
		tfDocName = new FocusTextField("Document Name");
		tfDocName.setMaxLength(40);
		cbDocStatus = new FocusComboBox("Status", "EmployeeDocumentDM", "DocumentStatus");
		tblEmployeDocument = new FocusTable();
		tblEmployeDocument.setPageLength(6);
		tblEmployeDocument.setWidth("500px");
		tblEmployeDocument.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if (tblEmployeDocument.isSelected(event.getItemId())) {
					tblEmployeDocument.setImmediate(true);
					btnAddDtl.setCaption("Add");
					btnAddDtl.setStyleName("add");
					resetFields();
					btndelete.setEnabled(false);
				} else {
					((AbstractSelect) event.getSource()).select(event.getItemId());
					btnAddDtl.setCaption("Update");
					btnAddDtl.setStyleName("add");
					editEmployeeDocument();
					btnAddDtl.setEnabled(true);
					btndelete.setEnabled(true);
				}
			}
		});
		btnAddDtl.setVisible(true);
		btnAddDtl.addClickListener(new ClickListener() {
			// Click Listener for Add and Update
			private static final long serialVersionUID = 6551953728534136363L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if (!validateDocumentDetails()) {
						try {
							saveEmployeeDocumentDtl();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				catch (ValidationException e) {
					e.printStackTrace();
				}
			}
		});
		btndelete.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6551953728534136363L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (btndelete == event.getButton()) {
					deleteDetails();
				}
			}
		});
		// build search layout
		hlSearchLayout = new FocusAddEditHLayout();
		assembleSearchLayout();
		hlSrchContainer.addComponent(FocusPanelGenerator.createPanel(hlSearchLayout));
		resetFields();
		loadSrchRslt();
		loadDocumentSrchRslt();
	}
	
	private void loadSrchRslt() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Loading Search...");
		tblMstScrSrchRslt.setSelectable(true);
		tblMstScrSrchRslt.removeAllItems();
		List<EmployeeDM> employeeList = new ArrayList<EmployeeDM>();
		employeeList = new ArrayList<EmployeeDM>();
		employeeList = serviceEmployee.getEmployeeList(null, tfempCode.getValue(), tfFirstname.getValue(), loginCompanyId,
				null, "Active", null);
		recordCnt = employeeList.size();
		beanEmployee = new BeanItemContainer<EmployeeDM>(EmployeeDM.class);
		beanEmployee.addAll(employeeList);
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Got the Employee result set");
		tblMstScrSrchRslt.setContainerDataSource(beanEmployee);
		tblMstScrSrchRslt.setVisibleColumns(new Object[] { "employeeId", "employeeCode", "fullName", "empRole", 
				"primaryPhone", "employeeSalary", "gender", "empStatus", "updatedDate", "updatedBy" });
		tblMstScrSrchRslt.setColumnHeaders(new String[] { "Ref.Id", "Emp.Code", "Employee Name", "Designation", 
				"Phone Number", "Salary", "Gender", "Status", "Updated Date", "Updated By" });
		tblMstScrSrchRslt.setColumnAlignment("employeeId", Align.RIGHT);
		tblMstScrSrchRslt.setColumnFooter("updatedBy", "No.of Records : " + recordCnt);
	}
	
	public void loadDocumentSrchRslt() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Search Parameters are "
				+ loginCompanyId);
		tblEmployeDocument.removeAllItems();
		employeeDocList = new ArrayList<EmployeeDocumentDM>();
		employeeDocList = serviceEmployeeDocumentService.getEmployeeDocumentList(null, employeeId, "Active");
		recordDocCnt = employeeDocList.size();
		employeeDocBean = new BeanItemContainer<EmployeeDocumentDM>(EmployeeDocumentDM.class);
		employeeDocBean.addAll(employeeDocList);
		tblEmployeDocument.setContainerDataSource(employeeDocBean);
		tblEmployeDocument.setVisibleColumns(new Object[] { "empDocumentId", "documentName", "documentStatus",
				"updatedDate" });
		tblEmployeDocument.setColumnHeaders(new String[] { "Ref.Id", "Document Name", "Status", "Last Updated" });
		tblEmployeDocument.setColumnAlignment("custDocumentId", Align.RIGHT);
		tblEmployeDocument.setColumnFooter("updatedDate", "No.of Records : " + recordDocCnt);
	}
	
	private void assembleSearchLayout() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Assembling search layout");
		// Remove all components in User Input Layout
		hlSearchLayout.removeAllComponents();
		tfempCode.setRequired(false);
		tfFirstname.setRequired(false);
		tblMstScrSrchRslt.setPageLength(15);
		// Add components for Search Layout
		flEmployeeCode = new FormLayout();
		flFirstName = new FormLayout();
		flStatus = new FormLayout();
		flEmployeeCode.addComponent(tfempCode);
		flFirstName.addComponent(tfFirstname);
		flStatus.addComponent(cbStatus);
		hlSearchLayout.addComponent(flEmployeeCode);
		hlSearchLayout.addComponent(flFirstName);
		hlSearchLayout.addComponent(flStatus);
		hlSearchLayout.setSpacing(true);
		hlSearchLayout.setMargin(true);
		hlSearchLayout.setSizeUndefined();
	}
	
	@Override
	protected void searchDetails() throws NoDataFoundException {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + " Invoking search");
		loadSrchRslt();
		if (recordCnt == 0) {
			logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > "
					+ "No data for the search. throwing ERPException.NoDataFoundException");
			throw new FocusException.NoDataFoundException();
		} else {
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
		}
	}
	
	@Override
	protected void resetSearchDetails() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > "
				+ "Resetting search fields and reloading the result");
		tfFirstname.setValue("");
		tfempCode.setValue("");
		lblNotification.setIcon(null);
		lblNotification.setCaption("");
		cbStatus.setValue(cbStatus.getItemIds().iterator().next());
		loadSrchRslt();
	}
	
	@Override
	protected void addDetails() {
		resetFields();
		hlUserInputLayout.removeAllComponents();
		assembleUserInputLayout();
		tblMstScrSrchRslt.setPageLength(8);
		hlUserIPContainer.addComponent(hlUserInputLayout);
		List<SlnoGenDM> slnoList = serviceSlnoGen.getSlnoGenList(null, loginCompanyId, true, "EmployeeCode");
		for (SlnoGenDM slnoObj : slnoList) {
			if (slnoObj.getAutogenYN().equals(true)) {
				tfempCode.setReadOnly(true);
			} else {
				tfempCode.setReadOnly(false);
			}
		}
		employeeId = 0L;
		loadDocumentSrchRslt();
	}
	
	private void assembleUserInputLayout() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Assembling User Input layout");
		// Remove all components in Search Layout
		hlUserInputLayout.removeAllComponents();
		// Add components for User Input Layout
		FormLayout fl1 = new FormLayout();
		FormLayout fl2 = new FormLayout();
		FormLayout fl3 = new FormLayout();
		FormLayout fl4 = new FormLayout();
		//
		fl1.addComponent(tfempCode);
		fl1.addComponent(cbTitle);
		fl1.addComponent(tfFirstname);
		fl1.addComponent(tflastname);
		fl1.setSpacing(true);
		//
		fl2.addComponent(pdDOB);
		fl2.addComponent(cbGender);
		fl2.addComponent(tfPhoneNo);
		fl2.addComponent(tfEmailId);
		fl2.setSpacing(true);
		//
		fl3.addComponent(cbEmpRole);
		fl3.addComponent(pdDoJ);
		fl3.addComponent(tfSalary);
		fl3.addComponent(cbStatus);
		fl3.addComponent(chlogAcs);
		fl3.setSpacing(true);
		//
		fl4.addComponent(hlimage);
		HorizontalLayout hlEmployeeDetails = new HorizontalLayout();
		hlEmployeeDetails.addComponent(fl1);
		hlEmployeeDetails.addComponent(fl2);
		hlEmployeeDetails.addComponent(fl3);
		hlEmployeeDetails.addComponent(fl4);
		hlEmployeeDetails.setSpacing(true);
		hlEmployeeDetails.setMargin(true);
		// Document Components
		VerticalLayout img = new VerticalLayout();
		img.addComponent(vlDocument);
		img.setSpacing(true);
		img.setSizeFull();
		new UploadDocumentUI(vlDocument);
		//
		resetDocumentFields();
		FormLayout flDoc1 = new FormLayout();
		FormLayout flDoc2 = new FormLayout();
		FormLayout flDoc3 = new FormLayout();
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(btnAddDtl);
		hl.addComponent(btndelete);
		//
		flDoc1.addComponent(tfDocName);
		flDoc1.addComponent(cbDocStatus);
		flDoc1.addComponent(hl);
		flDoc1.setSpacing(true);
		flDoc2.addComponent(img);
		flDoc2.setWidth("180px");
		flDoc3.addComponent(tblEmployeDocument);
		//
		HorizontalLayout hldtl = new HorizontalLayout();
		hldtl.addComponent(flDoc1);
		hldtl.addComponent(flDoc2);
		hldtl.addComponent(flDoc3);
		hldtl.setSpacing(true);
		hldtl.setMargin(true);
		flDoc3.setMargin(false);
		flDoc3.setSpacing(false);
		tabEmployee = new TabSheet();
		tabEmployee.setWidth("100%");
		tabEmployee.setHeight("240px");
		tabEmployee.addTab(hlEmployeeDetails, "Employee Details", null);
		tabEmployee.addTab(hldtl, "Employee Documents", null);
		// add the form layouts into user input layout
		hlUserInputLayout.addComponent(tabEmployee);
		hlUserInputLayout.setWidth("100%");
	}
	
	@Override
	protected void editDetails() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Updating existing record...");
		tblMstScrSrchRslt.setPageLength(8);
		hlUserIPContainer.removeAllComponents();
		hlUserIPContainer.addComponent(hlUserInputLayout);
		assembleUserInputLayout();
		resetFields();
		editEmployee();
	}
	
	private void editEmployee() {
		hlUserInputLayout.setVisible(true);
		Item sltedRcd = tblMstScrSrchRslt.getItem(tblMstScrSrchRslt.getValue());
		EmployeeDM editEmployeelist = beanEmployee.getItem(tblMstScrSrchRslt.getValue()).getBean();
		employeeId = editEmployeelist.getEmployeeId();
		tfempCode.setReadOnly(false);
		tfempCode.setValue((String) sltedRcd.getItemProperty("employeeCode").getValue());
		tfempCode.setReadOnly(true);
		tfFirstname.setValue(sltedRcd.getItemProperty("firstName").getValue().toString());
		tflastname.setValue(sltedRcd.getItemProperty("lastName").getValue().toString());
		cbEmpRole.setValue(sltedRcd.getItemProperty("empRole").getValue());
		tfPhoneNo.setValue((String) sltedRcd.getItemProperty("primaryPhone").getValue());
		tfEmailId.setValue((String) sltedRcd.getItemProperty("primaryEmail").getValue());
		tfSalary.setValue(sltedRcd.getItemProperty("employeeSalary").getValue().toString());
		String stCode = sltedRcd.getItemProperty("empStatus").getValue().toString();
		cbStatus.setValue(stCode);
		cbGender.setValue(sltedRcd.getItemProperty("gender").getValue().toString());
		cbTitle.setValue(sltedRcd.getItemProperty("employeeSalut").getValue().toString());
		if (editEmployeelist.getDob() != null) {
			pdDOB.setValue(editEmployeelist.getDob());
		}
		if (editEmployeelist.getDoj() != null) {
			pdDoJ.setValue(editEmployeelist.getDoj());
		}
		if (editEmployeelist.getLoginaccessYn() == true) {
			chlogAcs.setValue(true);
		} else {
			chlogAcs.setValue(false);
		}
		if (editEmployeelist.getEmpPhoto() != null) {
			hlimage.removeAllComponents();
			byte[] myimage = (byte[]) editEmployeelist.getEmpPhoto();
			UploadUI test = new UploadUI(hlimage);
			test.dispayImage(myimage, editEmployeelist.getFirstName());
		} else {
			new UploadUI(hlimage);
		}
		loadDocumentSrchRslt();
	}
	
	@Override
	protected void validateDetails() throws ValidationException {
		tfempCode.setComponentError(null);
		tfFirstname.setComponentError(null);
		tflastname.setComponentError(null);
		pdDOB.setComponentError(null);
		pdDoJ.setComponentError(null);
		tfEmailId.setComponentError(null);
		tfSalary.setComponentError(null);
		tfPhoneNo.setComponentError(null);
		tfEmailId.setComponentError(null);
		cbEmpRole.setComponentError(null);
		cbTitle.setComponentError(null);
		cbGender.setComponentError(null);
		Boolean errorFlag = false;
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Validating Data ");
		if (serviceSlnoGen.getSlnoGenList(null, loginCompanyId, true, null).size() > 0) {
			errorFlag = false;
		} else {
			if (tfempCode.getValue().equals("") && tfempCode.getValue().trim().length() < 0) {
				tfempCode.setComponentError(new UserError("Enter Employee code"));
				errorFlag = true;
			} else {
				errorFlag = false;
			}
		}
		if ((tfFirstname.getValue() == null) || tfFirstname.getValue().trim().length() == 0) {
			tfFirstname.setComponentError(new UserError("Enter first name"));
			errorFlag = true;
		}
		if ((tflastname.getValue() == null) || tfFirstname.getValue().trim().length() == 0) {
			tflastname.setComponentError(new UserError("Enter last name"));
			errorFlag = true;
		}
		if ((tfSalary.getValue() == null) || tfSalary.getValue().trim().length() == 0) {
			tflastname.setComponentError(new UserError("Enter employee salary"));
			errorFlag = true;
		}
		if (pdDoJ.getValue() == null) {
			pdDoJ.setComponentError(new UserError("Enter date of join"));
			errorFlag = true;
		}
		String emailSeq = tfEmailId.getValue().toString();
		if (!emailSeq.contains("@") || !emailSeq.contains(".")) {
			tfEmailId.setComponentError(new UserError("Enter valid email-Id"));
			errorFlag = true;
		}
		if (cbEmpRole.getValue() == null) {
			cbEmpRole.setComponentError(new UserError("Select Employee Designation"));
			errorFlag = true;
		}
		if (cbTitle.getValue() == null) {
			cbTitle.setComponentError(new UserError("Select Employee Title"));
			errorFlag = true;
		}
		if (cbGender.getValue() == null) {
			cbGender.setComponentError(new UserError("Select gender"));
			errorFlag = true;
		}
		if (tfPhoneNo.getValue().toString() == null) {
			tfPhoneNo.setComponentError(new UserError("Enter phone number"));
			errorFlag = true;
		} else if (tfPhoneNo.getValue() != null) {
			if (!tfPhoneNo.getValue().matches("^\\+?[0-9. ()-]{10,25}$")) {
				tfPhoneNo.setComponentError(new UserError("Enter valid phone number"));
				errorFlag = true;
			}
		}
		if (pdDOB.getValue() == null) {
			pdDOB.setComponentError(new UserError("Enter date of birth"));
			errorFlag = true;
		} else if (pdDOB.getValue().after(new Date()) || pdDOB.getValue().equals(new Date())) {
			pdDOB.setComponentError(new UserError("Enter valid date"));
			errorFlag = true;
		}
		if (pdDoJ.getValue() == null) {
			pdDoJ.setComponentError(new UserError("Enter date of join"));
			errorFlag = true;
		} else if (pdDoJ.getValue().before(pdDOB.getValue())) {
			pdDoJ.setComponentError(new UserError("Enter valid date"));
			errorFlag = true;
		}
		if (errorFlag) {
			throw new FocusException.ValidationException();
		}
	}
	
	@Override
	protected void saveDetails() throws SaveException, FileNotFoundException, IOException {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Saving Data... ");
		int count = 0;
		List<EmployeeDM> employeeList = new ArrayList<EmployeeDM>();
		employeeList = serviceEmployee.getEmployeeList(null, null, null, loginCompanyId, null, "Active", null);
		if (tblMstScrSrchRslt.getValue() == null) {
			for (EmployeeDM employeeObj : employeeList) {
				tfempCode.setReadOnly(false);
				if (tfempCode.getValue().trim().length() > 0 && employeeObj.getEmployeeCode().equals(tfempCode.getValue())) {
					count++;
				}
				tfempCode.setReadOnly(true);
			}
		}
		
		if (count != 0) {
			new FocusFailureNotification(FocusConstants.sameDataMsg);
		} else {
			EmployeeDM employeeObj = new EmployeeDM();
			if (tblMstScrSrchRslt.getValue() != null) {
				employeeObj = beanEmployee.getItem(tblMstScrSrchRslt.getValue()).getBean();
				new FocusSuccessNotification(FocusConstants.UPDATED_MSG);
				tfempCode.setReadOnly(false);
				employeeObj.setEmployeeCode(tfempCode.getValue());
				tfempCode.setReadOnly(true);
			} else {
				for (EmployeeDM employeeObjs : employeeList = serviceEmployee.getEmployeeList(null, null, null, loginCompanyId, null, "Active", null)) {
					tfempCode.setReadOnly(false);
					if (tfempCode.getValue().trim().length() > 0 && employeeObjs.getEmployeeCode().equals(tfempCode.getValue())) {
						count++;
					}
					tfempCode.setReadOnly(true);
				}
				if (count == 0) {
					List<SlnoGenDM> slnoList = serviceSlnoGen.getSlnoGenList(null, loginCompanyId, true, "EmployeeCode");
					for (SlnoGenDM slnoObj : slnoList) {
						if (slnoObj.getAutogenYN().equals(true)) {
							employeeObj.setEmployeeCode(slnoObj.getRefKey());
						}
					}
					new FocusSuccessNotification(FocusConstants.SAVE_MSG);
				} else {
					new FocusFailureNotification(FocusConstants.sameDataMsg);
				}
			}
			employeeObj.setEmployeeSalut(cbTitle.getValue().toString());
			employeeObj.setFirstName(tfFirstname.getValue());
			if (tflastname.getValue() != null) {
				employeeObj.setLastName(tflastname.getValue());
			}
			employeeObj.setCompanyId(loginCompanyId);
			employeeObj.setPrimaryPhone(tfPhoneNo.getValue());
			employeeObj.setPrimaryEmail(tfEmailId.getValue());
			employeeObj.setEmployeeSalary(Double.valueOf(tfSalary.getValue()));
			employeeObj.setDob(pdDOB.getValue());
			employeeObj.setGender(cbGender.getValue().toString());
			employeeObj.setDoj(pdDoJ.getValue());
			if ((Boolean) UI.getCurrent().getSession().getAttribute("isFileUploaded")) {
				try {
					employeeObj.setEmpPhoto((byte[]) UI.getCurrent().getSession().getAttribute("imagebyte"));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				employeeObj.setEmpPhoto(null);
			}
			if (chlogAcs.getValue() == true) {
				employeeObj.setLoginaccessYn(true);
			} else {
				employeeObj.setLoginaccessYn(false);
			}
			employeeObj.setEmpRole((String) cbEmpRole.getValue());
			employeeObj.setEmpStatus(cbStatus.getValue().toString());
			employeeObj.setUpdatedDate(DateUtil.getcurrentdate());
			employeeObj.setUpdatedBy(loginUserName);
			serviceEmployee.saveOrUpdateEmployeeDetails(employeeObj);
			if (tblMstScrSrchRslt.getValue() == null) {
				List<SlnoGenDM> slnoListNxt = serviceSlnoGen.getSlnoGenList(null, loginCompanyId, null, "EmployeeCode");
				for (SlnoGenDM slnoObj : slnoListNxt) {
					if (slnoObj.getAutogenYN().equals(true)) {
						serviceSlnoGen.updateNextSequenceNumber(loginCompanyId, "EmployeeCode");
					}
				}
			}
			resetFields();
			loadSrchRslt();
			employeeId = 0L;
			loadDocumentSrchRslt();
		}
	}
	
	@Override
	protected void cancelDetails() {
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Canceling action ");
		assembleSearchLayout();
		tfFirstname.setRequired(false);
		tfPhoneNo.setRequired(false);
		tfEmailId.setRequired(false);
		tfSalary.setRequired(false);
		tblMstScrSrchRslt.setValue(null);
		resetFields();
		loadSrchRslt();
		employeeId = 0L;
		loadDocumentSrchRslt();
	}
	
	@Override
	public void downloadDetails() {
	}
	
	@Override
	protected void resetFields() {
		tfFirstname.setValue("");
		tflastname.setValue("");
		tfempCode.setReadOnly(false);
		tfempCode.setValue("");
		tfempCode.setReadOnly(true);
		tfPhoneNo.setValue("");
		tfEmailId.setValue("");
		tfSalary.setValue("");
		chlogAcs.setValue(false);
		cbStatus.setValue(cbStatus.getItemIds().iterator().next());
		pdDOB.setValue(null);
		pdDoJ.setValue(null);
		cbTitle.setValue(null);
		cbGender.setValue(null);
		tfempCode.setComponentError(null);
		tfFirstname.setComponentError(null);
		tflastname.setComponentError(null);
		pdDOB.setComponentError(null);
		pdDoJ.setComponentError(null);
		tfSalary.setComponentError(null);
		cbEmpRole.setValue(null);
		tfPhoneNo.setComponentError(null);
		tfEmailId.setComponentError(null);
		cbGender.setComponentError(null);
		cbTitle.setComponentError(null);
		cbGender.setRequired(true);
		tfempCode.setRequired(true);
		tfFirstname.setRequired(true);
		tfPhoneNo.setRequired(true);
		tfEmailId.setRequired(true);
		tfSalary.setRequired(true);
		cbEmpRole.setRequired(true);
		cbTitle.setRequired(true);
		pdDOB.setRequired(true);
		pdDoJ.setRequired(true);
		new UploadUI(hlimage);
		UI.getCurrent().getSession().setAttribute("isFileUploaded", false);
		employeeId = 0L;
	}
	
	public void editEmployeeDocument() {
		hlUserInputLayout.setVisible(true);
		Item sltedRcd = tblEmployeDocument.getItem(tblEmployeDocument.getValue());
		EmployeeDocumentDM editEmployeelist = employeeDocBean.getItem(tblEmployeDocument.getValue()).getBean();
		tfDocName.setValue(sltedRcd.getItemProperty("documentName").getValue().toString());
		if (editEmployeelist.getDocument() != null) {
			byte[] bytes = (byte[]) editEmployeelist.getDocument();
			UploadDocumentUI test = new UploadDocumentUI(vlDocument);
			test.displaycertificate(bytes);
		} else {
			new UploadDocumentUI(vlDocument);
		}
		cbDocStatus.setValue((String) sltedRcd.getItemProperty("documentStatus").getValue());
	}
	
	private void deleteDetails() {
		EmployeeDocumentDM save = new EmployeeDocumentDM();
		if (tblEmployeDocument.getValue() != null) {
			save = employeeDocBean.getItem(tblEmployeDocument.getValue()).getBean();
		}
		employeeDocList.remove(save);
		save.setDocumentStatus("Inactive");
		serviceEmployeeDocumentService.saveOrUpdateEmployeeDocumentDetails(save);
		resetDocumentFields();
		loadDocumentSrchRslt();
		btndelete.setEnabled(false);
		btnAddDtl.setCaption("Add");
	}
	
	public boolean validateDocumentDetails() throws ValidationException {
		boolean errorflag = false;
		logger.info("Company ID : " + loginCompanyId + " | User Name : " + loginUserName + " > " + "Validating Data ");
		if (tfDocName.getValue().length() == 0) {
			tfDocName.setComponentError(new UserError("Enter document name"));
			errorflag = true;
		} else {
			tfDocName.setComponentError(null);
		}
		return errorflag;
	}
	
	public void saveEmployeeDocumentDtl() throws IOException {
		try {
			EmployeeDocumentDM empDocumentObj = new EmployeeDocumentDM();
			if (tblEmployeDocument.getValue() != null) {
				empDocumentObj = employeeDocBean.getItem(tblEmployeDocument.getValue()).getBean();
			}
			empDocumentObj.setEmployeeId(employeeId);
			empDocumentObj.setCompanyId(loginCompanyId);
			empDocumentObj.setDocumentName(tfDocName.getValue());
			if ((Boolean) UI.getCurrent().getSession().getAttribute("isDocumentUploaded")) {
				File file = new File(FocusConstants.DOCUMENT_PATH);
				FileInputStream fio = new FileInputStream(file);
				byte fileContent[] = new byte[(int) file.length()];
				fio.read(fileContent);
				fio.close();
				empDocumentObj.setDocument(fileContent);
			} else {
				empDocumentObj.setDocument(null);
			}
			empDocumentObj.setUpdatedDate(DateUtil.getcurrentdate());
			empDocumentObj.setDocumentStatus(cbDocStatus.getValue().toString());
			empDocumentObj.setUpdated_by(loginUserName);
			serviceEmployeeDocumentService.saveOrUpdateEmployeeDocumentDetails(empDocumentObj);
			resetDocumentFields();
			loadDocumentSrchRslt();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetDocumentFields() {
		tfDocName.setValue("");
		tfDocName.setRequired(true);
		tfDocName.setComponentError(null);
		cbDocStatus.setValue(cbDocStatus.getItemIds().iterator().next());
		new UploadDocumentUI(vlDocument);
		UI.getCurrent().getSession().setAttribute("isDocumentUploaded", false);
	}
	
}
