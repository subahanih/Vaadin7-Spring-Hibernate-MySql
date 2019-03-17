package com.ninox.focus.view.components;

import java.text.SimpleDateFormat;
import java.util.Locale;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.PopupDateField;

public class FocusPopupDateField extends PopupDateField {
	private static final long serialVersionUID = 1L;
	
	public FocusPopupDateField(String caption) {
		setWidth("150px");
		setCaption(caption);
		setDateFormat("dd-MMM-yyyy");
		addBlurListener(new BlurListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void blur(BlurEvent event) {
				// TODO Auto-generated method stub
				try {
					if (getValue() != null) {
						new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(getValue().toString());
					}
				}
				catch (Exception e) {
					try {
						if (getValue() != null) {
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH)
									.parse(getValue().toString());
						}
					}
					catch (Exception e1) {
						setComponentError(new UserError("Date format not recongnized"));
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
