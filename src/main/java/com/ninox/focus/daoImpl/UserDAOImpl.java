package com.ninox.focus.daoImpl;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.UserDAO;
import com.ninox.focus.domain.UserDM;
import com.ninox.focus.util.DateUtils;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(UserDAOImpl.class);
	
	public void saveOrUpdateUser(UserDM objUserDM) {
		sessionFactory.getCurrentSession().saveOrUpdate(objUserDM);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserDM> getUserDMList(Long userId, Long companyId, String userName, String loginId,
			String loginPassword, String userStatus, String userRole) {
		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append(" select a.userId as userId, a.companyId as companyId, "
				+ " a.userName as userName, a.loginId as loginId, a.loginPassword as loginPassword, "
				+ " a.creationDt as creationDt, a.userRole as userRole, "
				+ " a.userStatus as userStatus, a.updatedDate as updatedDate, "
				+ " a.empoloyeeId as empoloyeeId, e.empPhoto as empPhoto, a.baseAdmin as baseAdmin,"
				+ " a.updatedBy as updatedBy from UserDM a, EmployeeDM e "
				+ " where 1=1 and a.empoloyeeId = e.employeeId ");
		if (userId != null) {
			sql.append(" and a.userId =" + userId);
		}
		if (userRole != null) {
			sql.append(" and a.userRole ='" + userRole + "'");
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (userName != null && userName.trim().length() > 0) {
			sql.append(" and lower(a.userName) like (lower('" + userName + "%'))");
		}
		if (loginId != null) {
			sql.append(" and a.loginId ='" + loginId + "'");
		}
		if (loginPassword != null) {
			sql.append(" and a.loginPassword ='" + loginPassword + "'");
		}
		if (userStatus != null) {
			sql.append(" and a.userStatus ='" + userStatus + "'");
		}
		sql.append(" order by a.updatedDate desc");
		logger.info("Inside getUserDMList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(UserDM.class));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserDM> getUserReportList(Long userId, Long companyId, String userName, String loginId,
			String loginPassword, String userStatus, String userRole, Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append(" select a.userId as userId, a.companyId as companyId, "
				+ " a.userName as userName, a.loginId as loginId, a.loginPassword as loginPassword, "
				+ " a.creationDt as creationDt, a.userRole as userRole, "
				+ " a.userStatus as userStatus, a.updatedDate as updatedDate, "
				+ " a.empoloyeeId as empoloyeeId, e.empPhoto as empPhoto, "
				+ " a.updatedBy as updatedBy, a.baseAdmin as baseAdmin "
				+ " from UserDM  a, EmployeeDM e "
				+ " where 1=1 and a.empoloyeeId = e.employeeId ");
		if (startDate != null && endDate != null) {
			sql.append(" AND a.creationDt BETWEEN '" + DateUtils.datetostringtimenew(startDate) + "' AND '"
					+ DateUtils.datetostringtimenew(endDate) + "'");
		} else if (startDate != null) {
			sql.append(" AND a.creationDt LIKE ('" + DateUtils.datetostringtimenew(startDate) + "%')");
		}
		if (userId != null) {
			sql.append(" and a.userId =" + userId);
		}
		if (userRole != null) {
			sql.append(" and a.userRole ='" + userRole + "'");
		}
		if (companyId != null) {
			sql.append(" and a.companyId =" + companyId);
		}
		if (userName != null && userName.trim().length() > 0) {
			sql.append(" and lower(a.userName) like (lower('" + userName + "%'))");
		}
		if (loginId != null) {
			sql.append(" and a.loginId ='" + loginId + "'");
		}
		if (loginPassword != null) {
			sql.append(" and a.loginPassword ='" + loginPassword + "'");
		}
		if (userStatus != null) {
			sql.append(" and a.userStatus ='" + userStatus + "'");
		}
		sql.append(" order by a.updatedDate desc");
		logger.info("Inside getUserReportList > Query > " + sql);
		query = sessionFactory.getCurrentSession().createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(UserDM.class));
		return query.list();
	}
	
	public boolean isBaseAdmin(Long userId) {
		try {
			String sql = "SELECT baseAdmin as baseAdmin "
					+ " FROM UserDM WHERE userId = " + userId;
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			return (boolean) query.list().get(0);
		}
		catch (Exception e) {
			return false;
		} 
		
	}
	
}