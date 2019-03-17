package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.UserLoginDM;

public interface UserLoginService {
public void saveOrUpdateUserLogin(UserLoginDM objUserLoginDM);
	
	/**
	 * update log out date in MBaseUserLogin based on user login id
	 * 
	 * @param userLoginId
	 */
	public void updateLogoutDateByMbaseUserLogin(Long userLoginId);
	
	public List<UserLoginDM> getUserLoginList(Long sessionloginId, Long companyId, Long userId);
}
