
package com.ninox.focus.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.ApprovalSchemaDAO;
import com.ninox.focus.domain.ApprovalSchemaDM;

@Repository
public class ApprovalSchemaDAOImpl implements ApprovalSchemaDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(ApprovalSchemaDAOImpl.class);
	
	public void saveOrUpdateApprovalSchema(ApprovalSchemaDM objApprSchm) {
		sessionFactory.getCurrentSession().saveOrUpdate(objApprSchm);
	}
	
	public void deleteUsersScreenApproval(Long userId) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM ApprovalSchemaDM WHERE userId = " + userId)
				.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<ApprovalSchemaDM> getApprovalSchemaList(Long approvelSchemaId,
			Long companyId, Long screenId, String userRole, String status, Long userId, Boolean isApproved) {
		StringBuffer sql = new StringBuffer(" select ");
		sql.append(" a.approvelSchemaId  as approvelSchemaId, a.companyId as companyId, a.screenId as screenId, "
				+ " a.userRole as userRole, a.updatedDate as updatedDate, a.userId as userId ,"
				+ " a.status as status, a.updatedBy as updatedBy, s.screenDesc as screenDesc, s.targetClass as targetClass,"
				+ " s.parentName as parentName, a.isApproved as isApproved "
				+ " from ApprovalSchemaDM a, AppScreensDM s, UserDM u where 1=1 "
				+ " and s.screenId = a.screenId and u.userRole = a.userRole ");
		Query query = null;
		if (approvelSchemaId != null) {
			sql.append(" and a.approvelSchemaId =" + approvelSchemaId);
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (screenId != null) {
			sql.append(" and a.screenId =" + screenId);
		}
		if (userRole != null) {
			sql.append(" and a.userRole ='" + userRole + "'");
		}
		if (status != null) {
			sql.append(" and a.status ='" + status + "'");
		}
		if (userId != null) {
			sql.append(" and a.userId =" + userId);
		}
		if (isApproved != null) {
			sql.append(" and a.isApproved =" + isApproved);
		}
		logger.info("Inside getApprovalSchemaList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(ApprovalSchemaDM.class));
		return query.list();
	}
}