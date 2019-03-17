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
@Table(name = "slno_gen")
public class SlnoGenDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "slno_id")
	private Long slnoGenId;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "ref_key")
	private String refKey;
	@Column(name = "key_desc")
	private String keyDesc;
	@Column(name = "autogen_yn")
	private Boolean autogenYN;
	@Column(name = "prefix_key")
	private String prefixKey;
	@Column(name = "prefix_cncat")
	private String preffixCncat;
	@Column(name = "suffix_key")
	private String suffixKey;
	@Column(name = "suffix_cncat")
	private String suffixCncat;
	@Column(name = "curr_seqno")
	private Long currSeqNo;
	@Column(name = "last_seq_no")
	private Long lastSeqNo;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	
	public SlnoGenDM() {
	}
	
	public Long getSlnoGenId() {
		return slnoGenId;
	}
	
	public void setSlnoGenId(Long slnoGenId) {
		this.slnoGenId = slnoGenId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getRefKey() {
		return refKey;
	}
	
	public void setRefKey(String refKey) {
		this.refKey = refKey;
	}
	
	public String getKeyDesc() {
		return keyDesc;
	}
	
	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}
	
	public Boolean getAutogenYN() {
		return autogenYN;
	}
	
	public void setAutogenYN(Boolean autogenYN) {
		this.autogenYN = autogenYN;
	}
	
	public String getPrefixKey() {
		return prefixKey;
	}
	
	public void setPrefixKey(String prefixKey) {
		this.prefixKey = prefixKey;
	}
	
	public String getPreffixCncat() {
		return preffixCncat;
	}
	
	public void setPreffixCncat(String preffixCncat) {
		this.preffixCncat = preffixCncat;
	}
	
	public String getSuffixKey() {
		return suffixKey;
	}
	
	public void setSuffixKey(String suffixKey) {
		this.suffixKey = suffixKey;
	}
	
	public String getSuffixCncat() {
		return suffixCncat;
	}
	
	public void setSuffixCncat(String suffixCncat) {
		this.suffixCncat = suffixCncat;
	}
	
	public Long getCurrSeqNo() {
		return currSeqNo;
	}
	
	public void setCurrSeqNo(Long currSeqNo) {
		this.currSeqNo = currSeqNo;
	}
	
	public Long getLastSeqNo() {
		return lastSeqNo;
	}
	
	public void setLastSeqNo(Long lastSeqNo) {
		this.lastSeqNo = lastSeqNo;
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
		
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
