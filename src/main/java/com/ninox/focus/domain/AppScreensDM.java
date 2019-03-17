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
@Table(name = "app_screens")
public class AppScreensDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "screen_id")
	private Long screenId;
	@Column(name = "screen_code")
	private Long screenCode;
	@Column(name = "sort_order")
	private Long sortOrder;
	@Column(name="screen_desc")
	private String screenDesc;
	@Column(name = "target_class")
	private String targetClass;
	@Column(name="parent_name")
	private String parentName;
	@Column(name = "screen_status")
	private String screenStatus;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	
	public Long getScreenId() {
		return screenId;
	}
	
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}
	
	public Long getScreenCode() {
		return screenCode;
	}
	
	public void setScreenCode(Long screenCode) {
		this.screenCode = screenCode;
	}
		
	public Long getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getTargetClass() {
		return targetClass;
	}
	
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
		
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getScreenStatus() {
		return screenStatus;
	}
	
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
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

	public String getScreenDesc() {
		return screenDesc;
	}

	public void setScreenDesc(String screenDesc) {
		this.screenDesc = screenDesc;
	}
	
}
