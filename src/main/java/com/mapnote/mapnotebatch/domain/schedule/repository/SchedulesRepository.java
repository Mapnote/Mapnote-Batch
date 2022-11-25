package com.mapnote.mapnotebatch.domain.schedule.repository;

import com.mapnote.mapnotebatch.domain.schedule.entity.AlarmStatus;
import com.mapnote.mapnotebatch.domain.schedule.entity.Schedules;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Long> {

  @Query("SELECT s FROM Schedules s WHERE s.alarmStatus = :alarmStatus")
  List<Schedules> findAllByAlarmStatus(@Param("alarmStatus") AlarmStatus alarmStatus);
}
