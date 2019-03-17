package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.AppScreensDAO;
import com.ninox.focus.domain.AppScreensDM;
import com.ninox.focus.service.AppScreensService;

@SuppressWarnings("serial")
@Repository
public class AppScreensServiceImpl implements AppScreensService, Serializable {
	@Autowired
	private AppScreensDAO appScreensDAO;
	
	@Transactional
	@CacheEvict(value = "appScreens", allEntries = true)
	public void saveOrUpdateAppScreens(AppScreensDM objAppScreens) {
		appScreensDAO.saveOrUpdateAppScreens(objAppScreens);
	}
	
	@Transactional
	@Cacheable(value = "appScreens")
	public List<AppScreensDM> getAppScreensList(Long screenId, String screenCode, String screenDesc,
			String reviewAplYn, String screenStatus) {
		return appScreensDAO.getAppScreensList(screenId, screenCode, screenDesc, reviewAplYn, screenStatus);
	}
}
