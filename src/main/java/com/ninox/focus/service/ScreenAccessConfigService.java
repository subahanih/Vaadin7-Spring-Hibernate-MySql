package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.ScreenAccessConfigDM;

public interface ScreenAccessConfigService {

	public void saveOrUpdateScreenAccessConfig(
			ScreenAccessConfigDM objScreenAccessConfigDM);

	public List<ScreenAccessConfigDM> getScreenAccessConfigList(
			Long screenAccessId, Long screenId, Long companyId,
			String userRole, String viewYn, String accessStatus);

	public List<ScreenAccessConfigDM> getUnAllocatedScreenList(String userRole,
			Long userId);
}
