package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.LookUpDAO;
import com.ninox.focus.domain.LookUpDM;
import com.ninox.focus.service.LookUpService;

@SuppressWarnings("serial")
@Repository
public class LookUpServiceImpl implements LookUpService, Serializable {
	@Autowired
	private LookUpDAO lookUpDAO;
	
	@Transactional
	// @CacheEvict(value = "lookupData", allEntries = true)
	public void saveOrUpdateLookUp(LookUpDM objLookUpDM) {
		lookUpDAO.saveOrUpdateLookUp(objLookUpDM);
	}
	
	@Transactional
	// @Cacheable(value = "lookupData")
	public List<LookUpDM> getLookUpDMList(Long lookupId, String lookupCode, Long companyId, Date dob, 
			String lookupName, String lookupStatus, String tableName, Long lookupno) {
		return lookUpDAO.getLookUpDMList(lookupId, lookupCode, companyId, dob, lookupName,
				lookupStatus, tableName, lookupno);
	}
}
