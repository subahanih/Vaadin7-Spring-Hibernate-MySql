package com.ninox.focus.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.AppScreensDAO;
import com.ninox.focus.domain.AppScreensDM;

@Repository
public class AppScreensDAOImpl implements AppScreensDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(AppScreensDAOImpl.class);
	
	public void saveOrUpdateAppScreens(AppScreensDM objAppScreens) {
		sessionFactory.getCurrentSession().saveOrUpdate(objAppScreens);
	}
	
	@SuppressWarnings("unchecked")
	public List<AppScreensDM> getAppScreensList(Long screenId, String screenCode, String screenDesc,
			String reviewAplYn, String screenStatus) {
		StringBuffer sql = new StringBuffer(" select ");
		sql.append(" a.screenId  as screenId, a.screenCode as screenCode, "
				+ " a.screenDesc as screenDesc, a.sortOrder as sortOrder, a.targetClass as targetClass, "
				+ " a.reviewAplYn as reviewAplYn,  a.parentName as parentName, a.screenStatus as screenStatus, "
				+ " a.updatedDate as updatedDate, a.updatedBy as updatedBy from AppScreensDM a where 1=1 ");
		Query query = null;
		if (screenId != null) {
			sql.append(" and a.screenId =" + screenId);
		}
		if (screenCode != null) {
			sql.append(" and a.companyId ='" + screenCode+"'");
		}
		if (reviewAplYn != null) {
			sql.append(" and a.reviewAplYn ='" + reviewAplYn+"'");
		}
		if (screenStatus != null) {
			sql.append(" and a.screenStatus ='" + screenStatus + "'");
		}
		sql.append(" order by a.updatedDate desc");
		logger.info("Inside getAppScreensList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(AppScreensDM.class));
		return query.list();
	}
}
