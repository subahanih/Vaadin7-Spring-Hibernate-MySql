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
@Table(name = "approval_schema")
public class ApprovalSchemaDM implements Serializable {
	private static final long serialVersionUID = -3252652758724801778L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "apprv_schmid")
	private Long approvelSchemaId;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "screen_id")
	private Long screenId;
	@Column(name = "user_role")
	private String userRole;
	@Column(name = "aprvsch_status")
	private String status;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "app_user_id")
	private Long userId;
	@Column(name = "is_approved")
	private Boolean isApproved;
	@Transient
	private String screenDesc;
	@Transient
	private String parentName;
	@Transient
	private String targetClass;
	@Transient
	private Boolean select;
	
	public Long getApprovelSchemaId() {
		return approvelSchemaId;
	}
	
	public void setApprovelSchemaId(Long approvelSchemaId) {
		this.approvelSchemaId = approvelSchemaId;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public String getTargetClass() {
		return targetClass;
	}
	
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Long getScreenId() {
		return screenId;
	}
	
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Boolean getIsApproved() {
		return isApproved;
	}
	
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	public Boolean getSelect() {
		return select;
	}
	
	public void setSelect(Boolean select) {
		this.select = select;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getScreenDesc() {
		return screenDesc;
	}

	public void setScreenDesc(String screenDesc) {
		this.screenDesc = screenDesc;
	}

	@Override
	public String toString() {
		return "ApprovalSchemaDM [approvelSchemaId=" + approvelSchemaId + ", companyId=" + companyId + ", screenId="
				+ screenId + ", branchId=" + ", userRoleId=" + ", status=" + status
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", userId=" + userId + ", isApproved="
				+ isApproved + ", screenDesc=" + ", parentName=" + parentName + ", userRoleName="
				+ ", branchName=" + ", targetClass=" + targetClass + ", select=" + select + "]";
	}
}