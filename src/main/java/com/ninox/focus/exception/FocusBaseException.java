package com.ninox.focus.exception;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class FocusBaseException extends Exception {
	// Declarations
	private static final long serialVersionUID = 1L;
	private String errorCd = "SYSTEM_ERROR";
	
	// Default constructor
	public FocusBaseException() {
		super();
	}
	
	// Constructor with error code passed. Based on the error code this display
	// the description in the notification area
	public FocusBaseException(String errCode) {
		this.errorCd = errCode;
		Label lblNotification = (Label) UI.getCurrent().getSession().getAttribute("lblNotification");
		if (lblNotification != null) {
			lblNotification.setIcon(new ThemeResource("img/failure.png"));
			lblNotification.setCaption(errCode);
		}
	}
	
	// Get method to get the error code
	public String getErrCode() {
		return errorCd;
	}
}
