package com.ninox.focus.view.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class FocusSuccessNotification {
	// public iERPSaveNotification() {
	public FocusSuccessNotification(String crystalCons) {
		Label lblNotification = (Label) UI.getCurrent().getSession().getAttribute("lblNotification");
		if (lblNotification != null) {
			lblNotification.setIcon(new ThemeResource("img/success_small.png"));
			// lblNotification.setCaption(iERPConstants.saveMsg);
			lblNotification.setCaption(crystalCons);
		}
	}
}
