package com.ninox.focus.dao;

import java.util.List;
import com.ninox.focus.domain.AppScreensDM;

public interface AppScreensDAO {

	public void saveOrUpdateAppScreens(AppScreensDM objAppScreens);

	public List<AppScreensDM> getAppScreensList(Long screenId, String screenCode, String screenDesc, 
			String reviewAplYn, String screenStatus);
	
	
	
}
