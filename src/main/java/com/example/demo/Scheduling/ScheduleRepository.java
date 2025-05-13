package com.example.demo.Scheduling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDoctorId(Long doctorId);
    List<Schedule> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    Schedule findById(long id);
    List<Schedule> findByAptType(String aptType);
    List<Schedule> findByDate(LocalDate date);
    List<Schedule> findByDateAndStartTime(LocalDate date, LocalTime time);
    List<Schedule> findByAptTypeAndDate(String aptType, LocalDate date);
}
