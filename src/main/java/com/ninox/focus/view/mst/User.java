package com.ninox.focus.view.mst;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.log4j.Logger;
import com.ninox.focus.domain.EmployeeDM;
import com.ninox.focus.domain.UserDM;
import com.ninox.focus.exception.FocusException;
import com.ninox.focus.exception.FocusException.NoDataFoundException;
import com.ninox.focus.exception.FocusException.SaveException;
import com.ninox.focus.exception.FocusException.ValidationException;
import com.ninox.focus.service.ApprovalSchemaService;
import com.ninox.focus.service.EmployeeService;
import com.ninox.focus.service.UserService;
import com.ninox.focus.util.DateUtils;
import com.ninox.focus.view.components.FocusAddEditHLayout;
import com.ninox.focus.view.components.FocusComboBox;
import com.ninox.focus.view.components.FocusFailureNotification;
import com.ninox.focus.view.components.FocusPanelGenerator;
import com.ninox.focus.view.components.FocusSuccessNotification;
import com.ninox.focus.view.components.FocusTextField;
import com.ninox.focus.view.util.BaseUI;
import com.ninox.focus.view.util.CryptoUtil;
import com.ninox.focus.view.util.FocusConstants;
import com.ninox.focus.view.util.DateUtil;
import com.ninox.focus.view.util.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.UserError;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;

public class User extends BaseUI {
	private static final long serialVersionUID = 1L;
	private ApprovalSchemaService serviceApprovalSchema = (ApprovalSchemaService) SpringContextHelper
			.getBean("approvalSchema");
	private EmployeeService serviceEmployee = (EmployeeService) SpringContextHelper.getBean("employee");
	private UserService serviceUser = (UserService) SpringContextHelper.getBean("appUser");
	private FocusTextField tfUserName, tfloginId, tfCrtDate;
	private FocusComboBox cbUserRole, cbStatus, cbEmployee;
	private PasswordField pfPassword, pfConfirmPassword;
	private FormLayout flColumn1, flColumn2, flColumn3;
	private FocusAddEditHLayout hlUserInputLayout = new FocusAddEditHLayout();
	private FocusAddEditHLayout hlSearchLayout;
	private BeanItemContainer<UserDM> beanUser = null;
	private BeanContainer<Long, EmployeeDM> beanEmployee = null;
	@SuppressWarnings("unused")
	private Long companyId, employeeId, userId;
	private int recordCnt = 0;
	private String userName, userRole;
	private Logger logger = Logger.getLogger(User.class);
	
