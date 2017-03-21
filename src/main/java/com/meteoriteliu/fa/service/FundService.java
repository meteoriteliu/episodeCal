package com.meteoriteliu.fa.service;

import com.meteoriteliu.fa.model.Fund;

import java.util.List;

public interface FundService {
	Fund getFund(String code);

	List<Fund> getFunds(int offset, int limit);
}
