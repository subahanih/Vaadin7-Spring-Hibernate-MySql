package com.ninox.focus.daoImpl;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.EmployeeDAO;
import com.ninox.focus.domain.EmployeeDM;
import com.ninox.focus.util.DateUtils;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(EmployeeDAOImpl.class);
	
	public void saveOrUpdateEmployeeDetails(EmployeeDM objEmployeeDM) {
		sessionFactory.getCurrentSession().saveOrUpdate(objEmployeeDM);
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeDM> getEmployeeList(Long employeeId, String employeeCode, String firstName, Long companyId,
			Date dob, String empStatus, Boolean checkYN) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.employeeId  as employeeId, a.employeeCode as employeeCode, a.empRole as empRole, "
				+ " a.employeeSalut as employeeSalut, a.firstName as firstName, a.lastName as lastName, "
				+ " a.companyId as companyId, a.primaryPhone as primaryPhone ,a.primaryEmail as primaryEmail, a.dob as dob,a.gender as gender, "
				+ " a.doj as doj, a.empPhoto as empPhoto, a.loginaccessYn as loginaccessYn, a.employeeSalary as employeeSalary,  "
				+ " a.empStatus as empStatus,a.updatedDate as updatedDate,a.updatedBy as updatedBy  from EmployeeDM a where 1=1 ");
		Query query = null;
		if (employeeId != null) {
			sql.append(" and a.employeeId =" + employeeId);
		}
		if (employeeCode != null && employeeCode.trim().length() > 0) {
			sql.append(" and lower(a.employeeCode) like (lower('" + employeeCode + "%'))");
		}
		if (firstName != null && firstName.trim().length() > 0) {
			sql.append(" and lower(a.firstName) like (lower('" + firstName + "%'))");
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
	
		if (empStatus != null) {
			sql.append(" and a.empStatus ='" + empStatus + "'");
		}
		if (checkYN != null) {
			sql.append(" and a.loginaccessYn =" + checkYN);
		}
		sql.append(" order by a.updatedDate desc");
		logger.info("Inside getEmployeeList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(EmployeeDM.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeDM> getEmployeeReportList(Long employeeId, String employeeCode, String firstName,
			String lastName, Long companyId, Date dob, String empStatus, Boolean checkYN, Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.employeeId  as employeeId, a.employeeCode as employeeCode, a.empRole as empRole, "
				+ " a.employeeSalut as employeeSalut, a.firstName as firstName, a.lastName as lastName, "
				+ " a.companyId as companyId, a.primaryPhone as primaryPhone ,a.primaryEmail as primaryEmail, a.dob as dob,a.gender as gender, "
				+ " a.employeeSalary as employeeSalary, "
				+ " a.doj as doj, a.empPhoto as empPhoto, a.loginaccessYn as loginaccessYn,"
				+ " a.empStatus as empStatus,a.updatedDate as updatedDate, a.updatedBy as updatedBy from EmployeeDM a where 1=1 ");
		Query query = null;
		if (startDate != null && endDate != null) {
			sql.append(" AND a.updatedDate BETWEEN '" + DateUtils.datetostringtimenew(startDate) + "' AND '"
					+ DateUtils.datetostringtimenew(endDate) + "'");
		} else if (startDate != null) {
			sql.append(" AND a.updatedDate LIKE ('" + DateUtils.datetostringtimenew(startDate) + "%')");
		}
		if (employeeId != null) {
			sql.append(" and a.employeeId =" + employeeId);
		}
		if (employeeCode != null && employeeCode.trim().length() > 0) {
			sql.append(" and lower(a.employeeCode) like (lower('" + employeeCode + "%'))");
		}
		if (firstName != null && firstName.trim().length() > 0) {
			sql.append(" and lower(a.firstName) like (lower('" + firstName + "%'))");
		}
		if (lastName != null && lastName.trim().length() > 0) {
			sql.append(" and lower(a.lastName) like (lower('" + lastName + "%'))");
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (empStatus != null) {
			sql.append(" and a.empStatus ='" + empStatus + "'");
		}
		if (checkYN != null) {
			sql.append(" and a.loginaccessYn =" + checkYN);
		}
		sql.append(" order by a.updatedDate desc");
		logger.info("Inside getEmployeeList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(EmployeeDM.class));
		return query.list();
	}
	
	public String getEmployeeName(Long employeeId) {
		try {
			String sql = "SELECT CONCAT(IFNULL(firstName,''), ' ', IFNULL(lastName,'')) AS fullName"
					+ " FROM EmployeeDM WHERE employeeId = " + employeeId;
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			return query.list().get(0).toString();
		}
		catch (Exception e) {
			return null;
		}
	}
}
