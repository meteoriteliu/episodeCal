package com.meteoriteliu.fa.service.impl;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.service.FundDataSource;

public class EastmoneyFundDataSource implements FundDataSource{

	private static final String BASEURL = "http://fund.eastmoney.com";
	private static final String FUND_TYPE = "lsjz";
	private static final Logger logger = LoggerFactory.getLogger(EastmoneyFundDataSource.class);
	

	@Override
	public Fund getFund(String code) {
		String fundUrl = BASEURL + "/" + code + ".html";
		
		try {
			Connection.Response response = Jsoup.connect(fundUrl).execute();
			if (response.statusCode() == 404) {
				logger.error("fund(" + code + ") not found");
				return null;
			}
			Document doc = response.parse();
			String name = doc.select(".fundDetail-tit").text();
			Fund fund = new Fund();
			fund.setCode(code);
			fund.setName(name);
			fund.setType(FUND_TYPE);
			return fund;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

}
