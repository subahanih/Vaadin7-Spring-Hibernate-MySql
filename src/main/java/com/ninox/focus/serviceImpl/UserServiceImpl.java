package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.UserDAO;
import com.ninox.focus.domain.UserDM;
import com.ninox.focus.service.UserService;

@SuppressWarnings("serial")
@Repository
public class UserServiceImpl implements UserService, Serializable {
	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	@CacheEvict(value = "user", allEntries = true)
	public void saveOrUpdateUser(UserDM objUserDM) {
		userDAO.saveOrUpdateUser(objUserDM);
	}
	
	@Transactional
	@Cacheable(value = "user")
	public List<UserDM> getUserDMList(Long userId, Long companyId, String userName, String loginId,
			String loginPassword, String userStatus, String userRole) {
		return userDAO.getUserDMList(userId, companyId, userName, loginId, loginPassword, userStatus, userRole);
	}
	
	@Transactional
	@Cacheable(value = "user")
	public List<UserDM> getUserReportList(Long userId, Long companyId, String userName, String loginId,
			String loginPassword, String userStatus, String userRole, Date startDate, Date endDate) {
		return userDAO.getUserReportList(userId, companyId, userName, loginId, loginPassword, userStatus, userRole, 
				startDate, endDate);
	}
	
	@Transactional
	public boolean isBaseAdmin(Long userId) {
		return userDAO.isBaseAdmin(userId);
	}

}