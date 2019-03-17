package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.EmployeeDocumentDao;
import com.ninox.focus.domain.EmployeeDocumentDM;
import com.ninox.focus.service.EmployeeDocumentService;

@SuppressWarnings("serial")
@Repository
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService, Serializable {
	@Autowired
	EmployeeDocumentDao employeeDocumentDao;
	
	@Transactional
	public void saveOrUpdateEmployeeDocumentDetails(EmployeeDocumentDM freeInsuranceObj) {
		employeeDocumentDao.saveOrUpdateEmployeeDocumentDetails(freeInsuranceObj);
	}
	
	@Transactional
	public List<EmployeeDocumentDM> getEmployeeDocumentList(Long empDocumentId, Long employeeId,
			String documentStatus) {
		return employeeDocumentDao.getEmployeeDocumentList(empDocumentId, employeeId, documentStatus);
	}
}
