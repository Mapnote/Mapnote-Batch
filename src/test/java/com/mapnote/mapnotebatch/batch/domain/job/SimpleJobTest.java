package com.mapnote.mapnotebatch.batch.domain.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.mapnote.mapnotebatch.batch.TestBatchConfig;
import com.mapnote.mapnotebatch.domain.schedule.entity.AlarmStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.Schedules;
import com.mapnote.mapnotebatch.domain.schedule.repository.SchedulesRepository;
import com.mapnote.mapnotebatch.batch.job.ScheduleBatchJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {ScheduleBatchJob.class, TestBatchConfig.class})
class SimpleJobTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Autowired
  private SchedulesRepository schedulesRepository;

  @BeforeEach
  public void insert(){
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
    schedulesRepository.save(new Schedules("asd", AlarmStatus.CRY));
  }

  @AfterEach
  public void tearDown() {
    schedulesRepository.deleteAllInBatch();
  }

  @Test
  public void 알람이_울렸지만_아직_처리되지_않은_스케쥴의_알림_상태를_울리지_않음으로_update() throws Exception {
    //given
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("Date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")))
        .toJobParameters();

    //when
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

    //then
    List<Schedules> schedulesList = schedulesRepository.findAllByAlarmStatus(AlarmStatus.NOT_CRY);
    assertThat(schedulesList.size()).isEqualTo(10);
  }
}