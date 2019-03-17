package com.ninox.focus.view.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@Scope("request")
public class UploadDocumentUI implements Serializable {
	private static final long serialVersionUID = 1L;
	private Embedded embedded;
	private String basepath;
	private String basepath1;
	private String basepath2;
	private VerticalLayout imgPanel = new VerticalLayout();
	Label lblFileName = new Label();
	
	public UploadDocumentUI(VerticalLayout layout) {
		basic(layout);
	}
	
	@SuppressWarnings("deprecation")
	private void basic(VerticalLayout layout) {
		layout.removeAllComponents();
		// imagelayout.removeAllComponents();
		UI.getCurrent().getSession().setAttribute("isDocumentUploaded", false);
		basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		basepath1 = basepath + "/VAADIN/themes/crystal-mm/img/Document.pdf";
		// BEGIN-EXAMPLE: component.upload.basic
		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("", null);
		upload.setButtonCaption("Attach");
		upload.setImmediate(true);
		Upload upp = new Upload();
		upp.setButtonCaption("Download");
		/*
		 * Upload upp=new Upload(); upp.setButtonCaption("Download");
		 */
		VerticalLayout panel = new VerticalLayout();
		panel.removeAllComponents();
		panel.addComponent(upload);
		panel.setComponentAlignment(upload, Alignment.TOP_CENTER);
		class ImageUploader implements Receiver, SucceededListener {
			private static final long serialVersionUID = -1276759102490466761L;
			public File file;
			
			// /imgPanel.removeAllComponents();
			public OutputStream receiveUpload(String filename, String mimeType) {
				// Create upload stream
				FileOutputStream fos = null; // Output stream to write to
				basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
				basepath1 = basepath + "/VAADIN/themes/crystal-mm/img/Document.pdf";
				try {
					// Open the file for writing.
					file = new File(basepath1);
					fos = new FileOutputStream(file);
				}
				catch (final java.io.FileNotFoundException e) {
					Notification.show("Could not open file<br/>", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
					return null;
				}
				return fos; // Return the output stream to write to
			}
			
			@SuppressWarnings("unused")
			public void uploadSucceeded(SucceededEvent event) {
				// Show the uploaded file in the image viewer
				try {
					File f = new File(event.getFilename());
					String name = f.getName();
					lblFileName.setValue(name);
					int k = name.lastIndexOf(".");
					String ext = null;
					if (k != -1) ext = name.substring(k + 1, name.length());
					/*
					 * if(ext.toLowerCase().equals("pdf".toLowerCase())) {
					 */
					imgPanel.removeAllComponents();
					FileResource fRes = new FileResource(file);
					fRes.setCacheTime(10);
					embedded = new Embedded(null, fRes);
					embedded.setType(Embedded.TYPE_BROWSER);
					embedded.setMimeType("application/pdf");
					embedded.setParameter("allowFullScreen", "true");
					embedded.setWidth("180px");
					embedded.setHeight("60px");
					imgPanel.addComponent(lblFileName);
					imgPanel.addComponent(embedded);
					imgPanel.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);
					imgPanel.setWidth("5px");
					imgPanel.setHeight("70px");
					imgPanel.setSpacing(true);
					UI.getCurrent().getSession().setAttribute("isDocumentUploaded", true);
					// Product.filevalue3 = true;
					// Documents.filevalue = true;
					// imgPanel.removeAllComponents();
					/*
					 * } else if(ext.equals("")) { Notification.show("", " File Format is incorrect",
					 * Notification.TYPE_ERROR_MESSAGE); } else { Notification.show("",
					 * " Only PDF File Format is allowed .  The "+ext+ " format is not allowed !!",
					 * Notification.TYPE_ERROR_MESSAGE); }
					 */
					// imgPanel.removeAllComponents();
				}
				catch (Exception e) {
					Notification.show("", " File Format is incorrect", Notification.TYPE_ERROR_MESSAGE);
				}
			}
		}
		;
		final ImageUploader uploader = new ImageUploader();
		upload.setReceiver(uploader);
		upload.addListener(uploader);
		// END-EXAMPLE: component.upload.basic
		// Create uploads directory
		basepath2 = basepath + "/VAADIN/themes/crystal-mm/img/Document.pdf";
		File uploads = new File(basepath2);
		if (!uploads.exists() && !uploads.mkdir()) layout.addComponent(new Label("ERROR: Could not create upload dir"));
		memorybuffer(layout);
		// panel.setWidth("-1");
		// imagelayout.addComponent(imgPanel);
		layout.addComponent(imgPanel);
		layout.addComponent(panel);
		layout.setSpacing(true);
	}
	
