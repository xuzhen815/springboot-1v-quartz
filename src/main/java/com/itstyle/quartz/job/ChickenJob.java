package com.itstyle.quartz.job;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.quartz.*;
import org.springframework.util.StringUtils;

/**
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 * Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，一个新的 Job 实例会被创建。
 * 其中暗含的意思就是你的 Job 不必担心线程安全性，因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 * @DisallowConcurrentExecution 保证上一个任务执行完后，再去执行下一个任务，这里的任务是同一个任务
 */
@DisallowConcurrentExecution
public class ChickenJob implements Job,Serializable {

	private static final long serialVersionUID = 1L;

	@Override
    public void execute(JobExecutionContext context){
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        /**
         * 获取任务中保存的方法名字，动态调用方法
         */
        String methodName = dataMap.getString("jobMethodName");
        //参数
        String methodArgs = dataMap.getString("jobMethodArgs");

        Method method = null;
        try {
            ChickenJob job = new ChickenJob();

            if (StringUtils.isEmpty(methodArgs)){
                method = job.getClass().getMethod(methodName);
                method.invoke(job);
            }else {
                method = job.getClass().getMethod(methodName,methodArgs.getClass());
                method.invoke(job,methodArgs);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下面为定时任务
     * */
    public void test1(){
        System.out.println("测试方法1");
    }
    public void test2(String s){
	    System.out.println(s);
    }
}
