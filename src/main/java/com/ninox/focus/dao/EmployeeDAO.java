package com.ninox.focus.dao;

import java.util.Date;
import java.util.List;
import com.ninox.focus.domain.EmployeeDM;

public interface EmployeeDAO {
	public void saveOrUpdateEmployeeDetails(EmployeeDM objEmployeeDM);
	
	public List<EmployeeDM> getEmployeeList(Long employeeId, String employeeCode, String firstName, Long companyId,
			Date dob, String empStatus, Boolean checkYN);
	
	public String getEmployeeName(Long employeeId);
	
	public List<EmployeeDM> getEmployeeReportList(Long employeeId, String employeeCode, String firstName,
			String lastName, Long companyId, Date dob, String empStatus, Boolean checkYN, 
			Date startDate, Date endDate);
}
