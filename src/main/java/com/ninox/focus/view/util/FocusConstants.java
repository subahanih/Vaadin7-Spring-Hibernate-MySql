package com.ninox.focus.view.util;

import com.vaadin.server.VaadinService;

public class FocusConstants {
	public static final String SELECT_DEFAULT = "Select";
	public static final String SAVE_MSG = "Saved Successfully";
	public static final String UPDATED_MSG = "Updated Successfully";
	public static final String sameDataMsg = "This data is already exist";
	public static final String SAME_DATA_MSG_USER = "User already exist for this employee";

	public static final String dtFormatTableDisplay = "dd-MMM-yyyy HH:mm";
	public static final String dtFormatWeb = "dd-MMM-yyyy";

	public static final String IMAGE_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
			+ "/VAADIN/themes/crystal-mm/img/Upload.jpg";
	public static final String DOCUMENT_PATH = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
			+ "/VAADIN/themes/crystal-mm/img/Document.pdf";
	public static final String failedMsg = "Save Failed";
}
