package com.recruitment.project.parking.parkingspaces.repository;

import com.recruitment.project.parking.parkingspaces.domain.UserParkingVisit;
import com.recruitment.project.parking.parkingspaces.domain.UserVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserParkingVisitRepository extends JpaRepository<UserParkingVisit, Long> {
    UserParkingVisit findTopByUserVehicleOrderByStartParkingDesc(UserVehicle userVehicle);
    UserParkingVisit findTopByUserVehicleOrderByFinishParkingDesc(UserVehicle userVehicle);
    List<UserParkingVisit> findUserParkingVisitsByFinishParking(LocalDate dayFrom);
    List<UserParkingVisit> findUserParkingVisitsByFinishParkingBetween (LocalDateTime dateStart, LocalDateTime dateEnd );

}
