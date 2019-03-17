package com.ninox.focus.view.mst;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.ninox.focus.domain.ApprovalSchemaDM;
import com.ninox.focus.domain.ScreenAccessConfigDM;
import com.ninox.focus.domain.UserDM;
import com.ninox.focus.exception.FocusException.NoDataFoundException;
import com.ninox.focus.exception.FocusException.SaveException;
import com.ninox.focus.exception.FocusException.ValidationException;
import com.ninox.focus.service.ApprovalSchemaService;
import com.ninox.focus.service.ScreenAccessConfigService;
import com.ninox.focus.service.UserService;
import com.ninox.focus.util.DateUtils;
import com.ninox.focus.view.components.FocusAddEditHLayout;
import com.ninox.focus.view.components.FocusButton;
import com.ninox.focus.view.components.FocusComboBox;
import com.ninox.focus.view.components.FocusPanelGenerator;
import com.ninox.focus.view.components.FocusSuccessNotification;
import com.ninox.focus.view.util.BaseUI;
import com.ninox.focus.view.util.FocusConstants;
import com.ninox.focus.view.util.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;

public class ApprovalSchema extends BaseUI {
	private static final long serialVersionUID = 1L;
	private ApprovalSchemaService serviceApprSchema = (ApprovalSchemaService) SpringContextHelper
			.getBean("approvalSchema");
	private UserService serviceUser = (UserService) SpringContextHelper.getBean("appUser");
	private ScreenAccessConfigService serviceScrnAccessConfig = (ScreenAccessConfigService) SpringContextHelper
			.getBean("scrnAccessConfig");
	// form layout for input controls
	private FormLayout flColumn1, flColumn2, flColumn3;
	// User Input Components
	private FocusComboBox cbUser, cbRole;
	private FocusButton btnSaveApprSchm = new FocusButton("", "save");
	private FocusButton btnCancelButton = new FocusButton("", "delete");
	private BeanItemContainer<ApprovalSchemaDM> beanApprovalSchema = null;
	private BeanItemContainer<ScreenAccessConfigDM> beanScrennAccess = null;
	private FocusAddEditHLayout hlSearchLayout;
	private int recordCnt;
	private static Logger logger = Logger.getLogger(ApprovalSchema.class);
	private List<ApprovalSchemaDM> approvalScmeList = new ArrayList<ApprovalSchemaDM>();
	private List<ScreenAccessConfigDM> screenAccessList = new ArrayList<ScreenAccessConfigDM>();
	private String userName, userRole;
	private boolean isBaseAdmin = false;
	@SuppressWarnings({ "unused" })
	private Long companyId;
	
