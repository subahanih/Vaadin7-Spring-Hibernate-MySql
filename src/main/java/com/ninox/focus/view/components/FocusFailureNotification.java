package com.ninox.focus.view.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class FocusFailureNotification {
	public FocusFailureNotification(String crystalMMCons) {
		Label lblNotification = (Label) UI.getCurrent().getSession().getAttribute("lblNotification");
		if (lblNotification != null) {
			lblNotification.setIcon(new ThemeResource("img/failure.png"));
			lblNotification.setCaption(crystalMMCons);
		}
	}
}
