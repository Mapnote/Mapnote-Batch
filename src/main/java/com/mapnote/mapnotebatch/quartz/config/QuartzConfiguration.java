package com.mapnote.mapnotebatch.quartz.config;

import com.mapnote.mapnotebatch.quartz.job.ScheduleQuartzJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfiguration {

  @Bean
  public JobDetailFactoryBean jobDetail(){
    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
    jobDetailFactory.setJobClass(ScheduleQuartzJob.class);
    jobDetailFactory.setDescription("Job for updating Schedule alarm status");
    jobDetailFactory.setDurability(true);
    return jobDetailFactory;
  }

  @Bean
  public CronTriggerFactoryBean trigger(JobDetail job){
    CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(job);
    trigger.setCronExpression("1/30 * * * * ?");
    return trigger;
  }
}
