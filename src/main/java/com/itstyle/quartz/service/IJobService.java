package com.itstyle.quartz.service;

import java.util.List;
import com.itstyle.quartz.entity.QuartzEntity;
import org.quartz.SchedulerException;

public interface IJobService {

    List<QuartzEntity> listQuartzEntity(String jobName) throws SchedulerException;

    Long listQuartzEntity();

    //新增/修改
    void save(QuartzEntity quartz) throws Exception;

    //执行
    void trigger(QuartzEntity quartz) throws SchedulerException;

    //停止
    void pause(QuartzEntity quartz) throws SchedulerException;

    //恢复
    void resume(QuartzEntity quartz) throws SchedulerException;

    //删除
    void remove(QuartzEntity quartz) throws Exception;


}
