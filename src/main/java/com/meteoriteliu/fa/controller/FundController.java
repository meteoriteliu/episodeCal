package com.meteoriteliu.fa.controller;

import com.meteoriteliu.fa.service.FundDataSource;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.service.FundService;

import java.util.List;

@RestController
public class FundController {
	
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
    public List<Fund> fundList(@RequestParam(name = "offset", defaultValue = "0") int offset, @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Fund> funds = fundService.getFunds(offset, limit);
        return funds;
    }

    @RequestMapping("/funds/{fundNo}/values")
    public Fund fundValues(@PathVariable("fundNo") String fundNo) {
        Fund fund = fundService.getFund(fundNo);
        if (fund == null) throw new ResourceNotFoundException("Fund" );
        fundDataSource.updateFundValue(fund);
        return fund;
    }
}
