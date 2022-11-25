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
    trigger.setCronExpression("5 * * * * ?");
    return trigger;
  }

  //Spring Boot 기반에서 Quartz를 실행하면 Scheduler는 자동으로 빈으로 등록하고 trigger와 job을 주입받아 실행시켜준다.
//  @Bean
//  public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
//    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//
//    schedulerFactory.setJobDetails(job);
//    schedulerFactory.setTriggers(trigger);
//    return schedulerFactory;
//  }
}
