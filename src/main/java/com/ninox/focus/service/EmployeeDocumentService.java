package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.EmployeeDocumentDM;

public interface EmployeeDocumentService {
	public void saveOrUpdateEmployeeDocumentDetails(EmployeeDocumentDM freeInsuranceObj);
	
	public List<EmployeeDocumentDM> getEmployeeDocumentList(Long empDocumentId, Long employeeId,
			String documentStatus);
}
