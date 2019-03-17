package com.ninox.focus.view.base;

import java.util.List;
import java.util.Locale;
import javax.servlet.annotation.WebServlet;
import com.ninox.focus.domain.ApprovalSchemaDM;
import com.ninox.focus.domain.CompanyDM;
import com.ninox.focus.domain.EmployeeDM;
import com.ninox.focus.domain.UserDM;
import com.ninox.focus.domain.UserLoginDM;
import com.ninox.focus.service.ApprovalSchemaService;
import com.ninox.focus.service.CompanyService;
import com.ninox.focus.service.EmployeeService;
import com.ninox.focus.service.UserLoginService;
import com.ninox.focus.service.UserService;
import com.ninox.focus.util.DateUtils;
import com.ninox.focus.view.Dashboard;
import com.ninox.focus.view.util.CryptoUtil;
import com.ninox.focus.view.util.ImageUI;
import com.ninox.focus.view.util.SpringContextHelper;
import com.ninox.focus.view.util.UploadUI;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


@Theme("crystal-mm")
@Title("Ninox")
@SuppressWarnings("serial")
@PreserveOnRefresh
public class Login extends UI implements ItemClickListener, MouseEvents.ClickListener {
	private UserService serviceAppUser = null;
	private UserLoginService serviceUserLogin = null;
	private ApprovalSchemaService serviceApprovalSchema = null;
	private CompanyService serviceCompany = null;
	private EmployeeService serviceEmployee = null;
	private List<ApprovalSchemaDM> approvalSchemaList = null;
	private List<CompanyDM> companyList = null;
	private VerticalLayout vlRoot = new VerticalLayout();
	private VerticalLayout vlLoginPanel, vlTreeLayout, vlLocal, vlErrorpanel, vlUserPhotoName, vlBottom, vlMiddle,
			vlTop, vlClientLogo;
	private VerticalLayout clArgumentLayout = new VerticalLayout();
	private HorizontalLayout hlHeader, hlTreeMenu, hlCollapse, hlExpand, hlScreenName, hlTitle, hlFooterRight,
			hlFooterLayout, hlHeaderLeft, hlMenu, hlMiddleContent, hlUserSettingsComponents;
	private CssLayout clContent = new CssLayout();
	private HorizontalLayout hlUserPhoto = new HorizontalLayout();
	private HorizontalLayout hlUserSetttingsPhoto = new HorizontalLayout();
	private FormLayout flFields;
	private TextField tfUsername, tfDisplayName;
	private PasswordField pfPassword, pfOldPassword, pfNewPassword, pfConfirmPassword;
	private Button btnSignin, btnUserSettings;
	private Window winUserSettings = new Window();
	private Tree treeSettings, treePurchase, treeSales, treeExpense, treeReports;
	private Accordion accordion;
	private Image imgGlob, imgProfile, imgSingOut, imgExpand, imgCollapse, imageMenu;
	private Label lblProdectName, lblLogin, lblerror;
	private Label lblCurrentScreenName;
	