	public ApprovalSchema() {
		userName = UI.getCurrent().getSession().getAttribute("userName").toString();
		companyId = Long.valueOf(UI.getCurrent().getSession().getAttribute("companyId").toString());
		userRole = UI.getCurrent().getSession().getAttribute("userRole").toString();
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > "
				+ "Inside ApprovalSchema() constructor");
		// Loading the ApprovalSchema UI
		buildView();
	}
	
	@SuppressWarnings("serial")
	public void buildView() {
		btnSaveApprSchm.setWidth("80px");
		btnSaveApprSchm.setHeight("25px");
		btnCancelButton.setWidth("80px");
		btnCancelButton.setHeight("25px");
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Painting ApprovalSchema UI");
		// cbRole is name of Combo box to select role name
		cbRole = new FocusComboBox("User Role");
		cbRole.setItemCaptionPropertyId("userRole");
		cbRole.setWidth("150");
		cbRole.setRequired(true);
		//cbUser
		cbUser = new FocusComboBox("User Name");
		cbUser.setItemCaptionPropertyId("userName");
		cbUser.setRequired(true);
		cbUser.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			public void valueChange(ValueChangeEvent event) {
				// Get the selected item
				Object itemId = event.getProperty().getValue();
				BeanItem<?> item = (BeanItem<?>) cbUser.getItem(itemId);
				if (item != null) {
					loadUserRole();
					approvalScmeList = new ArrayList<ApprovalSchemaDM>();
					screenAccessList = new ArrayList<ScreenAccessConfigDM>();
				}
			}
		});

		cbRole.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				if (cbRole.getValue() != null) {
					String userRole;
					if (cbRole.getValue().toString().equals("Admin")) {
						userRole = null;
					} else {
						userRole = (String) cbRole.getValue();
					}
					System.out.println("getUnAllocatedScreenList: " + userRole);
					if (serviceScrnAccessConfig.getUnAllocatedScreenList(userRole, (Long) cbUser.getValue()).size() > 0) {
						for (ScreenAccessConfigDM screenAccessConObj : serviceScrnAccessConfig
								.getUnAllocatedScreenList(userRole, (Long) cbUser.getValue())) {
							ApprovalSchemaDM approvalSchemaObj = new ApprovalSchemaDM();
							approvalSchemaObj.setCompanyId(companyId);
							approvalSchemaObj.setScreenId(screenAccessConObj.getScreenId());
							approvalSchemaObj.setUserRole((String) cbRole.getValue());
							approvalSchemaObj.setUserId((Long) cbUser.getValue());
							approvalSchemaObj.setIsApproved(false);
							approvalSchemaObj.setStatus("Active");
							approvalSchemaObj.setUpdatedDate(DateUtils.getcurrentdate());
							approvalSchemaObj.setUpdatedBy(userName);
							serviceApprSchema.saveOrUpdateApprovalSchema(approvalSchemaObj);
						}
					}
				}
				if (cbRole.getValue() != null) {
					if (serviceApprSchema.getApprovalSchemaList(null, companyId, null, cbRole.getValue().toString(),
							"Active", Long.valueOf(cbUser.getValue().toString()), null).size() > 0) {
						approvalScmeList = serviceApprSchema.getApprovalSchemaList(null, companyId, null,
								cbRole.getValue().toString(), null,
								Long.valueOf(cbUser.getValue().toString()), null);
						loadSrchRslt();
					} else {
						String userRole;
						if (cbRole.getValue().toString().equals("Admin")) {
							userRole = null;
						} else {
							userRole = (String) cbRole.getValue();
						}
						System.out.println("getScreenAccessConfigList: " + userRole);
						screenAccessList = serviceScrnAccessConfig.getScreenAccessConfigList(null, null, companyId,
								userRole, null, "Active");
						loadSrchRslt();
					}
				}
			}
		});
		// Buttons were set to invisible which are not used
		btnAdd.setVisible(false);
		btnEdit.setVisible(false);
		btnSearch.setVisible(false);
		btnReset.setVisible(false);	
		btnDownload.setVisible(false);
		// Buttons were set to be visible which are used
		btnSaveApprSchm.setVisible(true);
		btnSaveApprSchm.setStyleName("savebt");
		btnSaveApprSchm.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6551953728534136363L;
			
			public void buttonClick(ClickEvent event) {
				if (validateAuthSchema()) {
					saveAuthSchema();
				}
			}
		});
		btnCancelButton.setVisible(true);
		btnCancelButton.setStyleName("cancelbt");
		btnCancelButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6551953728534136363L;
			
			public void buttonClick(ClickEvent event) {
				cancelDetails();
			}
		});
		hlPageHdrContainter.addComponent(btnSaveApprSchm);
		hlPageHdrContainter.setComponentAlignment(btnSaveApprSchm, Alignment.MIDDLE_RIGHT);
		hlPageHdrContainter.addComponent(btnCancelButton);
		hlPageHdrContainter.setComponentAlignment(btnCancelButton, Alignment.MIDDLE_RIGHT);
		hlPageHdrContainter.setMargin(new MarginInfo(false, true, false, false));
		hlSearchLayout = new FocusAddEditHLayout();
		hlSrchContainer.addComponent(FocusPanelGenerator.createPanel(hlSearchLayout));
		tblMstScrSrchRslt.setSizeFull();
		tblMstScrSrchRslt.setMultiSelect(true);
		tblMstScrSrchRslt.setSelectable(false);
		tblMstScrSrchRslt.removeContainerProperty("select");
		for (Object listener : tblMstScrSrchRslt.getListeners(ItemClickEvent.class)) {
			tblMstScrSrchRslt.removeItemClickListener((ItemClickListener) listener);
		}
		assembleSearchLayout();
		loadUserList();
