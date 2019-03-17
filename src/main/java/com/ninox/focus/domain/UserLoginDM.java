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

@Entity
@Table(name = "user_login")
public class UserLoginDM implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "session_login_id")
	private Long sessionloginId;
	@Column(name = "company_id")
	private Long companyId;
	@Column(name = "app_user_id")
	private Long userId;
	@Column(name = "login_date")
	private Date loginDate;
	@Column(name = "logout_date")
	private Date logoutDate;
	@Column(name = "client_ip")
	private String clientIp;
	@Column(name = "session_id")
	private String sessionId;
	@Transient
	private String userName;
	
	public Long getSessionloginId() {
		return sessionloginId;
	}
	
	public void setSessionloginId(Long sessionloginId) {
		this.sessionloginId = sessionloginId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}
	
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public Date getLogoutDate() {
		return logoutDate;
	}
	
	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}
	
	public String getClientIp() {
		return clientIp;
	}
	
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}