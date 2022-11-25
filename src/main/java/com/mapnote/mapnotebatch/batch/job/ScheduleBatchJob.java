package com.mapnote.mapnotebatch.batch.job;

import com.mapnote.mapnotebatch.domain.schedule.entity.AlarmStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.ScheduleStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.Schedules;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ScheduleBatchJob {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  private static final int chunkSize = 10;

  @Bean
  public Job jpaCursorItemReaderJob() {
    return jobBuilderFactory.get("jpaCursorItemReaderJob")
        .start(jpaCursorItemReaderStep())
        .build();
  }

  @Bean
  @JobScope
  public Step jpaCursorItemReaderStep() {
    return stepBuilderFactory.get("jpaCursorItemReaderStep")
        .<Schedules, Schedules>chunk(chunkSize)
        .reader(jpaCursorItemReader())
        .processor(jpaItemProcessor())
        .writer(jpaCursorItemWriter())
        .build();
  }

  @Bean
  @StepScope
  public JpaCursorItemReader<Schedules> jpaCursorItemReader() {
    log.info("jpaCursorItemReader");
    HashMap<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("isDeleted", Boolean.FALSE);
    parameterValues.put("scheduleStatus", ScheduleStatus.ONGOING);
    parameterValues.put("alarmStatus", AlarmStatus.CRY);
    return new JpaCursorItemReaderBuilder<Schedules>()
        .name("jpaCursorItemReader")
        .entityManagerFactory(entityManagerFactory)
        .queryString(
            "SELECT s FROM Schedules s WHERE s.isDeleted = :isDeleted AND s.scheduleStatus = :scheduleStatus AND s.alarmStatus = :alarmStatus")
        .parameterValues(parameterValues)
        .build();
  }

  @Bean
  public ItemProcessor<Schedules, Schedules> jpaItemProcessor() {
    return schedule -> {
      log.info("before schedule.getAlarmStatus() = " + schedule.getAlarmStatus());
      schedule.toggleAlarm();
      log.info("after schedule.getAlarmStatus() = " + schedule.getAlarmStatus());
      return schedule;
    };
  }

  @StepScope
  private JpaItemWriter<Schedules> jpaCursorItemWriter() {
    JpaItemWriter<Schedules> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    return jpaItemWriter;
  }
}
