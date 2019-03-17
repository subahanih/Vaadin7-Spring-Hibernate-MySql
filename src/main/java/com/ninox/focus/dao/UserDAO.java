package com.ninox.focus.dao;

import java.util.Date;
import java.util.List;
import com.ninox.focus.domain.UserDM;

public interface UserDAO {
	public void saveOrUpdateUser(UserDM objUserDM);
	
	public List<UserDM> getUserDMList(Long userId, Long companyId, String userName, String loginId,
			String loginPassword, String userStatus, String userRole);
	
	public List<UserDM> getUserReportList(Long userId, Long companyId, String userName, String loginId,
			String loginPassword, String userStatus, String userRole, Date startDate, Date endDate);
	
	public boolean isBaseAdmin(Long userId);
	
}