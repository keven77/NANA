package com.juran.index.mdm.app.utils.job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.juran.index.mdm.app.service.PopulateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 作者:xy
 * 功能描述:PopulateCata Job
 */
public class PopulateDeltaJob implements SimpleJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(PopulateDeltaJob.class);

    @Autowired
    private PopulateService populateService;


    @Override
    public void execute(ShardingContext context) {
        Long startTime=System.currentTimeMillis();
        populateService.deltaCatentry();
        LOGGER.info("--PopulateDeltaJob--msg={},deltaCatentry time={}","PopulateJob任务",System.currentTimeMillis()-startTime);
        populateService.deltaBrand();
        LOGGER.info("--PopulateDeltaJob--msg={},deltaBrand time={}","PopulateJob任务",System.currentTimeMillis()-startTime);
    }
}