	@SuppressWarnings("unused")
	private String loginuserName = "", loginId = "", userPassword = "", screenName,
			companyName = "", sessionId, clientIP, loginUserRole;
	private Long loginCompanyId, appScreenId, userId, userLoginId, loginEmployeeId;
	private boolean menuPressed = false;
	public static Window settingsWindow = new Window();
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Login.class, widgetset = "com.ninox.focus.view.base.AppWidgetSet")
	public static class Servlet extends VaadinServlet {}
	
	public CssLayout layout = new CssLayout();
	public CssLayout mainView = new CssLayout();
	
	@Override
	protected void init(VaadinRequest request) {
		try {
			new SpringContextHelper();
			serviceUserLogin = (UserLoginService) SpringContextHelper.getBean("userLogin");
			serviceAppUser = (UserService) SpringContextHelper.getBean("appUser");
			serviceApprovalSchema = (ApprovalSchemaService) SpringContextHelper.getBean("approvalSchema");
			serviceEmployee = (EmployeeService) SpringContextHelper.getBean("employee");
			serviceCompany = (CompanyService) SpringContextHelper.getBean("company");
			setLocale(Locale.US);
			setContent(vlRoot);
			vlRoot.setSizeFull();
			Label bg = new Label();
			bg.setSizeUndefined();
			bg.addStyleName("login-bg");
			buildLoginView(false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buildLoginView(boolean exit) {
		if (exit) {
			vlRoot.removeAllComponents();
		}
		imgGlob = new Image(null, new ThemeResource("img/ninox.png"));
		imgGlob.setWidth("200px");
		imgGlob.setHeight("50px");
		imgGlob.setStyleName("imgglob");
		vlTop = new VerticalLayout();
		vlTop.setWidth("100%");
		vlTop.setHeight("100px");
		vlTop.setStyleName("login-top");
		vlTop.addComponent(imgGlob);
		vlTop.setComponentAlignment(imgGlob, Alignment.MIDDLE_RIGHT);
		vlClientLogo = new VerticalLayout();
		hlMiddleContent = new HorizontalLayout();
		lblLogin = new Label("Login");
		lblLogin.addStyleName("h4");
		lblLogin.setSizeUndefined();
		tfUsername = new TextField(":");
		tfUsername.setInputPrompt("Username");
		tfUsername.setWidth("250");
		tfUsername.setHeight("23");
		tfUsername.setValue("admin");
		// tfUsername.focus();
		tfUsername.addStyleName("username");
		tfUsername.addStyleName("username-textfield");
		tfUsername.addStyleName("username-textinput");
		tfUsername.setIcon(new ThemeResource("img/user.png"));
		pfPassword = new PasswordField(":");
		pfPassword.setInputPrompt("Password");
		pfPassword.setIcon(new ThemeResource("img/password.png"));
		pfPassword.setWidth("250");
		pfPassword.setHeight("23");
		pfPassword.setValue("admin");
		pfPassword.addStyleName("password");
		pfPassword.addStyleName("username-textfield");
		btnSignin = new Button("Sign In");
		btnSignin.addStyleName("default");
		final ShortcutListener enter = new ShortcutListener("Sign In", KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				btnSignin.click();
			}
		};
		btnSignin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				verifyLoginUser();
			}
		});
		vlErrorpanel = new VerticalLayout();
		btnSignin.addShortcutListener(enter);
		flFields = new FormLayout();
		flFields.setMargin(true);
		flFields.setSpacing(true);
		flFields.addComponent(tfUsername);
		flFields.addComponent(pfPassword);
		flFields.addComponent(btnSignin);
		flFields.addComponent(vlErrorpanel);
		vlLoginPanel = new VerticalLayout();
		vlLoginPanel.setMargin(true);
		vlLoginPanel.setWidth("50%");
		vlLoginPanel.setHeight("30%");
		vlLoginPanel.addComponent(lblLogin);
		vlLoginPanel.addComponent(flFields);
		hlMiddleContent.setSizeFull();
		hlMiddleContent.addComponent(vlClientLogo);
		hlMiddleContent.addComponent(vlLoginPanel);
		hlMiddleContent.setComponentAlignment(vlLoginPanel, Alignment.MIDDLE_CENTER);
		vlMiddle = new VerticalLayout();
		vlMiddle.setSizeFull();
		vlMiddle.setStyleName("login-middle");
		vlMiddle.addComponent(hlMiddleContent);
		lblProdectName = new Label("Copyright © 2018 Ninox Business Accelerator");
		lblProdectName.addStyleName("h8");
		lblProdectName.setWidth("100%");
		lblProdectName.setHeight("25%");
		vlBottom = new VerticalLayout();
		vlBottom.setWidth("100%");
		vlBottom.setHeight("100px");
		vlBottom.setStyleName("login-bottom");
		vlBottom.addComponent(lblProdectName);
		vlBottom.setComponentAlignment(lblProdectName, Alignment.BOTTOM_CENTER);
		vlRoot.addComponent(vlTop);
		vlRoot.addComponent(vlMiddle);
		vlRoot.setExpandRatio(vlMiddle, 1);
		vlRoot.addComponent(vlBottom);
		vlRoot.setComponentAlignment(vlBottom, Alignment.BOTTOM_CENTER);
		lblerror = new Label();
		lblerror.addStyleName("error");
		lblerror.setSizeUndefined();
		lblerror.addStyleName("light");
		// Add animation
		lblerror.addStyleName("v-animate-reveal");
		lblerror.addStyleName("h14");
		lblerror.setValue("");
	}
	
	private void buildMainView() {
		vlRoot.removeAllComponents();
		vlRoot.setVisible(true);
		imageMenu = new Image(null, new ThemeResource("img/menu.png"));
		imageMenu.setWidth("50px");
		imageMenu.setHeight("30px");
		imageMenu.setDescription("Menu");
		imageMenu.addClickListener(this);
		imageMenu.setStyleName("imgmenu");
		imageMenu.setImmediate(true);
		imgGlob = new Image(null, new ThemeResource("img/globe.png"));
		imgGlob.setWidth("60px");
		imgGlob.setHeight("65px");
		imgGlob.addClickListener(this);
		imgGlob.setImmediate(true);
		imgProfile = new Image(null, new ThemeResource("img/settings.png"));
		imgProfile.setWidth("30px");
		imgProfile.setHeight("30px");
		imgProfile.setDescription("Settings");
		imgProfile.setStyleName("imgsettings");
		imgProfile.addClickListener(this);
		imgProfile.setImmediate(true);
		imgSingOut = new Image(null, new ThemeResource("img/power.png"));
		imgSingOut.setDescription("Sign Out");
		imgSingOut.setWidth("30px");
		imgSingOut.setHeight("30px");
		imgSingOut.addClickListener(this);
		imgSingOut.setStyleName("imgsignout");
		imgSingOut.setImmediate(true);
		// add fields to header layout
		hlHeader = new HorizontalLayout();
		hlHeader.setWidth("100%");
		hlHeader.setHeight("75px");
		hlHeader.addStyleName("topbarone");
		hlHeaderLeft = new HorizontalLayout();
		hlHeaderLeft.setStyleName("topbarfive");
		hlHeaderLeft.setWidth("250px");
		hlHeaderLeft.setHeight("100%");
		hlHeader.addComponent(hlHeaderLeft);
		hlMenu = new HorizontalLayout();
		hlMenu.addComponent(imageMenu);
		hlHeaderLeft.addComponent(hlMenu);
		hlHeaderLeft.setComponentAlignment(hlMenu, Alignment.MIDDLE_CENTER);
		hlScreenName = new HorizontalLayout();
		hlScreenName.setHeight("30px");
		hlScreenName.setWidth("100%");
		hlScreenName.addStyleName("topbar");
		lblCurrentScreenName = new Label();
		hlScreenName.addComponent(lblCurrentScreenName);
		hlScreenName.setComponentAlignment(lblCurrentScreenName, Alignment.MIDDLE_LEFT);
		hlTitle = new HorizontalLayout();
		hlTitle.setHeight("30px");
		hlTitle.setWidth("100%");
		hlTitle.addStyleName("topbar");
		hlTitle.addComponent(hlScreenName);
		hlTitle.setComponentAlignment(hlScreenName, Alignment.MIDDLE_LEFT);
		hlTitle.setExpandRatio(hlScreenName, 1);
		Label userName = new Label(loginuserName);
		userName.setContentMode(ContentMode.HTML);
		userName.setValue("<font size=\"2\" color=\"#FFFF00\">" + loginuserName + "</font>");
		userName.setStyleName("h27");
		hlUserPhoto.setHeight("100px");
		hlUserPhoto.setWidth("100px");
		hlUserPhoto.setStyleName("user-photo");
		vlUserPhotoName = new VerticalLayout();
		vlUserPhotoName.setWidth("100%");
		vlUserPhotoName.setMargin(true);
		vlUserPhotoName.addComponent(hlUserPhoto);
		vlUserPhotoName.addComponent(userName);
		vlUserPhotoName.setComponentAlignment(hlUserPhoto, Alignment.TOP_CENTER);
		vlUserPhotoName.setComponentAlignment(userName, Alignment.BOTTOM_CENTER);
		accordion = new Accordion();
		accordion.setWidth("100%");
		accordion.setHeight("145%");
		accordion.setImmediate(true);
		vlTreeLayout = new VerticalLayout();
		vlTreeLayout.addStyleName("sidebaraccordian");
		vlTreeLayout.addComponent(vlUserPhotoName);
		vlTreeLayout.addComponent(accordion);
		vlTreeLayout.setComponentAlignment(accordion, Alignment.BOTTOM_CENTER);
		vlTreeLayout.setWidth("250px");
		vlTreeLayout.setHeight("100%");
		vlLocal = new VerticalLayout();
		vlLocal.setStyleName("sidebar");
		vlLocal.addComponent(hlTitle);
		vlLocal.addComponent(clContent);
		vlLocal.setExpandRatio(clContent, 1);
		hlTreeMenu = new HorizontalLayout();
		hlTreeMenu.setSizeFull();
		hlTreeMenu.addComponent(vlTreeLayout);
		hlTreeMenu.addComponent(vlLocal);
		hlTreeMenu.setExpandRatio(vlLocal, 1);
		hlFooterLayout = new HorizontalLayout();
		hlFooterLayout.setStyleName("topbartwo");
		hlFooterLayout.setWidth("100%");
		hlFooterLayout.setHeight("25px");
		final Label lblNinoxName = new Label("Powered by - Ninox Software Solutions. India | www.ninox.co.in");
		lblNinoxName.addStyleName("h18");
		final Label lblTime = new Label();
		lblTime.setValue("This Software Is Licenced To Selendang Netcafe Malaysia");
		lblTime.addStyleName("h18");
		hlFooterRight = new HorizontalLayout();
		hlFooterRight.setSpacing(true);
		hlFooterRight.addComponent(lblTime);
		HorizontalLayout hlDaemonName = new HorizontalLayout();
		hlDaemonName.setSpacing(true);
		hlDaemonName.addComponent(lblNinoxName);
		hlFooterLayout.addComponent(hlDaemonName);
		hlFooterLayout.setComponentAlignment(hlDaemonName, Alignment.MIDDLE_LEFT);
		hlFooterLayout.addComponent(hlFooterRight);
		hlFooterLayout.setComponentAlignment(hlFooterRight, Alignment.MIDDLE_RIGHT);
		HorizontalLayout logoHl = new HorizontalLayout();
		Label lblCompanyNameMM = new Label(
				"<p align=\"right\"><font size=\"4\"color=\"#ffffff\"><b>"+ companyName +"</b></font></p>",
				ContentMode.HTML);
		lblCompanyNameMM.setContentMode(ContentMode.HTML);
		logoHl.addComponent(imgGlob);
		logoHl.setComponentAlignment(imgGlob, Alignment.TOP_LEFT);
		logoHl.addComponent(lblCompanyNameMM);
		logoHl.setComponentAlignment(lblCompanyNameMM, Alignment.MIDDLE_RIGHT);
		hlHeader.addComponent(logoHl);
		hlHeader.setComponentAlignment(logoHl, Alignment.MIDDLE_LEFT);
		hlHeader.setExpandRatio(logoHl, 1);
		HorizontalLayout userLayout = new HorizontalLayout();
		hlHeader.addComponent(userLayout);
		userLayout.setSpacing(true);
		userLayout.setMargin(true);
		userLayout.addComponent(imgProfile);
		userLayout.addComponent(imgSingOut);
		userLayout.setComponentAlignment(imgSingOut, Alignment.MIDDLE_CENTER);
		hlHeader.setComponentAlignment(userLayout, Alignment.MIDDLE_RIGHT);
		// root layout
		vlRoot.addComponent(hlHeader);
		vlRoot.addComponent(hlTreeMenu);
		vlRoot.setExpandRatio(hlTreeMenu, 1);
		vlRoot.addComponent(hlFooterLayout);
		vlRoot.setComponentAlignment(hlFooterLayout, Alignment.BOTTOM_CENTER);
	}
	
	private void loadUserSettingsComponents() {
		tfDisplayName = new TextField("Display Name");
		tfDisplayName.setWidth("200px");
		tfDisplayName.setValue(loginuserName);
		pfOldPassword = new PasswordField("Old Password");
		pfOldPassword.setWidth("200px");
		pfNewPassword = new PasswordField("New Password");
		pfNewPassword.setWidth("200px");
		pfConfirmPassword = new PasswordField("Confirm Password");
		pfConfirmPassword.setWidth("200px");
		btnUserSettings = new Button();
		btnUserSettings.setWidth("80px");
		btnUserSettings.setHeight("25px");
		btnUserSettings.addStyleName("savebt");
		FormLayout flFields = new FormLayout();
		FormLayout flUserSetttingsPhoto = new FormLayout();
		flFields.addComponent(tfDisplayName);
		flFields.addComponent(pfOldPassword);
		flFields.addComponent(pfNewPassword);
		flFields.addComponent(pfConfirmPassword);
		flFields.addComponent(btnUserSettings);
		flFields.setSpacing(true);
		flFields.setMargin(true);
		flUserSetttingsPhoto.addComponent(hlUserSetttingsPhoto);
		flUserSetttingsPhoto.setMargin(true);
		flUserSetttingsPhoto.setSpacing(true);
		flUserSetttingsPhoto.setWidth("100px");
		hlUserSettingsComponents = new HorizontalLayout();
		hlUserSettingsComponents.addComponent(flFields);
		hlUserSettingsComponents.addComponent(flUserSetttingsPhoto);
		hlUserSettingsComponents.setSpacing(true);
		hlUserSettingsComponents.setMargin(true);
		hlUserSettingsComponents.setSizeFull();
		UI.getCurrent().removeWindow(winUserSettings);
		winUserSettings.setWidth("50%");
		winUserSettings.setHeight("35%");
		winUserSettings.center();
		winUserSettings.setDraggable(false);
		winUserSettings.setResizable(false);
		winUserSettings.setImmediate(true);
		winUserSettings.setCaption("User Settings");
		UI.getCurrent().addWindow(winUserSettings);
		winUserSettings.setContent(hlUserSettingsComponents);
		List<EmployeeDM> editEmployeelist = serviceEmployee.getEmployeeList(loginEmployeeId, null, null, null,
				 null, "Active", null);
		for (EmployeeDM employeeObj : editEmployeelist) {
			if (employeeObj.getEmpPhoto() != null) {
				hlUserSetttingsPhoto.removeAllComponents();
				byte[] myimage = (byte[]) employeeObj.getEmpPhoto();
				UploadUI test = new UploadUI(hlUserSetttingsPhoto);
				test.dispayImage(myimage, employeeObj.getFirstName());
			} else {
				new UploadUI(hlUserSetttingsPhoto);
			}
		}
		btnUserSettings.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				saveUserSettings();
			}
		});
	}
	
	private void saveUserSettings() {
	}
	
	private void verifyLoginUser() {
		try {
			String loginUserId = tfUsername.getValue().toString();
			String loginPassword = pfPassword.getValue().toString();
			String ep = new CryptoUtil().encryptString(loginPassword);
			boolean valid = false;
			List<UserDM> appUserList = serviceAppUser.getUserDMList(null, null, null, loginUserId, ep, "Active", null);
			if (appUserList.size() != 0) {
				for (UserDM appUserObj : appUserList) {
					loginCompanyId = appUserObj.getCompanyId();
					loginuserName = appUserObj.getUserName();
					loginId = appUserObj.getLoginId();
					userPassword = appUserObj.getLoginPassword();
					loginUserRole = appUserObj.getUserRole();
					loginEmployeeId = appUserObj.getEmpoloyeeId();
					userId = appUserObj.getUserId();
					VaadinSession vSession = UI.getCurrent().getSession();
					WrappedSession wSession = vSession.getSession();
					sessionId = wSession.getId();
					clientIP = Page.getCurrent().getWebBrowser().getAddress();
					valid = true;
					if (valid) {
						String userRoleIdScreenLoading;
						clArgumentLayout.removeAllComponents();
						clArgumentLayout.setMargin(new MarginInfo(true, true, false, true));
						lblerror.setValue("");
						if (loginUserRole == "Admin") {
							userRoleIdScreenLoading = null;
						} else {
							userRoleIdScreenLoading = loginUserRole;
						}
						//Load company details
						companyList = serviceCompany.getCompanyList(null, "SELENDANG NETCAFE", "Active");
						for(CompanyDM companyObj : companyList) {
							companyName = companyObj.getCompanyName();
							System.out.println(companyName);
						}
						// Create the tree nodes
						System.out.println("userRoleIdScreenLoading : " + userRoleIdScreenLoading);
						approvalSchemaList = serviceApprovalSchema.getApprovalSchemaList(null, loginCompanyId, null,
								userRoleIdScreenLoading, "Active", userId, true);
						buildMainView();
						hlUserPhoto.removeAllComponents();
						if (appUserObj.getEmpPhoto() != null) {
							byte[] myimage = (byte[]) appUserObj.getEmpPhoto();
							ImageUI test = new ImageUI(hlUserPhoto);
							test.dispayImage(myimage, appUserObj.getUserName());
						} else {
							new ImageUI(hlUserPhoto);
						}
						System.out.println(companyName);
						treeSettings = new Tree();
						treeSettings.addStyleName("no-children");
						treeSettings.addItemClickListener(this);
						treeSettings.setImmediate(true);
						treePurchase = new Tree();
						treePurchase.addStyleName("no-children");
						treePurchase.addItemClickListener(this);
						treePurchase.setImmediate(true);
						treeSales = new Tree();
						treeSales.addStyleName("no-children");
						treeSales.addItemClickListener(this);
						treeSales.setImmediate(true);
						treeExpense = new Tree();
						treeExpense.addStyleName("no-children");
						treeExpense.addItemClickListener(this);
						treeExpense.setImmediate(true);
						treeReports = new Tree();
						treeReports.addStyleName("no-children");
						treeReports.addItemClickListener(this);
						treeReports.setImmediate(true);
						accordion.addTab(treeSettings, "Settings", new ThemeResource("img/manage.png"));
						accordion.addTab(treePurchase, "Purchase", new ThemeResource("img/hr.png"));
						accordion.addTab(treeSales, "Sales", new ThemeResource("img/iconmonstr.png"));
						accordion.addTab(treeExpense, "Expense", new ThemeResource("img/iconmonstr.png"));
						accordion.addTab(treeReports, "Reports", new ThemeResource("img/bank.png"));
						// Save User Login Details
						UserLoginDM userLoginObj = new UserLoginDM();
						userLoginObj.setUserId(userId);
						userLoginObj.setCompanyId(loginCompanyId);
						userLoginObj.setLoginDate(DateUtils.getcurrentdate());
						userLoginObj.setClientIp(clientIP);
						userLoginObj.setSessionId(sessionId);
						serviceUserLogin.saveOrUpdateUserLogin(userLoginObj);
						userLoginId = userLoginObj.getSessionloginId();
						// To load active screen name in tree menu
						try {
							for (ApprovalSchemaDM appScreensObj : approvalSchemaList) {
								if (appScreensObj.getParentName().toString().equals("Settings")) {
									treeSettings.addItem(appScreensObj.getScreenDesc());
									if (appScreensObj.getParentName() != null) {
										treeSettings.setParent(appScreensObj.getScreenDesc(),
												appScreensObj.getParentName());
										if (appScreensObj.getScreenDesc() == null) {
											treeSettings.setChildrenAllowed(appScreensObj.getScreenDesc(),
													true);
										} else {
											treeSettings.setChildrenAllowed(appScreensObj.getScreenDesc(),
													false);
										}
									}
								}
								if (appScreensObj.getParentName().toString().equals("Purchase")) {
									treePurchase.addItem(appScreensObj.getScreenDesc());
									if (appScreensObj.getParentName() != null) {
										treePurchase.setParent(appScreensObj.getScreenDesc(),
												appScreensObj.getParentName());
										if (appScreensObj.getScreenDesc() == null) {
											treePurchase
													.setChildrenAllowed(appScreensObj.getScreenDesc(), true);
										} else {
											treePurchase.setChildrenAllowed(appScreensObj.getScreenDesc(),
													false);
										}
									}
								}
								if (appScreensObj.getParentName().toString().equals("Sales")) {
									treeSales.addItem(appScreensObj.getScreenDesc());
									if (appScreensObj.getParentName() != null) {
										treeSales.setParent(appScreensObj.getScreenDesc(),
												appScreensObj.getParentName());
										if (appScreensObj.getScreenDesc() == null) {
											treeSales.setChildrenAllowed(appScreensObj.getScreenDesc(), true);
										} else {
											treeSales.setChildrenAllowed(appScreensObj.getScreenDesc(), false);
										}
									}
								}
								if (appScreensObj.getParentName().toString().equals("Expense")) {
									treeExpense.addItem(appScreensObj.getScreenDesc());
									if (appScreensObj.getParentName() != null) {
										treeExpense.setParent(appScreensObj.getScreenDesc(),
												appScreensObj.getParentName());
										if (appScreensObj.getScreenDesc() == null) {
											treeExpense.setChildrenAllowed(appScreensObj.getScreenDesc(), true);
										} else {
											treeExpense.setChildrenAllowed(appScreensObj.getScreenDesc(), false);
										}
									}
								}
								if (appScreensObj.getParentName().toString().equals("Report")) {
									treeReports.addItem(appScreensObj.getScreenDesc());
									if (appScreensObj.getParentName() != null) {
										treeReports.setParent(appScreensObj.getScreenDesc(),
												appScreensObj.getParentName());
										if (appScreensObj.getScreenDesc() == null) {
											treeReports.setChildrenAllowed(appScreensObj.getScreenDesc(), true);
										} else {
											treeReports.setChildrenAllowed(appScreensObj.getScreenDesc(), false);
										}
									}
								}
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						UI.getCurrent().getSession().setAttribute("hlLayout", hlScreenName);
						UI.getCurrent().getSession().setAttribute("clLayout", clArgumentLayout);
						UI.getCurrent().getSession().setAttribute("userName", loginuserName);
						UI.getCurrent().getSession().setAttribute("userRole", loginUserRole);
						UI.getCurrent().getSession().setAttribute("companyId", loginCompanyId);
						UI.getCurrent().getSession().setAttribute("companyName", companyName);
						UI.getCurrent().getSession().setAttribute("loginEmployeeId", loginEmployeeId);
						clContent.removeAllComponents();
						new Dashboard();
						clContent.addComponent(clArgumentLayout);
					}
				}
			} else {
				lblerror.setValue("Invalid User Id and Password");
				vlErrorpanel.removeAllComponents();
				vlErrorpanel.addComponent(lblerror);
				tfUsername.focus();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			tfUsername.focus();
		}
	}
	
	private void loadScreenCode() {
		lblCurrentScreenName.setVisible(false);
		clArgumentLayout.removeAllComponents();
		clArgumentLayout.removeStyleName("dashboard-view");
		clArgumentLayout.setStyleName("padding : 0");
		UI.getCurrent().getSession().setAttribute("screenName", screenName);
		System.out.println("screen Name : " + screenName);
		String targetClassPath = null;
		try {
			for (ApprovalSchemaDM approvalScmObj : approvalSchemaList) {
				if (approvalScmObj.getScreenDesc().equals(screenName)) {
					targetClassPath = approvalScmObj.getTargetClass();
					appScreenId = approvalScmObj.getScreenId();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		UI.getCurrent().getSession().setAttribute("appScreenId", appScreenId);
		System.out.println("Target Class : " + targetClassPath);
		Class<?> c = null;
		Object b = null;
		try {
			c = Class.forName(targetClassPath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			b = c.newInstance();
			c.isInstance(b);// invoke(b);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		clContent.addComponent(clArgumentLayout);
	}
	
	@Override
	public void click(ClickEvent event) {
		if (imgExpand == event.getComponent()) {
			for (Object itemId : treeSettings.getItemIds())
				treeSettings.expandItem(itemId);
			hlCollapse.setVisible(true);
			hlExpand.setVisible(false);
		}
		if (imgCollapse == event.getComponent()) {
			for (Object itemId : treeSettings.getItemIds()) {
				treeSettings.collapseItemsRecursively(itemId);
			}
			hlExpand.setVisible(true);
			hlCollapse.setVisible(false);
		}
		if (imgProfile == event.getComponent()) {
			loadUserSettingsComponents();
		}
		if (imgSingOut == event.getComponent()) {
			vlTreeLayout.removeAllComponents();
			hlTreeMenu.removeAllComponents();
			vlRoot.removeAllComponents();
			clContent.removeAllComponents();
			buildLoginView(false);
			serviceUserLogin.updateLogoutDateByMbaseUserLogin(userLoginId);
			lblerror.setValue("");
			loginuserName = null;
			userId = null;
			sessionId = "";
			clientIP = null;
			hlUserPhoto.removeAllComponents();
			UI.getCurrent().getSession().setAttribute("hlLayout", null);
			UI.getCurrent().getSession().setAttribute("clLayout", null);
			UI.getCurrent().getSession().setAttribute("userName", null);
			UI.getCurrent().getSession().setAttribute("userRole", null);
			UI.getCurrent().getSession().setAttribute("companyId", null);
			UI.getCurrent().getSession().setAttribute("companyName", null);
			UI.getCurrent().getSession().setAttribute("loginEmployeeId", null);
			winUserSettings.close();
			settingsWindow.close();
		}
		if (imageMenu == event.getComponent()) {
			if (menuPressed) {
				vlTreeLayout.setVisible(true);
				menuPressed = false;
			} else {
				vlTreeLayout.setVisible(false);
				menuPressed = true;
			}
		}
		if (imgGlob == event.getComponent()) {
			clContent.removeAllComponents();
			new Dashboard();
			clContent.addComponent(clArgumentLayout);
		}
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		screenName = event.getItemId().toString();
		loadScreenCode();
	}
}