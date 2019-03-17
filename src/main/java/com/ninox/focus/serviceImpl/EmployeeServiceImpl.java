package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.EmployeeDAO;
import com.ninox.focus.domain.EmployeeDM;
import com.ninox.focus.service.EmployeeService;

@SuppressWarnings("serial")
@Repository
public class EmployeeServiceImpl implements EmployeeService, Serializable {
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Transactional
	public void saveOrUpdateEmployeeDetails(EmployeeDM objEmployeeDM) {
		employeeDAO.saveOrUpdateEmployeeDetails(objEmployeeDM);
	}
	
	@Transactional
	public List<EmployeeDM> getEmployeeList(Long employeeId, String employeeCode, String firstName, Long companyId,
			Date dob, String empStatus, Boolean checkYN) {
		return employeeDAO.getEmployeeList(employeeId, employeeCode, firstName, companyId, dob, empStatus, checkYN);
	}
	
	@Transactional
	public String getEmployeeName(Long employeeId) {
		return employeeDAO.getEmployeeName(employeeId);
	}
	
	@Transactional
	public List<EmployeeDM> getEmployeeReportList(Long employeeId, String employeeCode, String firstName,
			String lastName, Long companyId, Date dob, String empStatus, Boolean checkYN, Date startDate, Date endDate) {
		return employeeDAO
				.getEmployeeReportList(employeeId, employeeCode, firstName, lastName, companyId, dob, empStatus, 
						checkYN, startDate, endDate);
	}
}
