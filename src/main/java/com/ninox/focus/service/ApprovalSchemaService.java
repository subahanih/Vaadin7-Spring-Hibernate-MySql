package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.ApprovalSchemaDM;

public interface ApprovalSchemaService {

	public void saveOrUpdateApprovalSchema(ApprovalSchemaDM objApprSchm);

	public void deleteUsersScreenApproval(Long userId);

	public List<ApprovalSchemaDM> getApprovalSchemaList(Long approvelSchemaId,
			Long companyId, Long screenId, String userRole, String status, Long userId, Boolean isApproved);
}
