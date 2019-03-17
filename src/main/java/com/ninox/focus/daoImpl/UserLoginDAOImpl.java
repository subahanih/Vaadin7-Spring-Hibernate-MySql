package com.ninox.focus.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.UserLoginDAO;
import com.ninox.focus.domain.UserLoginDM;

@Repository
public class UserLoginDAOImpl implements UserLoginDAO {
	@Autowired
	SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(UserLoginDAOImpl.class);
	
	public void saveOrUpdateUserLogin(UserLoginDM objUserLoginDM) {
		sessionFactory.getCurrentSession().saveOrUpdate(objUserLoginDM);
	}
	
	/**
	 * To update the log out date based on loginUserId in MBaseUserLogin
	 * 
	 * @param userloginid
	 */
	public void updateLogoutDateByMbaseUserLogin(Long loginUserId) {
		sessionFactory
				.getCurrentSession()
				.createQuery(
						"UPDATE  UserLoginDM ul SET ul.logoutDate = NOW() WHERE ul.sessionloginId=" + loginUserId)
				.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserLoginDM> getUserLoginList(Long sessionloginId, Long companyId, Long userId) {
		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append(" select a.sessionloginId as sessionloginId, a.companyId as companyId ,"
				+ "a.userId as userId,a.loginDate as loginDate, a.clientIp as clientIp,"
				+ "a.sessionId as sessionId from UserLoginDM a where 1=1  ");
		if (userId != null) {
			sql.append(" and a.userId =" + userId);
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (sessionloginId != null) {
			sql.append(" and a.sessionloginId ='" + sessionloginId + "'");
		}
		logger.info("Inside getUserLoginList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(UserLoginDM.class));
		return query.list();
	}
}