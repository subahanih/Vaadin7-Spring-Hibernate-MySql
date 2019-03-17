package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.UserLoginDAO;
import com.ninox.focus.domain.UserLoginDM;
import com.ninox.focus.service.UserLoginService;

@SuppressWarnings("serial")
@Repository
public class UserLoginServiceImpl implements UserLoginService, Serializable {
	@Autowired
	private UserLoginDAO userLoginDAO;
	
	@Transactional
	public void saveOrUpdateUserLogin(UserLoginDM objUserLoginDM) {
		userLoginDAO.saveOrUpdateUserLogin(objUserLoginDM);
	}
	
	@Transactional
	public List<UserLoginDM> getUserLoginList(Long sessionloginId, Long companyId, Long userId) {
		return userLoginDAO.getUserLoginList(sessionloginId, companyId, userId);
	}
	
	@Transactional
	public void updateLogoutDateByMbaseUserLogin(Long userLoginId) {
		userLoginDAO.updateLogoutDateByMbaseUserLogin(userLoginId);
	}
}
