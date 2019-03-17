package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.ApprovalSchemaDAO;
import com.ninox.focus.domain.ApprovalSchemaDM;
import com.ninox.focus.service.ApprovalSchemaService;

@SuppressWarnings("serial")
@Repository
public class ApprovalSchemaServiceImpl implements ApprovalSchemaService, Serializable {
	@Autowired
	private ApprovalSchemaDAO approvalSchemaDAO;
	
	@Transactional
	@CacheEvict(value = "approvalSchema", allEntries = true)
	public void saveOrUpdateApprovalSchema(ApprovalSchemaDM objApprSchm) {
		approvalSchemaDAO.saveOrUpdateApprovalSchema(objApprSchm);
	}
	
	@Transactional
	@CacheEvict(value = "approvalSchema", allEntries = true)
	public void deleteUsersScreenApproval(Long userId) {
		approvalSchemaDAO.deleteUsersScreenApproval(userId);
	}
	
	@Transactional
	@Cacheable(value = "approvalSchema")
	public List<ApprovalSchemaDM> getApprovalSchemaList(Long approvelSchemaId,
			Long companyId, Long screenId, String userRole, String status, Long userId, Boolean isApproved) {
		return approvalSchemaDAO.getApprovalSchemaList(approvelSchemaId, companyId, screenId, userRole,
				status, userId, isApproved);
	}
}
