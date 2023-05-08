package com.itstyle.quartz.service.impl;

import java.util.List;

import com.itstyle.quartz.mapper.QrtzJobDetailsMapper;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.service.IJobService;


@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private QrtzJobDetailsMapper mapper;
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Override
    public List<QuartzEntity> listQuartzEntity(QuartzEntity quartz, Integer pageNo, Integer pageSize) throws SchedulerException {
        List<QuartzEntity> list = mapper.queryListModel(null);
        //获取执行的方法名
        for (QuartzEntity quartzEntity : list) {
            JobKey key = new JobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(key);
            quartzEntity.setJobMethodName(jobDetail.getJobDataMap().getString("jobMethodName"));
        }
        return list;
    }

    @Override
    public Long listQuartzEntity() {

        return mapper.queryCount();
    }
}
