package com.itstyle.quartz.service;

import java.util.List;
import com.itstyle.quartz.entity.QuartzEntity;
import org.quartz.SchedulerException;

public interface IJobService {

    List<QuartzEntity> listQuartzEntity(String jobName) throws SchedulerException;

    Long listQuartzEntity();
}
