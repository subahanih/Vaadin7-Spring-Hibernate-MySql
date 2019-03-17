package com.ninox.focus.view.util;

import org.apache.log4j.Logger;
import com.ninox.focus.exception.FocusException;
import com.ninox.focus.view.Dashboard;
import com.ninox.focus.view.components.FocusButton;
import com.ninox.focus.view.components.FocusPanelGenerator;
import com.ninox.focus.view.components.FocusTable;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class BaseReportUI implements ClickListener {
	private static final long serialVersionUID = 1L;
	// Root container for all the page components.
	VerticalLayout hlPageRootContainter = (VerticalLayout) UI.getCurrent().getSession().getAttribute("clLayout");
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
	public Button btnAdd = new FocusButton("", "stylenull", this);
	public Button btnSearch = new FocusButton("", "searchbt", this);
	public Button btnReset = new FocusButton("", "resetbt", this);
	public Image imgExcel, imgPdf;
	// Search result table
	public Table tblMstScrSrchRslt = new FocusTable();
	// Other local components and variables
	String screenName = "";
	private Button btnScreenName;
	public Label lblNotification;
	private Logger logger = Logger.getLogger(BaseUI.class);
	public String loginInstitution, loginInstAddress, loginInstPhone, loginInstEmail;
	
	public BaseReportUI() {
		if (UI.getCurrent().getSession().getAttribute("screenName") != null) {
			screenName = UI.getCurrent().getSession().getAttribute("screenName").toString();
		}
		btnSearch.setWidth("80px");
		btnSearch.setHeight("25px");
		btnReset.setWidth("80px");
		btnReset.setHeight("25px");
		btnScreenName = new FocusButton(screenName, "link", this);
		imgExcel = new Image(null, new ThemeResource("img/green-xl-file.png"));
		imgExcel.setHeight("25px");
		imgExcel.setWidth("40px");
		imgExcel.addStyleName("imgexcel");
		// imgCsv.addClickListener(this);
		// imgCsv.addStyleName("imgcsv");
		imgPdf = new Image(null, new ThemeResource("img/red-pdf-file.png"));
		imgPdf.setHeight("25px");
		imgPdf.setWidth("40px");
		imgPdf.addStyleName("imgpdf");
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
		hlCmdBtnLayout.setHeight("35px");
		hlCmdBtnLayout.setWidth("100%");
		hlCmdBtnLayout.addComponent(imgExcel);
		hlCmdBtnLayout.addComponent(imgPdf);
		hlCmdBtnLayout.setExpandRatio(btnAdd, 1);
		hlCmdBtnLayout.addStyleName("topbarthree");
		hlCmdBtnLayout.setComponentAlignment(imgExcel, Alignment.MIDDLE_RIGHT);
		hlCmdBtnLayout.setComponentAlignment(imgPdf, Alignment.MIDDLE_RIGHT);
		hlCmdBtnLayout.setMargin(new MarginInfo(false, false, false, true));
		// Add child containers to the Root container
		hlPageRootContainter.addComponent(hlUserIPContainer);
		hlPageRootContainter.addComponent(hlSrchContainer);
		hlPageRootContainter.addComponent(FocusPanelGenerator.createPanel(hlCmdBtnLayout));
		hlPageRootContainter.addComponent(vlSrchRsltContainer);
		// Set the initial visibility property
		btnAdd.setEnabled(false);
		// Set Master screen search table properties and listener events
		tblMstScrSrchRslt.setImmediate(true);
		tblMstScrSrchRslt.setColumnReorderingAllowed(true);
		tblMstScrSrchRslt.setSelectable(false);
		tblMstScrSrchRslt.setMultiSelect(false);
		tblMstScrSrchRslt.setColumnCollapsingAllowed(true);
		vlSrchRsltContainer.addComponent(tblMstScrSrchRslt);
	}
	
	// Abstract methods for user actions. Actual implementation will be done in
	// extended class
	protected abstract void searchDetails() throws FocusException.NoDataFoundException;
	
	protected abstract void resetSearchDetails();
	
	protected abstract void resetFields();
	
	// this method reset the visibility property of the button and layout based
	// on user actions
	@Override
	public void buttonClick(ClickEvent event) {
		if (btnSearch == event.getButton()) {
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
			btnSearch.setVisible(true);
			btnReset.setVisible(true);
			btnAdd.setEnabled(true);
			hlSrchContainer.setEnabled(true);
			vlSrchRsltContainer.removeAllComponents();
			vlSrchRsltContainer.addComponent(tblMstScrSrchRslt);
			vlSrchRsltContainer.setExpandRatio(tblMstScrSrchRslt, 1);
			tblMstScrSrchRslt.setValue(null);
			resetSearchDetails();
		}
	}
	
}
