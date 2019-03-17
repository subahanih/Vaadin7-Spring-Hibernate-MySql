package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.SlnoGenDao;
import com.ninox.focus.domain.SlnoGenDM;
import com.ninox.focus.service.SlnoGenService;

@SuppressWarnings("serial")
@Repository
public class SlnoGenServiceImpl implements SlnoGenService, Serializable {
	@Autowired
	private SlnoGenDao slnoGenDao;
	
	@Transactional
	public void saveOrUpdateSlnoGenDetails(SlnoGenDM slnoGenObj) {
		slnoGenDao.saveOrUpdateSlnoGenDetails(slnoGenObj);
	}
	
	@Transactional
	public List<SlnoGenDM> getSlnoGenList(Long slnoGenId, Long companyId, Boolean autogenYN, String refkey) {
		return slnoGenDao.getSlnoGenList(slnoGenId, companyId, autogenYN, refkey);
	}
	
	@Transactional
	public void updateNextSequenceNumber(Long companyid, String refkey) {
		slnoGenDao.updateNextSequenceNumber(companyid, refkey);
	}
}
