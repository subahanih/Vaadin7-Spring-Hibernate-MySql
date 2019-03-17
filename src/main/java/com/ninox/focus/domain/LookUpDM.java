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
@Table(name = "lookup_data")
public class LookUpDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lookup_id")
	private Long lookupId;
	@Column(name = "lookup_code")
	private String lookupCode;
	@Column(name = "lookup_name")
	private String lookupName;
	@Column(name = "lookup_status")
	private String lookupStatus;
	@Column(name = "created_date")
	private Date updatedDate;
	@Column(name = "table_name")
	private String tableName;
	@Column(name = "lookup_no")
	private Long lookupno;
	
	public Long getLookupId() {
		return lookupId;
	}
	
	public void setLookupId(Long lookupId) {
		this.lookupId = lookupId;
	}
	
	public String getLookupCode() {
		return lookupCode;
	}
	
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}
	
	public String getLookupName() {
		return lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}

	public String getLookupStatus() {
		return lookupStatus;
	}
	
	public void setLookupStatus(String lookupStatus) {
		this.lookupStatus = lookupStatus;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Long getLookupno() {
		return lookupno;
	}
	
	public void setLookupno(Long lookupno) {
		this.lookupno = lookupno;
	}
}