	@SuppressWarnings("unused")
	void memorybuffer(VerticalLayout layout) {
		// BEGIN-EXAMPLE: component.upload.memorybuffer
		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Upload the Document here", null);
		// Put the upload component in a panel
		VerticalLayout panel = new VerticalLayout();
		// panel.addComponent(upload);
		// Show uploaded file in this placeholder
		final Embedded image = new Embedded("Uploaded Document");
		// / panel.addComponent(image);
		// Put upload in this memory buffer that grows automatically
		final ByteArrayOutputStream os = new ByteArrayOutputStream(10240);
		// Implement receiver that stores in the memory buffer
		class ImageReceiver implements Receiver {
			private static final long serialVersionUID = -1276759102490466761L;
			public String filename; // The original filename
			
			public OutputStream receiveUpload(String filename, String mimeType) {
				this.filename = filename;
				os.reset(); // If re-uploading
				return os;
			}
		}
		;
		final ImageReceiver receiver = new ImageReceiver();
		upload.setReceiver(receiver);
		// // Handle success in upload
		// upload.addListener(new SucceededListener() {
		// private static final long serialVersionUID = 6053253347529760665L;
		//
		// public void uploadSucceeded(SucceededEvent event) {
		// image.setCaption("Uploaded Image " + receiver.filename + " has length " + os.toByteArray().length);
		// // Display the image in the feedback component
		// StreamSource source = new StreamSource() {
		// private static final long serialVersionUID = -4905654404647215809L;
		//
		// public InputStream getStream() {
		// return new ByteArrayInputStream(os.toByteArray());
		// }
		// };
		// if (image.getSource() == null) image.setSource(new StreamResource(source, receiver.filename));
		// else { // Replace picture
		// StreamResource resource = (StreamResource) image.getSource();
		// resource.setStreamSource(source);
		// resource.setFilename(receiver.filename);
		// resource.setCacheTime(10);
		// }
		// image.requestRepaint();
		// imgPanel.removeAllComponents();
		// }
		// });
		// END-EXAMPLE: component.upload.memorybuffer
		// ((VerticalLayout) panel.getContent()).setSpacing(true);
		// layout.addComponent(panel);
	}
	
	@SuppressWarnings("deprecation")
	public void displaycertificate(byte[] certificate) {
		imgPanel.removeAllComponents();
		System.out.println("Edit staff certificate");
		UI.getCurrent().getSession().setAttribute("isDocumentUploaded", true);
		if (certificate != null && !"null".equals(certificate)) {
			File someFile = new File(basepath1);
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(someFile);
				fos.write(certificate);
				fos.flush();
				fos.close();
			}
			catch (Exception e) {
			}
			FileResource filere = new FileResource(someFile);
			filere.setCacheTime(10);
			embedded = new Embedded(null, filere);
			embedded.setType(Embedded.TYPE_BROWSER);
			embedded.setMimeType("application/pdf");
			embedded.setParameter("allowFullScreen", "true");
			embedded.setWidth("180px");
			embedded.setHeight("60px");
			imgPanel.addComponent(lblFileName);
			imgPanel.addComponent(embedded);
			imgPanel.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);
			imgPanel.setWidth("5px");
			imgPanel.setHeight("70px");
			imgPanel.setSpacing(true);
		}
	}
}