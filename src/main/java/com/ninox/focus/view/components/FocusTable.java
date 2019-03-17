package com.ninox.focus.view.components;

import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Runo;

public class FocusTable extends Table {
	private static final long serialVersionUID = 1L;
	
	public FocusTable() {
		setStyleName(Runo.TABLE_SMALL);
		setSizeFull();
		setFooterVisible(true);
		setSelectable(true);
		setImmediate(true);
		setColumnCollapsingAllowed(false);
	}
}
