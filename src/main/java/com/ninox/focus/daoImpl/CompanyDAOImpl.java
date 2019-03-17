package com.ninox.focus.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.CompanyDAO;
import com.ninox.focus.domain.CompanyDM;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(CompanyDAOImpl.class);
	
	public void saveOrUpdateCompany(CompanyDM objCompany) {
		sessionFactory.getCurrentSession().saveOrUpdate(objCompany);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyDM> getCompanyList(Long companyId, String companyName, String companyStatus) {
		Query query = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.companyId as companyId, a.companyName as companyName, a.companyAddress as companyAddress, "
				+ " a.phone as phone, a.emailId as emailId, a.companyShortName as companyShortName, "
				+ " a.regNo as regNo, a.servicetaxNo as servicetaxNo, a.companyLogo as companyLogo, "
				+ " a.companyStatus as companyStatus, a.companyDate as companyDate from CompanyDM a where 1=1 ");
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (companyName != null) {
			sql.append(" and a.companyName ='" + companyName + "'");
		}
		if (companyStatus != null) {
			sql.append(" and a.companyStatus ='" + companyStatus + "'");
		}
		sql.append(" order by a.companyDate desc");
		logger.info("Inside getCompanyList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(CompanyDM.class));
		return query.list();
	}
}
