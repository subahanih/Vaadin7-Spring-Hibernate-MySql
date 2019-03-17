package com.ninox.focus.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.ninox.focus.util.DateUtils;

@Entity
@Table(name = "employee")
public class EmployeeDM implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "employee_code")
	private String employeeCode;
	@Column(name = "employee_salut")
	private String employeeSalut;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "primary_phone")
	private String primaryPhone;
	@Column(name = "primary_email")
	private String primaryEmail;
	@Column(name = "dob")
	private Date dob;
	@Column(name = "loginaccess_yn")
	private Boolean loginaccessYn;
	@Column(name = "employee_salary")
	private Double employeeSalary;
	@Column(name = "gender")
	private String gender;
	@Column(name = "doj")
	private Date doj;
	@Column(name = "employee_photo")
	private byte[] empPhoto;
	@Column(name = "employee_status")
	private String empStatus;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "employee_role")
	private String empRole;
	@Transient
	private String fullName;
	@Transient
	private String myDOJ;

	public EmployeeDM() {
	}
	
	public EmployeeDM(Long employeeId, String fullName) {
		this.fullName = fullName;
		this.employeeId = employeeId;
	}
	
	public String getFullName() {
		if (lastName != null) {
			return firstName + " " + lastName;
		} else {
			return firstName;
		}
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
		
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public String getEmployeeSalut() {
		return employeeSalut;
	}
	
	public void setEmployeeSalut(String employeeSalut) {
		this.employeeSalut = employeeSalut;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	
	public String getPrimaryEmail() {
		return primaryEmail;
	}
	
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getDoj() {
		return doj;
	}
	
	public String getMyDOJ() {
		return myDOJ = DateUtils.dateToString(doj);
	}
	
	public void setMyDOJ(String myDOJ) {
		this.myDOJ = myDOJ;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}
	
	public byte[] getEmpPhoto() {
		return empPhoto;
	}
	
	public void setEmpPhoto(byte[] empPhoto) {
		this.empPhoto = empPhoto;
	}
	
	public String getEmpStatus() {
		return empStatus;
	}
	
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
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
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getEmpRole() {
		return empRole;
	}
	
	public void setEmpRoleId(String empRole) {
		this.empRole = empRole;
	}

	public Boolean getLoginaccessYn() {
		return loginaccessYn;
	}

	public void setLoginaccessYn(Boolean loginaccessYn) {
		this.loginaccessYn = loginaccessYn;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public Double getEmployeeSalary() {
		return employeeSalary;
	}

	public void setEmployeeSalary(Double employeeSalary) {
		this.employeeSalary = employeeSalary;
	}
	
}
