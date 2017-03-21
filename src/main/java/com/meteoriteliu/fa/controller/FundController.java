package com.meteoriteliu.fa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.service.FundService;

@RestController
public class FundController {
	
	@Autowired
	FundService fundService;
	
	@RequestMapping("/fund/{fundNo}")
    public Fund greeting(@PathVariable("fundNo") String fundNo) {
        Fund fund = fundService.getFund(fundNo);
        return fund;
    }
}
