package com.itstyle.quartz.web;

import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.entity.Result;
import com.itstyle.quartz.service.IJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author xuzhen
 * @version 1.0
 * @date 2023/5/5 16:05
 */
@RestController
@RequestMapping("/job")
public class JobController {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);


    @Autowired
	@Qualifier("Scheduler")
    private Scheduler scheduler;
    @Autowired
    private IJobService jobService;

	@PostMapping("/add")
	public Result save(QuartzEntity quartz){
		LOGGER.info("新增任务");
		try {
			jobService.save(quartz);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error();
		}
		return Result.ok();
	}

	@PostMapping("/list")
	public Result list(String jobName) throws SchedulerException{
		LOGGER.info("任务列表");
		List<QuartzEntity> list = jobService.listQuartzEntity(jobName);
		return Result.ok(list);
	}

	@PostMapping("/trigger")
	public  Result trigger(QuartzEntity quartz,HttpServletResponse response) {
		try {
			jobService.trigger(quartz);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/pause")
	public  Result pause(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("停止任务");
		try {
			jobService.pause(quartz);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/resume")
	public  Result resume(QuartzEntity quartz) {
		LOGGER.info("恢复任务");
		try {
			jobService.resume(quartz);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/remove")
	public  Result remove(QuartzEntity quartz) {
		try {
			jobService.remove(quartz);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error();
		}
		return Result.ok();
	}
}
