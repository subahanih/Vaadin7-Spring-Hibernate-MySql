package com.ninox.focus.dao;

import java.util.List;
import com.ninox.focus.domain.ScreenAccessConfigDM;

public interface ScreenAccessConfigDAO {

	public void saveOrUpdateScreenAccessConfig(
			ScreenAccessConfigDM objScreenAccessConfigDM);

	public List<ScreenAccessConfigDM> getScreenAccessConfigList(
			Long screenAccessId, Long screenId, Long companyId,
			String userRole, String viewYn, String accessStatus);

	public List<ScreenAccessConfigDM> getUnAllocatedScreenList(String userRole, Long userId);
}
