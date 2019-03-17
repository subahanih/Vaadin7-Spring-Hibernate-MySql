package com.ninox.focus.view.components;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

public class FocusPanelGenerator {
	public static CssLayout createPanel(Component content) {
		CssLayout panel = new CssLayout();
		panel.addStyleName("layout-panel");
		panel.setSizeFull();
		panel.addComponent(content);
		return panel;
	}
}
