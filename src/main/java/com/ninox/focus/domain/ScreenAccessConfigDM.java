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
@Table(name = "scrn_access_config")
public class ScreenAccessConfigDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "screen_access_id")
	private Long screenAccessId;
	@Column(name = "screen_id")
	private Long screenId;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "user_role")
	private String userRole;
	@Column(name = "view_yn")
	private String viewYn;
	@Column(name = "access_status")
	private String accessStatus;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Transient
	private String screeName;
	
	public Long getScreenAccessId() {
		return screenAccessId;
	}
	
	public void setScreenAccessId(Long screenAccessId) {
		this.screenAccessId = screenAccessId;
	}
	
	public Long getScreenId() {
		return screenId;
	}
	
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	public String getViewYn() {
		return viewYn;
	}
	
	public void setViewYn(String viewYn) {
		this.viewYn = viewYn;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getAccessStatus() {
		return accessStatus;
	}
	
	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
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
	
	public String getScreeName() {
		return screeName;
	}
	
	public void setScreeName(String screeName) {
		this.screeName = screeName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
