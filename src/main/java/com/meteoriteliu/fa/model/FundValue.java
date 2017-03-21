package com.meteoriteliu.fa.model;

import com.meteoriteliu.fa.config.GsonExclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class FundValue {
	@Id @GsonExclude
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable=false)
	int id;
	
	@Column @GsonExclude
	Integer fundId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fundId", updatable=false, insertable=false)
	Fund fund;

	@Column
	@GsonExclude
	@CreatedDate
	Date createDate;
	
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

	@Column
	BigDecimal accuValue;

	@Column
	BigDecimal growRate;

	public BigDecimal getGrowRate() {
		return growRate;
	}

	public void setGrowRate(BigDecimal growRate) {
		this.growRate = growRate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getAccuValue() {
		return accuValue;
	}

	public void setAccuValue(BigDecimal accuValue) {
		this.accuValue = accuValue;
	}
}
