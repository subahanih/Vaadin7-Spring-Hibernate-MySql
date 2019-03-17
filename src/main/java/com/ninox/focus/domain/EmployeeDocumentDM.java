package com.ninox.focus.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ninox.focus.util.DateUtils;

@Entity
@Table(name = "employee_document")
public class EmployeeDocumentDM implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "document_id")
	private Long empDocumentId;
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "document_name")
	private String documentName;
	@Column(name = "document")
	private byte[] document;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "document_status")
	private String documentStatus;
	@Column(name = "updated_by")
	private String updated_by;
	
	public Long getEmpDocumentId() {
		return empDocumentId;
	}
	
	public void setEmpDocumentId(Long empDocumentId) {
		this.empDocumentId = empDocumentId;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public byte[] getDocument() {
		return document;
	}
	
	public void setDocument(byte[] document) {
		this.document = document;
	}
	
	public String getUpdatedDate() {
		if (updatedDate != null) {
			return DateUtils.dateToString(updatedDate);
		} else {
			return null;
		}
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getDocumentStatus() {
		return documentStatus;
	}
	
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	public String getUpdated_by() {
		return updated_by;
	}
	
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
}
