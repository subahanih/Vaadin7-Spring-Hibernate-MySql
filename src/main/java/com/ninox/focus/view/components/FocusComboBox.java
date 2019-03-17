package com.ninox.focus.view.components;

import java.util.List;
import com.ninox.focus.domain.LookUpDM;
import com.ninox.focus.service.LookUpService;
import com.ninox.focus.view.util.FocusConstants;
import com.ninox.focus.view.util.SpringContextHelper;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.ComboBox;

public class FocusComboBox extends ComboBox {
	private static final long serialVersionUID = 1L;

	public FocusComboBox(String caption) {
		setWidth("150px");
		setHeight("22px");
		setCaption(caption);
		setInputPrompt(FocusConstants.SELECT_DEFAULT);
		setNullSelectionAllowed(false);
		setImmediate(true);
	}

	public FocusComboBox(String caption, String tablename, String code) {
		setWidth("150px");
		setHeight("22px");
		setCaption(caption);
		setInputPrompt(FocusConstants.SELECT_DEFAULT);
		setNullSelectionAllowed(false);
		setImmediate(true);
		LookUpService serviceLookup = (LookUpService) SpringContextHelper
				.getBean("lookup");
		BeanContainer<Long, LookUpDM> beanLookup = new BeanContainer<Long, LookUpDM>(
				LookUpDM.class);
		beanLookup.setBeanIdProperty("lookupName");
		List<LookUpDM> lookupList = serviceLookup.getLookUpDMList(null, code, null, null, null, "Active", tablename, null);
		beanLookup.addAll(lookupList);
		setContainerDataSource(beanLookup);
		setItemCaptionPropertyId("lookupName");

	}
}
