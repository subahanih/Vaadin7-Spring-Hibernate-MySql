package com.ninox.focus.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class CompanyDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "company_address")
	private String companyAddress;
	@Column(name = "company_phone")
	private String phone;
	@Column(name = "company_email")
	private String emailId;
	@Column(name = "company_short_name")
	private String companyShortName;
	@Column(name = "reg_no")
	private Long regNo;
	@Column(name = "servicetax_no")
	private Long servicetaxNo;
	@Column(name = "company_logo")
	private byte[] companyLogo;
	@Column(name = "company_status")
	private String companyStatus;
	@Column(name = "company_date")
	private Date companyDate;
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyAddress() {
		return companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public Long getRegNo() {
		return regNo;
	}
	
	public void setRegNo(Long regNo) {
		this.regNo = regNo;
	}
	
	public Long getServicetaxNo() {
		return servicetaxNo;
	}
	
	public void setServicetaxNo(Long servicetaxNo) {
		this.servicetaxNo = servicetaxNo;
	}
	
	public byte[] getCompanyLogo() {
		return companyLogo;
	}
	
	public void setCompanyLogo(byte[] companyLogo) {
		this.companyLogo = companyLogo;
	}
	
	public String getCompanyStatus() {
		return companyStatus;
	}
	
	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}
	
	public Date getCompanyDate() {
		return companyDate;
	}
	
	public void setCompanyDate(Date companyDate) {
		this.companyDate = companyDate;
	}
	
	public String getCompanyShortName() {
		return companyShortName;
	}
	
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}
}
