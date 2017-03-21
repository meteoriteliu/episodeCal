package com.meteoriteliu.fa.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FundValue {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable=false)
	int id;
	
	@Column
	Integer fundId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fundId", updatable=false, insertable=false)
	Fund fund;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getFundId() {
		return fundId;
	}

	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Column
	Date date;
	
	@Column
	BigDecimal value;
}
