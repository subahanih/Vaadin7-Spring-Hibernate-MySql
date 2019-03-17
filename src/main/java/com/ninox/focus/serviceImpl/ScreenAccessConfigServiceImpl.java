package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.ScreenAccessConfigDAO;
import com.ninox.focus.domain.ScreenAccessConfigDM;
import com.ninox.focus.service.ScreenAccessConfigService;

@SuppressWarnings("serial")
@Repository
public class ScreenAccessConfigServiceImpl implements ScreenAccessConfigService, Serializable {
	@Autowired
	private ScreenAccessConfigDAO screenAccessConfigDAO;
	
	@Transactional
	@CacheEvict(value = "screenAccCon", allEntries = true)
	public void saveOrUpdateScreenAccessConfig(ScreenAccessConfigDM objScreenAccessConfigDM) {
		screenAccessConfigDAO.saveOrUpdateScreenAccessConfig(objScreenAccessConfigDM);
	}
	
	@Transactional
	@Cacheable(value = "screenAccCon")
	public List<ScreenAccessConfigDM> getScreenAccessConfigList(Long screenAccessId, Long screenId, Long companyId,
			String userRole, String viewYn, String accessStatus) {
		return screenAccessConfigDAO.getScreenAccessConfigList(screenAccessId, screenId, companyId,
				userRole, viewYn, accessStatus);
	}
	
	@Transactional
	public List<ScreenAccessConfigDM> getUnAllocatedScreenList(String userRole, Long userId) {
		return screenAccessConfigDAO.getUnAllocatedScreenList(userRole, userId);
	}
}
