package com.ninox.focus.service;

import java.util.List;
import com.ninox.focus.domain.CompanyDM;

public interface CompanyService {

	public void saveOrUpdateCompany(CompanyDM objCompany);

	public List<CompanyDM> getCompanyList(Long companyId, String companyName, String companyStatus);
}
