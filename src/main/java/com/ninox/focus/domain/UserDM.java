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
@Table(name = "app_user")
public class UserDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "app_user_id")
	private Long userId;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "login_name")
	private String loginId;
	@Column(name = "login_password")
	private String loginPassword;
	@Column(name = "created_date")
	private Date creationDt;
	@Column(name = "user_role")
	private String userRole;
	@Column(name = "user_status")
	private String userStatus;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name="base_admin")
	private Boolean baseAdmin;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "employee_id")
	private Long empoloyeeId;
	@Transient
	private byte[] empPhoto;
	
	public UserDM() {
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getLoginPassword() {
		return loginPassword;
	}
	
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	public Date getCreationDt() {
		return creationDt;
	}
	
	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}
	
	public String getUserStatus() {
		return userStatus;
	}
	
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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
	
	public Long getEmpoloyeeId() {
		return empoloyeeId;
	}
	
	public void setEmpoloyeeId(Long empoloyeeId) {
		this.empoloyeeId = empoloyeeId;
	}
	
	public byte[] getEmpPhoto() {
		return empPhoto;
	}
	
	public void setEmpPhoto(byte[] empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Boolean getBaseAdmin() {
		return baseAdmin;
	}

	public void setBaseAdmin(Boolean baseAdmin) {
		this.baseAdmin = baseAdmin;
	}	
	
}