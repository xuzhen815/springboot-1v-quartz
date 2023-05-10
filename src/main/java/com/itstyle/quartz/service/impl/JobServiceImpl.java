package com.itstyle.quartz.service.impl;

import java.util.List;

import com.itstyle.quartz.mapper.QrtzJobDetailsMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.service.IJobService;
import org.springframework.util.StringUtils;

/**
 * @author xuzhen
 * @version 1.0
 * @date 2023/5/5 16:05
 */
@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private QrtzJobDetailsMapper mapper;
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Override
    public List<QuartzEntity> listQuartzEntity(String jobName) throws SchedulerException {
        List<QuartzEntity> list = mapper.queryListModel(jobName);
        //获取执行的方法名
        if (list.size() > 0) {
            for (QuartzEntity quartzEntity : list) {
            JobKey key = new JobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(key);
            System.out.println(jobDetail);
            quartzEntity.setJobMethodName(jobDetail.getJobDataMap().getString("jobMethodName"));
            quartzEntity.setJobMethodArgs(jobDetail.getJobDataMap().getString("jobMethodArgs"));
            }
        }
        return list;
    }

    @Override
    public Long listQuartzEntity() {

        return mapper.queryCount();
    }


    @Override
    public void save(QuartzEntity quartz) throws Exception {
        //获取Scheduler实例、废弃、使用自动注入的scheduler、否则spring的service将无法注入
        //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //如果是修改  展示旧的 任务
        if(quartz.getOldJobGroup()!=null){
            JobKey key = new JobKey(quartz.getOldJobName(),quartz.getOldJobGroup());
            scheduler.deleteJob(key);
        }
        Class cls = Class.forName(quartz.getJobClassName()) ;
        cls.newInstance();
        //构建job信息
        JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
                        quartz.getJobGroup())
                .withDescription(quartz.getDescription()).build();
        //方法名
        job.getJobDataMap().put("jobMethodName", quartz.getJobMethodName());
        //方法参数
        if (!StringUtils.isEmpty(quartz.getJobMethodArgs())) {
            job.getJobDataMap().put("jobMethodArgs", quartz.getJobMethodArgs());
        }
        // 触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+quartz.getJobName(), quartz.getJobGroup())
                .startNow().withSchedule(cronScheduleBuilder).build();
        //交由Scheduler安排触发
        scheduler.scheduleJob(job, trigger);
    }

    @Override
    public void trigger(QuartzEntity quartz) throws SchedulerException {
        JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
        scheduler.triggerJob(key);
    }

    @Override
    public void pause(QuartzEntity quartz) throws SchedulerException {
        JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
        scheduler.pauseJob(key);
    }

    @Override
    public void resume(QuartzEntity quartz) throws SchedulerException {
        JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
        scheduler.resumeJob(key);
    }

    @Override
    public void remove(QuartzEntity quartz) throws Exception{
        TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
    }

}
