package com.meteoriteliu.fa.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.meteoriteliu.fa.model.FundValue;
import com.meteoriteliu.fa.repository.FundValueDAO;
import com.meteoriteliu.fa.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.service.FundDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class EastmoneyFundDataSource implements FundDataSource{

	private static final String BASEURL = "http://fund.eastmoney.com";
	private static final String FUND_TYPE = "lsjz";
	private static final Logger logger = LoggerFactory.getLogger(EastmoneyFundDataSource.class);

	private static final int FUNDVALUE_OFFSET = 50;

	@Autowired
	FundValueDAO fundValueDAO;

	@Override
	@Transactional
	public void updateFundValue(Fund fund) {
		logger.info("updating fund " + fund.getCode());
		String fundUrl = BASEURL + "/f10/F10DataApi.aspx?type="+fund.getType()+"&code="+fund.getCode()+"&page=%d&per=" + FUNDVALUE_OFFSET;
		FundValue lastFundValue = fundValueDAO.findTop1ByFundIdOrderByDateDesc(fund.getId());
		if (lastFundValue != null && DateUtil.isSameDate(lastFundValue.getDate(), new Date())) {
			logger.info("skip updating fund " + fund.getCode() + ", already up-to-date");
			return;
		}
		try {
			String url = String.format(fundUrl, 1);
			logger.debug("url =" + url);
			Connection.Response response = Jsoup.connect(url).execute();
			String body = response.body();
			String jsonStr = body.substring(body.indexOf("{"), body.lastIndexOf("}")) + "}";
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(jsonStr, JsonObject.class);
			int totalPage = jsonObject.get("pages").getAsInt();
			int currPage = 1;

			List<FundValue> values = new ArrayList<>();
			boolean recordExists = false;

			do {
				if (currPage > 1) {
					url = String.format(fundUrl, currPage);
					response = Jsoup.connect(url).execute();
					body = response.body();
					jsonStr = body.substring(body.indexOf("{"), body.lastIndexOf("}")) + "}";
					jsonObject = gson.fromJson(jsonStr, JsonObject.class);
				}
				Document document = Jsoup.parse(jsonObject.get("content").getAsString());
				for (Element tr : document.select("tbody tr")) {
					FundValue fundValue = new FundValue();
					fundValue.setFundId(fund.getId());
					Element td = tr.child(0);
					fundValue.setDate(DateUtil.parseDate(td.text()));

					if (lastFundValue != null && DateUtil.isSameDate(fundValue.getDate(), lastFundValue.getDate())) {
						recordExists = true;
						break;
					}

					td = tr.child(1);
					fundValue.setValue(new BigDecimal(td.text()));

					td = tr.child(2);
					fundValue.setAccuValue(new BigDecimal(td.text()));

					td = tr.child(3);
					if (StringUtils.isNotBlank(td.text())) {
						fundValue.setGrowRate(new BigDecimal(td.text().substring(0, td.text().length() - 1)).divide(BigDecimal.valueOf(100)));
					}

					values.add(fundValue);
				}

				currPage++;
				logger.debug("totalPage=" + totalPage + ", currPage=" + currPage + ", recordExists=" + recordExists);
			} while (totalPage >= currPage && !recordExists);

			fundValueDAO.save(values);
			logger.info("updating fund " + fund.getCode() + " success. " + values.size() + " values updated.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

}
