package com.ninox.focus.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ninox.focus.dao.SlnoGenDao;
import com.ninox.focus.domain.SlnoGenDM;

@Repository
public class SlnoGenDaoImpl implements SlnoGenDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(SlnoGenDaoImpl.class);

	public void saveOrUpdateSlnoGenDetails(SlnoGenDM slnoGenObj) {
		sessionFactory.getCurrentSession().saveOrUpdate(slnoGenObj);
	}

	@SuppressWarnings("unchecked")
	public List<SlnoGenDM> getSlnoGenList(Long slnoGenId, Long companyId,
			Boolean autogenYN, String refkey) {
		Query query = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.slnoGenId as slnoGenId, s.companyId as companyId, ");
		sql.append(" s.keyDesc as keyDesc," +
				   " s.prefixKey||s.preffixCncat||s.suffixKey||s.suffixCncat||s.currSeqNo  as refKey," +
				   " s.autogenYN as autogenYN, ");
		sql.append(" s.prefixKey as prefixKey, s.preffixCncat as preffixCncat, s.suffixKey as suffixKey, ");
		sql.append(" s.suffixCncat as suffixCncat, s.currSeqNo as currSeqNo, s.lastSeqNo as lastSeqNo, ");
		sql.append(" s.updatedDate as updatedDate, s.updatedBy as updatedBy FROM SlnoGenDM s WHERE 1=1 ");
		if (slnoGenId != null) {
			sql.append(" AND s.slnoGenId =" + slnoGenId);
		}
		if (companyId != null) {
			sql.append(" AND s.companyId =" + companyId);
		}

		if (autogenYN != null) {
			sql.append(" AND s.autogenYN =" + autogenYN);
		}

		if (refkey != null) {
			sql.append(" AND s.refKey ='" + refkey + "'");
		}
		
		sql.append(" ORDER BY s.updatedDate desc");
		logger.info("Inside getSlnoGenList => SQL :" + sql.toString());
		query = sessionFactory
				.getCurrentSession()
				.createQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(SlnoGenDM.class));
		return query.list();
	}

	/**
	 * To update sequence number
	 * 
	 * @param Long
	 *            companyid,Long branchid,Long moduleid,String refkey
	 * 
	 */
	public void updateNextSequenceNumber(Long companyid, String refkey) {
		// TODO Auto-generated method stub
		String sql1 = "update SlnoGenDM s set s.lastSeqNo =s.currSeqNo where s.companyId="
				+ companyid	+ " and s.refKey ='" + refkey + "'";
		String sql2 = "update SlnoGenDM s set s.currSeqNo=s.currSeqNo+1 where s.companyId="
				+ companyid + " and s.refKey ='" + refkey + "'";
		sessionFactory.getCurrentSession().createQuery(sql1).executeUpdate();
		sessionFactory.getCurrentSession().createQuery(sql2).executeUpdate();
	}

}