	public User() {
		userName = UI.getCurrent().getSession().getAttribute("userName").toString();
		companyId = Long.valueOf(UI.getCurrent().getSession().getAttribute("companyId").toString());
		userRole = UI.getCurrent().getSession().getAttribute("userRole").toString();
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > "
				+ "Inside ChequeDeposit() constructor");
		// Loading the User UI
		buildView();
	}
	
	@SuppressWarnings("serial")
	public void buildView() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Painting User UI");
		chSelectAll.setVisible(false);
		btnDownload.setVisible(false);
		btnEdit.setVisible(true);
		hlCmdBtnLayout.setExpandRatio(btnEdit, 1);
		tfUserName = new FocusTextField("Display Name");
		tfUserName.setWidth("200px");
		tfUserName.setMaxLength(100);
		tfloginId = new FocusTextField("User Name");
		tfloginId.setWidth("200px");
		tfloginId.setMaxLength(30);
		pfPassword = new PasswordField("Password");
		pfPassword.setWidth("200px");
		pfPassword.setMaxLength(20);
		pfPassword.setMaxLength(20);
		pfConfirmPassword = new PasswordField("Confirm Password");
		pfConfirmPassword.setWidth("200px");
		pfConfirmPassword.setMaxLength(20);
		cbStatus = new FocusComboBox("User Status", "UserDM", "UserStatus");
		cbStatus.setWidth("200px");
		cbEmployee = new FocusComboBox("Employee Name");
		cbEmployee.setImmediate(true);
		cbEmployee.setItemCaptionPropertyId("fullName");
		cbEmployee.setWidth("200px");
		cbEmployee.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Object itemId = event.getProperty().getValue();
				if (itemId != null) {
					tfUserName.setValue(serviceEmployee.getEmployeeName((Long) cbEmployee.getValue()));
				}
			}
		});
		cbUserRole = new FocusComboBox("User Role", "UserDM", "UserRole");
		cbUserRole.setWidth("200px");
		cbUserRole.setImmediate(true);
		tfCrtDate = new FocusTextField("Creation Date");
		tfCrtDate.setReadOnly(false);
		tfCrtDate.setValue(DateUtil.monthDateYear(new Date()));
		tfCrtDate.setReadOnly(true);
		tfCrtDate.setWidth("200px");
		hlSearchLayout = new FocusAddEditHLayout();
		assembleSearchLayout();
		loadEmployee();
		hlSrchContainer.addComponent(FocusPanelGenerator.createPanel(hlSearchLayout));
		resetFields();
		loadSrchRslt();
	}
	
	private void loadEmployee() {
		List<EmployeeDM> getEmployeeList = new ArrayList<EmployeeDM>();
		getEmployeeList.addAll(serviceEmployee.getEmployeeList(null, null, null, companyId, null, "Active", true));
		beanEmployee = new BeanContainer<Long, EmployeeDM>(EmployeeDM.class);
		beanEmployee.setBeanIdProperty("employeeId");
		beanEmployee.addAll(getEmployeeList);
		cbEmployee.setContainerDataSource(beanEmployee);
	}
	
	private void loadSrchRslt() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Loading Search...");
		tblMstScrSrchRslt.removeAllItems();
		List<UserDM> userList = new ArrayList<UserDM>();
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Search Parameters are "
				+ companyId + ", " + tfUserName.getValue() + ",");
		userList = serviceUser.getUserDMList(null, companyId, tfUserName.getValue(), null, null,
				cbStatus.getValue().toString(), (String)cbUserRole.getValue());
		recordCnt = userList.size();
		beanUser = new BeanItemContainer<UserDM>(UserDM.class);
		beanUser.addAll(userList);
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Got the Branch. result set");
		tblMstScrSrchRslt.setContainerDataSource(beanUser);
		tblMstScrSrchRslt.setVisibleColumns(new Object[] { "userId", "userName", "loginId", "loginPassword",
				"userRole", "userStatus", "updatedDate", "updatedBy" });
		tblMstScrSrchRslt.setColumnHeaders(new String[] { "Ref.Id", "Display Name", "Login Id", "Password", "User Role",
				"Status", "Updated Date", "Updated By" });
		tblMstScrSrchRslt.setColumnAlignment("userId", Align.RIGHT);
		tblMstScrSrchRslt.setColumnFooter("updatedBy", "No.of Records : " + recordCnt);
	}
	
	private void assembleSearchLayout() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Assembling search layout");
		/*
		 * Adding user input layout to the search layout as all the fields in the user input are available in the search
		 * block. hence the same layout used as is
		 */
		hlSearchLayout.removeAllComponents();
		tblMstScrSrchRslt.setPageLength(16);
		cbEmployee.setRequired(false);
		tfUserName.setRequired(false);
		cbUserRole.setRequired(false);
		tfloginId.setRequired(false);
		pfPassword.setRequired(false);
		pfConfirmPassword.setRequired(false);
		flColumn1 = new FormLayout();
		flColumn2 = new FormLayout();
		flColumn3 = new FormLayout();
		flColumn1.addComponent(tfUserName);
		flColumn2.addComponent(cbUserRole);
		flColumn3.addComponent(cbStatus);
		hlSearchLayout.addComponent(flColumn1);
		hlSearchLayout.addComponent(flColumn2);
		hlSearchLayout.addComponent(flColumn3);
		hlSearchLayout.setSizeUndefined();
		hlSearchLayout.setSpacing(true);
		hlSearchLayout.setMargin(true);
	}
	
	@Override
	protected void searchDetails() throws NoDataFoundException {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + " Invoking search");
		loadSrchRslt();
		if (recordCnt == 0) {
			logger.info("Company ID : " + companyId + " | User Name : " + userName + " > "
					+ "No data for the search. throwing ERPException.NoDataFoundException");
			throw new FocusException.NoDataFoundException();
		} else {
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
		}
	}
	
	@Override
	protected void resetSearchDetails() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > "
				+ "Resetting search fields and reloading the result");
		tfUserName.setValue("");
		cbUserRole.setValue(null);
		cbStatus.setValue(cbStatus.getItemIds().iterator().next());
		lblNotification.setIcon(null);
		lblNotification.setCaption("");
		loadSrchRslt();
	}
	
	@Override
	protected void addDetails() {
		resetFields();
		hlUserInputLayout.removeAllComponents();
		assembleUserInputLayout();
		hlUserIPContainer.addComponent(FocusPanelGenerator.createPanel(hlUserInputLayout));
		tfUserName.setRequired(true);
		tfloginId.setRequired(true);
		pfPassword.setRequired(true);
		pfConfirmPassword.setRequired(true);
		cbUserRole.setRequired(true);
		cbEmployee.setRequired(true);
		// reset the input controls to default value
		resetFields();
	}
	
	private void assembleUserInputLayout() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Assembling search layout");
		cbEmployee.setRequired(true);
		tfUserName.setRequired(true);
		cbUserRole.setRequired(true);
		tfloginId.setRequired(true);
		pfPassword.setRequired(true);
		pfConfirmPassword.setRequired(true);
		// add the form layouts into user input layout
		flColumn1 = new FormLayout();
		flColumn2 = new FormLayout();
		flColumn3 = new FormLayout();
		// create form layouts to hold the input items
		hlUserInputLayout.removeAllComponents();
		tblMstScrSrchRslt.setPageLength(15);
		flColumn1.addComponent(cbEmployee);
		flColumn1.addComponent(tfUserName);
		flColumn1.addComponent(cbUserRole);
		flColumn2.addComponent(tfloginId);
		flColumn2.addComponent(pfPassword);
		flColumn2.addComponent(pfConfirmPassword);
		flColumn3.addComponent(tfCrtDate);
		flColumn3.addComponent(cbStatus);
		hlUserInputLayout.addComponent(flColumn1);
		hlUserInputLayout.addComponent(flColumn2);
		hlUserInputLayout.addComponent(flColumn3);
		// hlUserInputLayout.addComponent(flColumn4);
		hlUserInputLayout.setSpacing(true);
		hlUserInputLayout.setMargin(true);
		hlUserInputLayout.setSizeUndefined();
	}
	
	@Override
	protected void editDetails() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Invoking Edit record ");
		assembleUserInputLayout();
		hlUserIPContainer.removeAllComponents();
		hlUserIPContainer.addComponent(FocusPanelGenerator.createPanel(hlUserInputLayout));
		editUserList();
	}
	
	private void editUserList() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Editing the selected record");
		hlUserInputLayout.setVisible(true);
		Item sltedRcd = tblMstScrSrchRslt.getItem(tblMstScrSrchRslt.getValue());
		if (sltedRcd != null) {
			UserDM editUserList = beanUser.getItem(tblMstScrSrchRslt.getValue()).getBean();
			userId = editUserList.getUserId();
			tfUserName.setValue(editUserList.getUserName());
			tfloginId.setValue(editUserList.getLoginId());
			try {
				String loginPassword = editUserList.getLoginPassword();
				String dp = new CryptoUtil().decrypt(loginPassword);
				pfPassword.setValue(dp.toString());
				pfConfirmPassword.setValue(dp.toString());
			}
			catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			}
			catch (BadPaddingException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			tfCrtDate.setReadOnly(false);
			tfCrtDate.setValue(DateUtils.dateToString(editUserList.getCreationDt()));
			tfCrtDate.setReadOnly(true);
			cbUserRole.setValue(editUserList.getUserRole().toString());
			cbStatus.setValue(editUserList.getUserStatus());
			if (editUserList.getEmpoloyeeId() != null) {
				cbEmployee.setValue(Long.valueOf(editUserList.getEmpoloyeeId()));
			}
		}
	}
	
	@Override
	protected void validateDetails() throws ValidationException {
		boolean check = false;
		if (tfUserName.getValue().equals("") || tfUserName.getValue().trim().length() < 0) {
			tfUserName.setComponentError(new UserError("Please Enter Display Name"));
			check = true;
		} else {
			tfUserName.setComponentError(null);
		}
		if (tfloginId.getValue().equals("") || tfloginId.getValue().trim().length() < 0) {
			tfloginId.setComponentError(new UserError("Please Enter User Name"));
			check = true;
		} else {
			tfloginId.setComponentError(null);
		}
		if (pfConfirmPassword.getValue().equals("") || pfPassword.getValue().equals("")
				|| !pfPassword.getValue().equals(pfConfirmPassword.getValue())) {
			pfPassword.setComponentError(new UserError("Please Enter Correct Password"));
			pfConfirmPassword.setComponentError(new UserError("Please Enter Correct Password"));
			check = true;
		} else {
			pfPassword.setComponentError(null);
			pfConfirmPassword.setComponentError(null);
		}
		if (cbUserRole.getValue() == null) {
			cbUserRole.setComponentError(new UserError("Please Select User Role"));
			check = true;
		} else {
			cbUserRole.setComponentError(null);
		}
		if (cbEmployee.getValue() == null) {
			cbEmployee.setComponentError(new UserError("Please Select Employee"));
			check = true;
		} else {
			cbEmployee.setComponentError(null);
		}
		if (check) {
			throw new FocusException.ValidationException();
		}
	}
	
	@Override
	protected void saveDetails() throws SaveException, FileNotFoundException, IOException {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Saving Data... ");
		try {
			int count = 0;
			String prevUserRole = "";
			List<UserDM> userList = new ArrayList<UserDM>();
			userList = serviceUser.getUserDMList(null, companyId, null, null, null, "Active", null);
			if (tblMstScrSrchRslt.getValue() == null) {
				for (UserDM userObj : userList) {
					if (userObj.getEmpoloyeeId() == cbEmployee.getValue()) {
						count++;
					}
				}
			}
			if (count != 0) {
				new FocusFailureNotification(FocusConstants.SAME_DATA_MSG_USER);
			} else {
				UserDM userObj = new UserDM();
				if (tblMstScrSrchRslt.getValue() != null) {
					userObj = beanUser.getItem(tblMstScrSrchRslt.getValue()).getBean();
					new FocusSuccessNotification(FocusConstants.UPDATED_MSG);
				} else {
					new FocusSuccessNotification(FocusConstants.SAVE_MSG);
				}
				prevUserRole = userObj.getUserRole();
				if (prevUserRole != cbUserRole.getValue().toString()) {
					serviceApprovalSchema.deleteUsersScreenApproval(userId);
				}
				if (userId != null) {
					userObj.setUserId(userId);
				}
				userObj.setUserName(tfUserName.getValue());
				userObj.setLoginId(tfloginId.getValue());
				if (!pfPassword.equals("")) {
					String ep = new CryptoUtil().encryptString(String.valueOf(pfPassword.getValue()));
					userObj.setLoginPassword(ep);
				}
				userObj.setCreationDt(DateUtil.getcurrentdate());
				userObj.setUserRole(cbUserRole.getValue().toString());
				userObj.setUserStatus((String) cbStatus.getValue());
				userObj.setCompanyId(companyId);
				userObj.setBaseAdmin(false);
				userObj.setEmpoloyeeId((Long) cbEmployee.getValue());
				userObj.setUpdatedDate(DateUtil.getcurrentdate());
				userObj.setUpdatedBy(userName);
				logger.info(" saveOrUpdateBranch() > " + userObj);
				serviceUser.saveOrUpdateUser(userObj);
				resetFields();
				loadSrchRslt();
				userId = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void cancelDetails() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Canceling action ");
		assembleSearchLayout();
		tblMstScrSrchRslt.setValue(null);
		resetFields();
		loadSrchRslt();
	}
	
	@Override
	public void downloadDetails() {
	}
	
	@Override
	protected void resetFields() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Resetting the UI controls");
		cbEmployee.setValue(null);
		tfUserName.setValue("");
		cbUserRole.setValue(null);
		cbStatus.setValue(cbStatus.getItemIds().iterator().next());
		tfloginId.setValue("");
		pfPassword.setValue("");
		pfConfirmPassword.setValue("");
		tfCrtDate.setReadOnly(false);
		tfCrtDate.setValue(DateUtils.dateToString(new Date()));
		tfCrtDate.setReadOnly(true);
		cbEmployee.setComponentError(null);
		tfUserName.setComponentError(null);
		cbUserRole.setComponentError(null);
		tfloginId.setComponentError(null);
		pfPassword.setComponentError(null);
		cbStatus.setComponentError(null);
		pfConfirmPassword.setComponentError(null);
	}
}