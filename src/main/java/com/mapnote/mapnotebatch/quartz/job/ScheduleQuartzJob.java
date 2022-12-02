package com.mapnote.mapnotebatch.quartz.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleQuartzJob extends QuartzJobBean {

  private final Job job;
  private final JobLauncher jobLauncher;

  @SneakyThrows
  @Override
  protected void executeInternal(JobExecutionContext context) {
    jobLauncher.run(
        job,
        new JobParametersBuilder()
            .addString("executedAt",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .toJobParameters()
    );
  }
}
