package com.ninox.focus.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ninox.focus.dao.CompanyDAO;
import com.ninox.focus.domain.CompanyDM;
import com.ninox.focus.service.CompanyService;

@SuppressWarnings("serial")
@Repository
public class CompanyServiceImpl implements CompanyService, Serializable {
	@Autowired
	private CompanyDAO companyDAO;
	
	@Transactional
	public void saveOrUpdateCompany(CompanyDM objCompany) {
		companyDAO.saveOrUpdateCompany(objCompany);
	}
	
	@Transactional
	public List<CompanyDM> getCompanyList(Long companyId, String companyName, String companyStatus) {
		return companyDAO.getCompanyList(companyId, companyName, companyStatus);
	}
}
