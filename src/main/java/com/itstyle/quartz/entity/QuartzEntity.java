package com.itstyle.quartz.entity;

import lombok.Data;

/**
 * @author xuzhen
 * @version 1.0
 * @date 2023/5/5 16:05
 */
@Data
public class QuartzEntity{

	//任务名称
	private String jobName;

	//任务分组
	private String jobGroup;

	//任务描述
	private String description;

	//执行类
	private String jobClassName;

	//执行方法
	private String jobMethodName;

	//执行方法参数
	private String jobMethodArgs;

	//执行表达式
	private String cronExpression;

	//执行器名称
	private String triggerName;

	//执行器状态
	private String triggerState;

	//原任务名称（用于修改）
	private String oldJobName;

	//原任务分组（用于修改）
	private String oldJobGroup;

}
