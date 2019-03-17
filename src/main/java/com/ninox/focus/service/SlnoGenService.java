package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.SlnoGenDM;

public interface SlnoGenService {
	public void saveOrUpdateSlnoGenDetails(SlnoGenDM slnoGenObj);
	
	public List<SlnoGenDM> getSlnoGenList(Long slnoGenId, Long companyId, Boolean autogenYN, String refkey);

	public void updateNextSequenceNumber(Long companyid, String refkey);

}
