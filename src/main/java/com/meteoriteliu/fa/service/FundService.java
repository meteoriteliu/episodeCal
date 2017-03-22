package com.meteoriteliu.fa.service;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.model.FundValue;
import org.springframework.data.domain.Page;

public interface FundService {
	Fund getFund(String code);

	Page<Fund> getFunds(int offset, int limit);

	Page<FundValue> getFundValues(Fund fund, int offset, int limit);
}
