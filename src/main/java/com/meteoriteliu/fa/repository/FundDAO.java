package com.meteoriteliu.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meteoriteliu.fa.model.Fund;

public interface FundDAO extends JpaRepository<Fund, Integer> {
	Fund findByCode(String code);
}
