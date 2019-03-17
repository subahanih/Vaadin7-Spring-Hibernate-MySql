package com.ninox.focus.daoImpl;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.LookUpDAO;
import com.ninox.focus.domain.LookUpDM;

@Repository
public class LookUpDAOImpl implements LookUpDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(LookUpDAOImpl.class);
	
	public void saveOrUpdateLookUp(LookUpDM objLookUpDM) {
		sessionFactory.getCurrentSession().saveOrUpdate(objLookUpDM);
	}
	
	@SuppressWarnings("unchecked")
	public List<LookUpDM> getLookUpDMList(Long lookupId, String lookupCode, Long companyId, Date dob, 
			String lookupName, String lookupStatus, String tableName, Long lookupno) {
		Query query = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.lookupId AS lookupId, a.lookupCode AS lookupCode,"
				+ " a.lookupName AS lookupName, a.updatedDate as updatedDate, "
				+ " a.lookupStatus AS lookupStatus,a.tableName AS tableName,"
				+ " a.lookupno AS lookupno FROM LookUpDM a WHERE 1=1  ");
		if (lookupId != null) {
			sql.append(" AND a.lookupId =" + lookupId);
		}
		if (companyId != null) {
			sql.append(" AND a.companyId =" + companyId);
		}
		if (dob != null) {
			sql.append(" AND a.dob =" + dob);
		}
		if (lookupCode != null) {
			sql.append(" AND a.lookupCode ='" + lookupCode + "'");
		}
		if (lookupName != null) {
			sql.append(" AND a.lookupName ='" + lookupName + "'");
		}
		if (tableName != null) {
			sql.append(" AND a.tableName ='" + tableName + "'");
		}
		if (lookupStatus != null) {
			sql.append(" AND a.lookupStatus ='" + lookupStatus + "'");
		}
		sql.append(" ORDER BY a.lookupno ASC");
		logger.info("Inside getLookUpDMList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(LookUpDM.class));
		return query.list();
	}
}
