package com.ninox.focus.dao;

import java.util.List;
import com.ninox.focus.domain.CompanyDM;

public interface CompanyDAO {

	public void saveOrUpdateCompany(CompanyDM objCompany);

	public List<CompanyDM> getCompanyList(Long companyId, String companyName, String companyStatus);

}
