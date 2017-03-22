package com.meteoriteliu.fa.service.impl;

import com.meteoriteliu.fa.model.Fund;
import com.meteoriteliu.fa.repository.FundDAO;
import com.meteoriteliu.fa.service.FundDataSource;
import com.meteoriteliu.fa.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by meteo on 2017/3/22.
 */
@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    FundDataSource fundDataSource;

    @Autowired
    FundDAO fundDAO;

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void updateFundValue() {
        List<Fund> funds = fundDAO.findByLastSyncDateLessThanOrLastSyncDateIsNull(DateUtil.today());
        for (Fund fund : funds) {
            fundDataSource.updateFundValue(fund);
        }
    }
}
