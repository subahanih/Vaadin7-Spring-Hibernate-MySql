package com.ninox.focus.dao;

import java.util.Date;
import java.util.List;
import com.ninox.focus.domain.LookUpDM;

public interface LookUpDAO {


	public void saveOrUpdateLookUp(LookUpDM objLookUpDM);

	public List<LookUpDM> getLookUpDMList(Long lookupId, String lookupCode,
			Long companyId, Date dob, String lookupName, String lookupStatus,String tableName,Long lookupno);

}