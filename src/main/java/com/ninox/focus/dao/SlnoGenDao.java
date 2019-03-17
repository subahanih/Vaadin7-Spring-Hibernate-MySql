package com.ninox.focus.dao;

import java.util.List;
import com.ninox.focus.domain.SlnoGenDM;

public interface SlnoGenDao {
	public void saveOrUpdateSlnoGenDetails(SlnoGenDM slnoGenObj);
	
	public List<SlnoGenDM> getSlnoGenList(Long slnoGenId, Long companyId, Boolean autogenYN, String refkey);
	/**
	 * To update sequence number
	 * 
	 * @param Long
	 *            companyid,Long branchid,Long moduleid,String refkey
	 * 
	 */
	public void updateNextSequenceNumber(Long companyid, String refkey);

}
