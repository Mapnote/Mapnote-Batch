package com.mapnote.mapnotebatch.batch.job;

import com.mapnote.mapnotebatch.domain.schedule.entity.AlarmStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.ScheduleStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.Schedules;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
  private static final String SELECT_QUERY = "SELECT s FROM Schedules s WHERE s.isDeleted = :isDeleted AND s.scheduleStatus = :scheduleStatus AND s.alarmStatus = :alarmStatus";

  private static final int CHUNK_SIZE = 10;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  @Bean
  public Job jpaCursorItemReaderJob() {
    return jobBuilderFactory.get("jpaCursorItemReaderJob")
        .start(jpaCursorItemReaderStep())
        .build();
  }

  @Bean
  public Step jpaCursorItemReaderStep() {
    return stepBuilderFactory.get("jpaCursorItemReaderStep")
        .<Schedules, Schedules>chunk(CHUNK_SIZE)
        .reader(jpaCursorItemReader())
        .processor(jpaItemProcessor())
        .writer(jpaCursorItemWriter())
        .build();
  }

  @Bean
  public JpaCursorItemReader<Schedules> jpaCursorItemReader() {
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("isDeleted", Boolean.FALSE);
    parameterValues.put("scheduleStatus", ScheduleStatus.ONGOING);
    parameterValues.put("alarmStatus", AlarmStatus.CRY);
    return new JpaCursorItemReaderBuilder<Schedules>()
        .name("jpaCursorItemReader")
        .entityManagerFactory(entityManagerFactory)
        .queryString(
            SELECT_QUERY)
        .parameterValues(parameterValues)
        .build();
  }

  @Bean
  public ItemProcessor<Schedules, Schedules> jpaItemProcessor() {
    return schedule -> {
      AlarmStatus beforeAlarmStatus = schedule.getAlarmStatus();
      schedule.toggleAlarm();
      log.info("Schedule id {}'s alarm status is changed. before: {} / after: {}", schedule.getId(), beforeAlarmStatus, schedule.getAlarmStatus());
      return schedule;
    };
  }

  @Bean
  public JpaItemWriter<Schedules> jpaCursorItemWriter() {
    JpaItemWriter<Schedules> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    return jpaItemWriter;
  }
}
