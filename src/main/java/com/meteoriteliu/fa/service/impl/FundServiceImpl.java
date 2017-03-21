package com.meteoriteliu.fa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.repository.FundDAO;
import com.meteoriteliu.fa.service.FundDataSource;
import com.meteoriteliu.fa.service.FundService;
@Service
public class FundServiceImpl implements FundService {

	@Autowired
	FundDAO fundDAO;
	
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

}