//		resetFields();
		loadSrchRslt();
	}
	
	private void assembleSearchLayout() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Assembling search layout");
		// Initializing the form layouts
		tblMstScrSrchRslt.setVisible(true);
		tblMstScrSrchRslt.setPageLength(14);
		hlSearchLayout.removeAllComponents();
		flColumn1 = new FormLayout();
		flColumn2 = new FormLayout();
		flColumn3 = new FormLayout();
		// add the user input items into appropriate form layout
		flColumn2.addComponent(cbUser);
		flColumn3.addComponent(cbRole);
		hlSearchLayout.addComponent(flColumn1);
		hlSearchLayout.addComponent(flColumn2);
		hlSearchLayout.addComponent(flColumn3);
		hlSearchLayout.setSpacing(true);
		hlSearchLayout.setMargin(true);
		hlSearchLayout.setSizeUndefined();
	}
	
	private void loadSrchRslt() {
		logger.info("Company ID : " + companyId + " | User Name : " + userName + " > " + "Loading Search...");
		if (cbRole.getValue() != null) {
			isBaseAdmin = serviceUser.isBaseAdmin((Long)cbUser.getValue());
			tblMstScrSrchRslt.removeAllItems();
			if (approvalScmeList.size() > 0) {
				recordCnt = approvalScmeList.size();
				beanApprovalSchema = new BeanItemContainer<ApprovalSchemaDM>(ApprovalSchemaDM.class);
				beanApprovalSchema.addAll(approvalScmeList);
				tblMstScrSrchRslt.addContainerProperty("Ref.Id", Long.class, null);
				tblMstScrSrchRslt.addContainerProperty("Screen Name", String.class, null);
				tblMstScrSrchRslt.addContainerProperty("Screen Status", String.class, null);
				tblMstScrSrchRslt.addContainerProperty("Is Approved", CheckBox.class, null);
				for (ApprovalSchemaDM approvalSchemaObj : approvalScmeList) {
					CheckBox chIsApproved = new CheckBox("", false);
					if (approvalSchemaObj.getIsApproved() != null && approvalSchemaObj.getIsApproved()) {
						chIsApproved.setValue(true);
						if(isBaseAdmin) {
							chIsApproved.setReadOnly(true);
						}
					}
					tblMstScrSrchRslt.addItem(
							new Object[] { approvalSchemaObj.getApprovelSchemaId(), approvalSchemaObj.getScreenDesc(),
									approvalSchemaObj.getStatus(), chIsApproved }, approvalSchemaObj
									.getApprovelSchemaId().intValue());
				}
				tblMstScrSrchRslt.setColumnAlignment("Ref.Id", Align.RIGHT);
				tblMstScrSrchRslt.setColumnFooter("Screen Status", " No.of Records : " + recordCnt);
			} else {
				recordCnt = screenAccessList.size();
				beanScrennAccess = new BeanItemContainer<ScreenAccessConfigDM>(ScreenAccessConfigDM.class);
				beanScrennAccess.addAll(screenAccessList);
				tblMstScrSrchRslt.addContainerProperty("Ref.Id", Long.class, null);
				tblMstScrSrchRslt.addContainerProperty("Screen Name", String.class, null);
				tblMstScrSrchRslt.addContainerProperty("Is Approved", CheckBox.class, null);
				for (ScreenAccessConfigDM screenAccesConfObj : screenAccessList) {
					CheckBox chIsApproved = new CheckBox("", false);
					tblMstScrSrchRslt.addItem(
							new Object[] { screenAccesConfObj.getScreenAccessId(), screenAccesConfObj.getScreeName(),
									screenAccesConfObj.getAccessStatus(), chIsApproved }, screenAccesConfObj
									.getScreenAccessId().intValue());
				}
				tblMstScrSrchRslt.setColumnAlignment("Ref.Id", Align.RIGHT);
				tblMstScrSrchRslt.setColumnFooter("Screen Status", " No.of Records : " + recordCnt);
			}
		} else {
			tblMstScrSrchRslt.removeAllItems();
			screenAccessList = serviceScrnAccessConfig.getScreenAccessConfigList(null, null, companyId, null,
					null, "Active");
			recordCnt = screenAccessList.size();
			beanScrennAccess = new BeanItemContainer<ScreenAccessConfigDM>(ScreenAccessConfigDM.class);
			beanScrennAccess.addAll(screenAccessList);
			tblMstScrSrchRslt.addContainerProperty("Ref.Id", Long.class, null);
			tblMstScrSrchRslt.addContainerProperty("Screen Name", String.class, null);
			tblMstScrSrchRslt.addContainerProperty("Screen Status", String.class, null);
			tblMstScrSrchRslt.addContainerProperty("Is Approved", CheckBox.class, null);
			for (ScreenAccessConfigDM screenAccesConfObj : screenAccessList) {
				CheckBox chIsApproved = new CheckBox("", false);
				tblMstScrSrchRslt.addItem(
						new Object[] { screenAccesConfObj.getScreenAccessId(), screenAccesConfObj.getScreeName(),
								screenAccesConfObj.getAccessStatus(), chIsApproved }, screenAccesConfObj
								.getScreenAccessId().intValue());
			}
			tblMstScrSrchRslt.setColumnAlignment("Ref.Id", Align.RIGHT);
			tblMstScrSrchRslt.setColumnFooter("Screen Status", " No.of Records : " + recordCnt);
		}
	}
	
	private void loadUserList() {
		List<UserDM> list = serviceUser.getUserDMList(null, companyId, null, null, null, "Active", null);
		BeanContainer<Long, UserDM> beanEmployee = new BeanContainer<Long, UserDM>(UserDM.class);
		beanEmployee.setBeanIdProperty("userId");
		beanEmployee.addAll(list);
		cbUser.setContainerDataSource(beanEmployee);
	}
	
	@Override
	protected void resetSearchDetails() {
	}
	
	private void saveAuthSchema() {
		if (approvalScmeList.size() > 0) {
			for (Iterator<?> iterator = tblMstScrSrchRslt.getItemIds().iterator(); iterator.hasNext();) {
				int itemId = (Integer) iterator.next();
				try {
					Item item = tblMstScrSrchRslt.getItem(itemId);
					for (ApprovalSchemaDM approvalObj : approvalScmeList) {
						if (approvalObj.getApprovelSchemaId() == itemId) {
							approvalObj.setCompanyId(companyId);
							approvalObj.setScreenId(approvalObj.getScreenId());
							approvalObj.setUserRole((String) cbRole.getValue());
							CheckBox check = (CheckBox) (item.getItemProperty("Is Approved").getValue());
							if (check.getValue() != null && check.getValue()) {
								approvalObj.setIsApproved(true);
								approvalObj.setStatus("Active");
							} else {
								approvalObj.setIsApproved(false);
								approvalObj.setStatus("Inactive");
							}
							approvalObj.setUserId((Long) cbUser.getValue());
							approvalObj.setUpdatedDate(DateUtils.getcurrentdate());
							approvalObj.setUpdatedBy(userName);
							serviceApprSchema.saveOrUpdateApprovalSchema(approvalObj);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			new FocusSuccessNotification(FocusConstants.UPDATED_MSG);
		} else {
			for (Iterator<?> iterator = tblMstScrSrchRslt.getItemIds().iterator(); iterator.hasNext();) {
				int itemId = (Integer) iterator.next();
				ApprovalSchemaDM approvalSchemaObj = new ApprovalSchemaDM();
				try {
					Item item = tblMstScrSrchRslt.getItem(itemId);
					for (ScreenAccessConfigDM scrnAccConfObj : screenAccessList) {
						if (scrnAccConfObj.getScreenAccessId() == itemId) {
							approvalSchemaObj.setCompanyId(companyId);
							approvalSchemaObj.setScreenId(scrnAccConfObj.getScreenId());
							approvalSchemaObj.setUserRole((String) cbRole.getValue());
							CheckBox check = (CheckBox) (item.getItemProperty("Is Approved").getValue());
							if (check.getValue() != null && check.getValue()) {
								approvalSchemaObj.setIsApproved(true);
								approvalSchemaObj.setStatus("Active");
							} else {
								approvalSchemaObj.setIsApproved(false);
								approvalSchemaObj.setStatus("Inactive");
							}
							approvalSchemaObj.setUserId((Long) cbUser.getValue());
							approvalSchemaObj.setUpdatedDate(DateUtils.getcurrentdate());
							approvalSchemaObj.setUpdatedBy(userName);
							serviceApprSchema.saveOrUpdateApprovalSchema(approvalSchemaObj);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			new FocusSuccessNotification(FocusConstants.SAVE_MSG);
		}
		loadSrchRslt();
	}
	
	@Override
	protected void addDetails() {
	}
	
	@Override
	protected void editDetails() {
	}
	
	private boolean validateAuthSchema() {
		Boolean errorFlag = true;
		cbUser.setComponentError(null);
		cbRole.setComponentError(null);
		if (cbUser.getValue() == null) {
			cbUser.setComponentError(new UserError("Please Select User Name"));
			errorFlag = false;
		} else {
			cbUser.setComponentError(null);
		}
		if (cbRole.getValue() == null) {
			cbRole.setComponentError(new UserError("Please Select Role Name"));
			errorFlag = false;
		} else {
			cbRole.setComponentError(null);
		}
		return errorFlag;
	}
	
	@Override
	protected void validateDetails() throws ValidationException {
	}
	
	@Override
	protected void saveDetails() throws SaveException, FileNotFoundException, IOException {
	}
	
	@Override
	protected void cancelDetails() {
		logger.info("Companyid ID : " + companyId + " | User Name : " + userName + " > " + "Canceling action ");
		resetFields();
		assembleSearchLayout();
		chSelectAll.setValue(false);
		tblMstScrSrchRslt.refreshRowCache();
		lblNotification.setIcon(null);
		lblNotification.setCaption("");
		loadSrchRslt();
	}
	
	@Override
	public void downloadDetails() {
	}
	
	@Override
	protected void resetFields() {
		cbUser.setValue(null);
		cbRole.setValue(null);
		cbUser.setComponentError(null);
		cbRole.setComponentError(null);
		checkNo = 0l;
	}
	
	private void loadUserRole() {
		List<UserDM> list = serviceUser.getUserDMList((Long)cbUser.getValue(), companyId, null, null, null, "Active", null);
		BeanContainer<Long, UserDM> beanEmployee = new BeanContainer<Long, UserDM>(UserDM.class);
		beanEmployee.setBeanIdProperty("userRole");
		beanEmployee.addAll(list);
		cbRole.setContainerDataSource(beanEmployee);
	}
	
	@Override
	protected void searchDetails() throws NoDataFoundException {
	}
}
