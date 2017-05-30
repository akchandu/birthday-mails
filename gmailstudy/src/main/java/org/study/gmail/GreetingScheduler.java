package org.study.gmail;

import java.io.IOException;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import javax.mail.MessagingException;

public class GreetingScheduler {

	public static void main(String[] args) throws IOException, MessagingException, SchedulerException {
		System.out.println("hello");
		
		trigger();
	}
	
	public static void trigger() throws SchedulerException {
		
		JobKey jobKeyA = new JobKey("jobA", "group1");
		JobDetail jobA = JobBuilder.newJob(GreetingTrigger.class)
				.withIdentity(jobKeyA).build();

		CronTrigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("dummyTriggerName1", "group1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
				.build();

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler1 = sf.getScheduler();

		scheduler1.start();
		scheduler1.scheduleJob(jobA, trigger1);
	}
	
}
