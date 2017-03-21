package com.meteoriteliu.fa.service;

import com.meteoriteliu.fa.model.Fund;

public interface FundDataSource {
	Fund getFund(String code);
	void updateFundValue(Fund fund);
}
