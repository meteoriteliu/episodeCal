package com.meteoriteliu.fa.repository;

import com.meteoriteliu.fa.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FundDAO extends JpaRepository<Fund, Integer> {
	Fund findByCode(String code);

	List<Fund> findByLastSyncDateLessThanOrLastSyncDateIsNull(Date lastSyncDate);
}
