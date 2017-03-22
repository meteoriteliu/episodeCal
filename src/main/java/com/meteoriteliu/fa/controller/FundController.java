package com.meteoriteliu.fa.controller;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.model.FundValue;
import com.meteoriteliu.fa.service.FundDataSource;
import com.meteoriteliu.fa.service.FundService;
import com.meteoriteliu.fa.util.GsonPage;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FundController {

    private static final Logger logger = LoggerFactory.getLogger(FundController.class);
	
	@Autowired
	FundService fundService;

	@Autowired
    FundDataSource fundDataSource;
	
	@RequestMapping("/funds/{fundNo}")
    public Fund fund(@PathVariable("fundNo") String fundNo) {
        Fund fund = fundService.getFund(fundNo);
        if (fund == null) throw new ResourceNotFoundException("Fund" );
        return fund;
    }

    @RequestMapping("/funds")
    public GsonPage<Fund> fundList(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Fund> funds = fundService.getFunds(page, 100);
        return new GsonPage<>(funds);
    }

    @RequestMapping("/funds/{fundNo}/values")
    public GsonPage<FundValue> fundValues(@PathVariable("fundNo") String fundNo, @RequestParam(name = "page", defaultValue = "0") int page) {
        Fund fund = fundService.getFund(fundNo);
        if (fund == null) throw new ResourceNotFoundException("Fund" );
        Page<FundValue> fundValues = fundService.getFundValues(fund, page, 100);

        logger.info("fund values size=" + fundValues.getContent().size());

        return new GsonPage<>(fundValues);
    }
}
