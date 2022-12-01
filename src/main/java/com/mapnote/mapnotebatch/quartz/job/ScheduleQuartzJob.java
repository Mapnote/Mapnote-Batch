package com.mapnote.mapnotebatch.quartz.job;

import com.mapnote.mapnotebatch.domain.schedule.entity.AlarmStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.Schedules;
import com.mapnote.mapnotebatch.domain.schedule.repository.SchedulesRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleQuartzJob extends QuartzJobBean {

  private final Job job;
  private final JobLauncher jobLauncher;

  @Override
  protected void executeInternal(JobExecutionContext context) {
    try {
      jobLauncher.run(
          job,
          new JobParametersBuilder()
              .addString("executedAt",
                  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
              .toJobParameters()
      );
      log.info("Quartz Scheduler executed JobLauncher");
    } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
             JobInstanceAlreadyCompleteException | JobRestartException e) {
      System.out.println("e.getMessage() = " + e.getMessage());
    }
  }
}
