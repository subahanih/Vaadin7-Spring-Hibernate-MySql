package com.ninox.focus.view.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.ninox.focus.exception.FocusException;
import com.ninox.focus.exception.FocusException.SaveException;
import com.ninox.focus.view.components.FocusButton;
import com.ninox.focus.view.components.FocusPanelGenerator;
import com.ninox.focus.view.components.FocusTable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public abstract class BaseUI implements ClickListener {
	private static final long serialVersionUID = 1L;
	// Root container for all the page components.
	public VerticalLayout hlPageRootContainter = (VerticalLayout) UI.getCurrent().getSession().getAttribute("clLayout");
	// Header container which holds, screen name, notification and page master
	// buttons
	public HorizontalLayout hlPageHdrContainter = (HorizontalLayout) UI.getCurrent().getSession()
			.getAttribute("hlLayout");
	// Layout to display the page title
	public HorizontalLayout hlPageTitleLayout;
	// Notification layout to display information and errors
	HorizontalLayout hlNotificationLayout = new HorizontalLayout();
	// Container for all user input components, layouts
	public HorizontalLayout hlUserIPContainer = new HorizontalLayout();
	// Container for user search fields, buttons and layout
	public HorizontalLayout hlSrchContainer = new HorizontalLayout();
	// Container for user search fields, buttons and layout
	public VerticalLayout vlSrchRsltContainer = new VerticalLayout();
	// Layout for command buttons
	public HorizontalLayout hlCmdBtnLayout = new HorizontalLayout();
	// Common buttons used across all the screens
	public Button btnSave = new FocusButton("", "savebt", this);
	public Button btnCancel = new FocusButton("", "cancelbt", this);
	public Button btnAdd = new FocusButton("Add", "add", this);
	public Button btnEdit = new FocusButton("Edit", "editbt", this);
	public Button btnDownload = new FocusButton("Download", "downloadbt", this);
	public Button btnSearch = new FocusButton("", "searchbt", this);
	public Button btnReset = new FocusButton("", "resetbt", this);
	// Search result table
	public Table tblMstScrSrchRslt = new FocusTable();
	// Other local components and variables
	String screenName = "";
	public Label lblNotification;
	private Button btnScreenName;
	// CSVExprter declaration
	// protected CSVExporter csvExporter = new CSVExporter();
	private Window wndReportPopup = new Window();
	private Logger logger = Logger.getLogger(BaseUI.class);
	private boolean ignorePropertyChangeEventInCheckBoxListener;
	public Long checkNo = 0l;
	public CheckBox chSelectAll = new CheckBox("Select All Rows");
	public String loginInstitution, loginInstAddress, loginInstPhone, loginInstEmail;
	
	@SuppressWarnings("serial")
	public BaseUI() {
		if (UI.getCurrent().getSession().getAttribute("screenName") != null) {
			/*
			 * loginInstitution = UI.getCurrent().getSession().getAttribute("instName").toString(); loginInstAddress =
			 * UI.getCurrent().getSession().getAttribute("instAddress" ).toString(); loginInstPhone =
			 * UI.getCurrent().getSession().getAttribute ("instPhone").toString(); loginInstEmail =
			 * UI.getCurrent().getSession ().getAttribute("instEmail").toString();
			 */
			screenName = UI.getCurrent().getSession().getAttribute("screenName").toString();
		}
		btnSave.setWidth("80px");
		btnSave.setHeight("25px");
		btnSearch.setWidth("80px");
		btnSearch.setHeight("25px");
		btnCancel.setWidth("80px");
		btnCancel.setHeight("25px");
		btnReset.setWidth("80px");
		btnReset.setHeight("25px");
		btnScreenName = new FocusButton(screenName, "link", this);
		// Build Page Header
		// Instantiate page title layout
		hlPageHdrContainter.removeAllComponents();
		hlPageTitleLayout = new HorizontalLayout();
		hlPageTitleLayout.setMargin(new MarginInfo(false, false, false, true));
		// Add screen name in the page title layout
		hlPageTitleLayout.addComponent(btnScreenName);
		hlPageTitleLayout.setComponentAlignment(btnScreenName, Alignment.MIDDLE_CENTER);
		// instantiate notification label
		lblNotification = new Label();
		lblNotification.setContentMode(ContentMode.HTML);
		// Add notification label in the notification layout
		hlNotificationLayout.addComponent(lblNotification);
		hlNotificationLayout.setComponentAlignment(lblNotification, Alignment.MIDDLE_CENTER);
		// Add page title to the Page header container
		hlPageHdrContainter.addComponent(hlPageTitleLayout);
		hlPageHdrContainter.setComponentAlignment(hlPageTitleLayout, Alignment.MIDDLE_LEFT);
		// Add notification to the Page header container
		hlPageHdrContainter.addComponent(hlNotificationLayout);
		hlPageHdrContainter.setComponentAlignment(hlNotificationLayout, Alignment.MIDDLE_CENTER);
		// Add master buttons to the Page header container
		hlPageHdrContainter.addComponent(btnSave);
		hlPageHdrContainter.setComponentAlignment(btnSave, Alignment.MIDDLE_RIGHT);
		hlPageHdrContainter.addComponent(btnCancel);
		hlPageHdrContainter.setComponentAlignment(btnCancel, Alignment.MIDDLE_RIGHT);
		hlPageHdrContainter.addComponent(btnSearch);
		hlPageHdrContainter.setComponentAlignment(btnSearch, Alignment.MIDDLE_RIGHT);
		hlPageHdrContainter.addComponent(btnReset);
		hlPageHdrContainter.setComponentAlignment(btnReset, Alignment.MIDDLE_RIGHT);
		hlPageHdrContainter.setExpandRatio(hlNotificationLayout, 1);
		hlPageHdrContainter.setMargin(new MarginInfo(false, true, false, false));
		// Format all the Container
		hlPageRootContainter.setSpacing(true);
		hlPageRootContainter.setWidth("100%");
		hlSrchContainer.setWidth("100%");
		vlSrchRsltContainer.setSizeFull();
		hlUserIPContainer.setWidth("100%");
		hlUserIPContainer.setVisible(false);
		hlCmdBtnLayout.addComponent(btnAdd);
		hlCmdBtnLayout.setComponentAlignment(btnAdd, Alignment.MIDDLE_LEFT);
		hlCmdBtnLayout.addComponent(btnEdit);
		hlCmdBtnLayout.setComponentAlignment(btnEdit, Alignment.MIDDLE_LEFT);
		hlCmdBtnLayout.addComponent(chSelectAll);
		hlCmdBtnLayout.setComponentAlignment(chSelectAll, Alignment.MIDDLE_LEFT);
		hlCmdBtnLayout.addComponent(btnDownload);
		hlCmdBtnLayout.setComponentAlignment(btnDownload, Alignment.MIDDLE_RIGHT);
		hlCmdBtnLayout.setHeight("35px");
		hlCmdBtnLayout.setWidth("100%");
		hlCmdBtnLayout.setExpandRatio(chSelectAll, 1);
		hlCmdBtnLayout.addStyleName("topbarthree");
		hlCmdBtnLayout.setMargin(new MarginInfo(false, false, false, true));
		// Add child containers to the Root container
		hlPageRootContainter.addComponent(hlUserIPContainer);
		hlPageRootContainter.addComponent(hlSrchContainer);
		hlPageRootContainter.addComponent(FocusPanelGenerator.createPanel(hlCmdBtnLayout));
		hlPageRootContainter.addComponent(vlSrchRsltContainer);
		// Set the initial visibility property
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
		btnEdit.setEnabled(false);
		btnAdd.setEnabled(true);
		UI.getCurrent().getSession().setAttribute("lblNotification", lblNotification);
		// Set Master screen search table properties and listener events
		tblMstScrSrchRslt.setImmediate(true);
		tblMstScrSrchRslt.setColumnReorderingAllowed(true);
		tblMstScrSrchRslt.setSelectable(true);
		tblMstScrSrchRslt.setSizeFull();
		tblMstScrSrchRslt.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if (tblMstScrSrchRslt.isSelected(event.getItemId())) {
					btnEdit.setEnabled(false);
					btnAdd.setEnabled(true);
				} else {
					btnEdit.setEnabled(true);
					btnAdd.setEnabled(false);
				}
				resetFields();
			}
		});
		// tblMstScrSrchRslt.addGeneratedColumn("select", new
		// iERPCheckBoxColumnGenerator());
		tblMstScrSrchRslt.addGeneratedColumn("select", new Table.ColumnGenerator() {
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				final CheckBox checkBox = new CheckBox("");
				checkBox.addValueChangeListener(new Property.ValueChangeListener() {
					@Override
					public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
						if (!ignorePropertyChangeEventInCheckBoxListener) {
							Boolean selected = (Boolean) valueChangeEvent.getProperty().getValue();
							if (selected) {
								source.select(itemId);
								btnEdit.setEnabled(true);
								checkNo++;
							} else {
								source.unselect(itemId);
								btnEdit.setEnabled(false);
								checkNo--;
							}
						}
						if (checkNo == 0) {
							btnEdit.setEnabled(false);
							chSelectAll.setValue(false);
						} else {
							btnEdit.setEnabled(true);
						}
					}
				});
				return checkBox;
			}
		});
		chSelectAll.setImmediate(true);
		chSelectAll.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Iterator<Component> iterator = tblMstScrSrchRslt.iterator();
				while (iterator.hasNext()) {
					Component component = iterator.next();
					if (component instanceof AbstractField) {
						@SuppressWarnings("unchecked")
						AbstractField<Object> abstractField = (AbstractField<Object>) component;
						if (chSelectAll.getValue() == true) {
							btnEdit.setEnabled(true);
							abstractField.setValue(true);
						} else {
							btnEdit.setEnabled(false);
							abstractField.setValue(false);
						}
					}
				}
			}
		});
		vlSrchRsltContainer.addComponent(tblMstScrSrchRslt);
		// for report
		// csvExporter.setCaption("Comma Delimited (CSV)");
		// csvExporter.setStyleName("borderless");
	}
	
	// Abstract methods for user actions. Actual implementation will be done in
	// extended class
	protected abstract void searchDetails() throws FocusException.NoDataFoundException;
	
	protected abstract void resetSearchDetails();
	
	protected abstract void addDetails();
	
	protected abstract void editDetails();
	
	protected abstract void validateDetails() throws FocusException.ValidationException;
	
	protected abstract void saveDetails() throws FocusException.SaveException, FileNotFoundException, IOException;
	
	protected abstract void cancelDetails();
	
	public abstract void downloadDetails();
	
	protected abstract void resetFields();
	
	// this method reset the visibility property of the button and layout based
	// on user actions
	@Override
	public void buttonClick(ClickEvent event) {
		wndReportPopup.close();
		if (btnAdd == event.getButton()) {
			hlUserIPContainer.setVisible(true);
			hlUserIPContainer.setEnabled(true);
			hlSrchContainer.setVisible(false);
			btnSave.setVisible(true);
			btnCancel.setVisible(true);
			btnSearch.setVisible(false);
			btnReset.setVisible(false);
			btnScreenName.setVisible(true);
			btnAdd.setEnabled(false);
			hlUserIPContainer.removeAllComponents();
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
			// Dummy implementation, actual will be implemented in extended
			// class
			addDetails();
		} else if (btnEdit == event.getButton()) {
			hlUserIPContainer.setVisible(true);
			hlUserIPContainer.setEnabled(true);
			hlSrchContainer.setVisible(false);
			btnSave.setVisible(true);
			btnCancel.setVisible(true);
			btnSearch.setVisible(false);
			btnEdit.setEnabled(false);
			btnAdd.setEnabled(false);
			btnReset.setVisible(false);
			btnScreenName.setVisible(true);
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
			hlUserIPContainer.removeAllComponents();
			// Dummy implementation, actual will be implemented in extended
			// class
			editDetails();
		} else if (btnSave == event.getButton()) {
			try {
				// Before save invoke the business validations.
				validateDetails();
				// After successful validation invokes the save method.
				saveDetails();
				hlSrchContainer.setVisible(false);
			}
			catch (Exception e) {
				try {
					e.printStackTrace();
					throw new FocusException.SaveException();
				}
				catch (SaveException e1) {
					/*
					 * logger.error("Institution ID : " + UI.getCurrent().getSession
					 * ().getAttribute("loginInstitutionId").toString() + " | User Name : " +
					 * UI.getCurrent().getSession().getAttribute ("loginuserName").toString() + " > " + "Exception " +
					 * e1.getMessage());
					 */e1.printStackTrace();
				}
			}
		} else if (btnCancel == event.getButton()) {
			hlUserIPContainer.setVisible(false);
			hlSrchContainer.setVisible(true);
			btnSave.setVisible(false);
			btnCancel.setVisible(false);
			btnSearch.setVisible(true);
			btnReset.setVisible(true);
			btnEdit.setEnabled(false);
			btnAdd.setEnabled(true);
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
			hlSrchContainer.setEnabled(true);
			vlSrchRsltContainer.removeAllComponents();
			vlSrchRsltContainer.addComponent(tblMstScrSrchRslt);
			vlSrchRsltContainer.setExpandRatio(tblMstScrSrchRslt, 1);
			tblMstScrSrchRslt.setValue(null);
			// Dummy implementation, actual will be implemented in extended
			// class
			cancelDetails();
		} else if (btnDownload == event.getButton()) {
			downloadDetails();
		} else if (btnScreenName == event.getButton()) {
			hlUserIPContainer.setVisible(false);
			hlUserIPContainer.setEnabled(true);
			hlSrchContainer.setVisible(true);
			btnSave.setVisible(false);
			btnCancel.setVisible(false);
			btnSearch.setVisible(true);
			btnReset.setVisible(true);
			btnEdit.setEnabled(false);
			btnAdd.setEnabled(true);
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
			hlSrchContainer.setEnabled(true);
			vlSrchRsltContainer.removeAllComponents();
			vlSrchRsltContainer.addComponent(tblMstScrSrchRslt);
			vlSrchRsltContainer.setExpandRatio(tblMstScrSrchRslt, 1);
			// Dummy implementation, actual will be implemented in extended
			// class
			cancelDetails();
		} else if (btnSearch == event.getButton()) {
			// Dummy implementation, actual will be implemented in extended
			// class
			tblMstScrSrchRslt.removeAllItems();
			try {
				searchDetails();
			}
			catch (Exception e) {
				logger.warn("btnSearch click -->", e);
			}
		} else if (btnReset == event.getButton()) {
			// Dummy implementation, actual will be implemented in extended
			// class
			hlUserIPContainer.setVisible(false);
			hlSrchContainer.setVisible(true);
			btnSave.setVisible(false);
			btnCancel.setVisible(false);
			btnSearch.setVisible(true);
			btnReset.setVisible(true);
			btnEdit.setEnabled(false);
			btnAdd.setEnabled(true);
			lblNotification.setIcon(null);
			lblNotification.setCaption("");
			hlSrchContainer.setEnabled(true);
			vlSrchRsltContainer.removeAllComponents();
			vlSrchRsltContainer.addComponent(tblMstScrSrchRslt);
			vlSrchRsltContainer.setExpandRatio(tblMstScrSrchRslt, 1);
			tblMstScrSrchRslt.setValue(null);
			resetSearchDetails();
		}
	}
}
