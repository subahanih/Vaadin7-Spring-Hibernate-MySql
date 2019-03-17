/**
 * File Name 		: Dashboard.java 
 * Description 		: This class is used for Dashboard screen. 
 * Author 			: Mahaboob Subahan J
 * Date 			: 07-Mar-2015
 * 
 * This Software is Designed and Developed by Ninox Software Solutions Pvt. Ltd India.
 * 
 * Version     Date           Modified By             Remarks
 * 0.1         07-Mar-2015    Mahaboob Subahan J      Initial Version
 */
package com.ninox.focus.view;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Dashboard {
	private HorizontalLayout vlMainLayout;
	private Label lblFormTittle;
	
	public Dashboard() {
		
		VerticalLayout clMainLayout = (VerticalLayout) UI.getCurrent().getSession().getAttribute("clLayout");
		HorizontalLayout hlHeader = (HorizontalLayout) UI.getCurrent().getSession().getAttribute("hlLayout");
		buildView(clMainLayout, hlHeader);
	}
	
	private void buildView(VerticalLayout clMainLayout, HorizontalLayout hlHeader) {
	//	hlHeader.removeAllComponents();
		clMainLayout.removeAllComponents();
		lblFormTittle = new Label();
		lblFormTittle.setContentMode(ContentMode.HTML);
		lblFormTittle.setValue("&nbsp;&nbsp;<b>" + "Dashboard");
		VerticalLayout vlchart = new VerticalLayout();
		vlMainLayout = new HorizontalLayout();
		vlMainLayout.setWidth("100%");
		vlMainLayout.setSpacing(true);
		vlMainLayout.setMargin(true);
		vlMainLayout.addComponent(vlchart);
		vlMainLayout.setExpandRatio(vlchart, 1);
		clMainLayout.addComponent(vlMainLayout);
		hlHeader.addComponent(lblFormTittle);
		hlHeader.setComponentAlignment(lblFormTittle, Alignment.MIDDLE_LEFT);
	}
}
