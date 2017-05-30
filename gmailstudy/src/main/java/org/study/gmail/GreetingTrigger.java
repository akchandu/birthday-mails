package org.study.gmail;

import java.io.IOException;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GreetingTrigger implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("hello world!");
		
		PrepareGreetingMail obj = new PrepareGreetingMail();
		try {
			obj.getBirthdayPeople();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Person p : obj.listPersons) {
			try {
				obj.sendMail(p.getName(), p.getEmailAddress(), p.getOccasion(), p.isPersonElder());
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
