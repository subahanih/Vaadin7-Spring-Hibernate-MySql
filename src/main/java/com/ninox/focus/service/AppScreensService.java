package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.AppScreensDM;

public interface AppScreensService {

	public void saveOrUpdateAppScreens(AppScreensDM objAppScreens);

	public List<AppScreensDM> getAppScreensList(Long screenId, String screenCode, String screenDesc, 
			String reviewAplYn, String screenStatus);
	
	
}
