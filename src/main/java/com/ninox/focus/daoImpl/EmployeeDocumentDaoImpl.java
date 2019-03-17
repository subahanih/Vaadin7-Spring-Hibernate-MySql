package com.ninox.focus.daoImpl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.EmployeeDocumentDao;
import com.ninox.focus.domain.EmployeeDocumentDM;

@Repository
public class EmployeeDocumentDaoImpl implements EmployeeDocumentDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public void saveOrUpdateEmployeeDocumentDetails(EmployeeDocumentDM freeInsuranceObj) {
		sessionFactory.getCurrentSession().saveOrUpdate(freeInsuranceObj);
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeDocumentDM> getEmployeeDocumentList(Long empDocumentId, Long employeeId, String documentStatus) {
		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append(" SELECT a.empDocumentId as empDocumentId, a.employeeId as employeeId, a.companyId as companyId,"
				+ " a.documentName as documentName, a.document as document, a.updatedDate as updatedDate,"
				+ " a.documentStatus as documentStatus, a.updated_by as updated_by from EmployeeDocumentDM a where 1=1 ");
		if (empDocumentId != null) {
			sql.append(" and a.empDocumentId =" + empDocumentId);
		}
		if (employeeId != null) {
			sql.append(" and a.employeeId =" + employeeId);
		}
		if (documentStatus != null) {
			sql.append(" and a.documentStatus ='" + documentStatus + "'");
		}
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(EmployeeDocumentDM.class));
		return query.list();
	}
}
