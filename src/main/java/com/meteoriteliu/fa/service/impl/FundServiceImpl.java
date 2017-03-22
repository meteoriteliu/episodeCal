package com.meteoriteliu.fa.service.impl;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.model.FundValue;
import com.meteoriteliu.fa.repository.FundDAO;
import com.meteoriteliu.fa.repository.FundValueDAO;
import com.meteoriteliu.fa.service.FundDataSource;
import com.meteoriteliu.fa.service.FundService;
import com.meteoriteliu.fa.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FundServiceImpl implements FundService {

	@Autowired
	FundDAO fundDAO;

	@Autowired
	FundValueDAO fundValueDAO;
	
	@Autowired
	FundDataSource fundDataSource;

	@Override
	@Transactional
	public Fund getFund(String code) {
		Fund fund = fundDAO.findByCode(code);
		if (fund != null) {
			return fund;
		}
		fund = fundDataSource.getFund(code);
		if (fund != null) {
			fundDAO.save(fund);
		}
 		return fund;
	}

	@Override
	public Page<Fund> getFunds(int page, int limit) {
		return fundDAO.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<FundValue> getFundValues(Fund fund, int page, int limit) {
		if (fund.getLastSyncDate() == null || fund.getLastSyncDate().before(DateUtil.today())) {
			fundDataSource.updateFundValue(fund);
		}

		return fundValueDAO.findByFundIdOrderByDateDesc(fund.getId(), new PageRequest(page, limit));
	}

}
