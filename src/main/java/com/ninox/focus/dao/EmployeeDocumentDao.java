package com.ninox.focus.dao;

import java.util.List;
import com.ninox.focus.domain.EmployeeDocumentDM;

public interface EmployeeDocumentDao {
	public void saveOrUpdateEmployeeDocumentDetails(EmployeeDocumentDM freeInsuranceObj);
	
	public List<EmployeeDocumentDM> getEmployeeDocumentList(Long empDocumentId, Long employeeId,
			String documentStatus);
}
