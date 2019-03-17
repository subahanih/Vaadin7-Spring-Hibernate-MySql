package com.ninox.focus.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.ScreenAccessConfigDAO;
import com.ninox.focus.domain.ScreenAccessConfigDM;

@Repository
public class ScreenAccessConfigDAOImpl implements ScreenAccessConfigDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(ScreenAccessConfigDAOImpl.class);
	
	public void saveOrUpdateScreenAccessConfig(ScreenAccessConfigDM objScreenAccessConfigDM) {
		sessionFactory.getCurrentSession().saveOrUpdate(objScreenAccessConfigDM);
	}
	
	@SuppressWarnings("unchecked")
	public List<ScreenAccessConfigDM> getScreenAccessConfigList(Long screenAccessId, Long screenId, Long companyId,
			String userRole, String viewYn, String accessStatus) {
		Query query = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.screenAccessId as screenAccessId, a.screenId as screenId,"
				+ " a.companyId as companyId, a.userRole as userRole ,a.viewYn as viewYn ,"
				+ " a.accessStatus as accessStatus ,a.updatedDate as updatedDate ,s.screenDesc as screeName,"
				+ " a.updatedBy as updatedBy from ScreenAccessConfigDM a, AppScreensDM s where 1=1 "
				+ " and s.screenId = a.screenId ");
		if (screenAccessId != null) {
			sql.append(" and a.screenAccessId =" + screenAccessId);
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (screenId != null) {
			sql.append(" and a.screenId =" + screenId);
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (userRole != null) {
			sql.append(" and a.userRole ='" + userRole + "'");
		}
		if (viewYn != null) {
			sql.append(" and a.viewYn ='" + viewYn + "'");
		}
		if (accessStatus != null) {
			sql.append(" and a. accessStatus ='" + accessStatus + "'");
		}
		sql.append(" order by a.updatedDate desc");
		logger.info("Inside getScreenAccessConfigList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(ScreenAccessConfigDM.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ScreenAccessConfigDM> getUnAllocatedScreenList(String userRole, Long userId) {
		Query query = null;
		StringBuffer sql = new StringBuffer(" SELECT ");
		sql.append("sa.screenId as screenId FROM ScreenAccessConfigDM sa WHERE sa.screenId NOT IN (SELECT a.screenId as screenId FROM ApprovalSchemaDM a WHERE a.userId = "
				+ userId + ")");
		if (userRole != null) {
			sql.append(" AND sa.userRole = '" + userRole +"'");
		}
		sql.append(" AND sa.accessStatus = 'Active'");
		logger.info("Inside getUnAllocatedScreenList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(ScreenAccessConfigDM.class));
		return query.list();
	}
}
