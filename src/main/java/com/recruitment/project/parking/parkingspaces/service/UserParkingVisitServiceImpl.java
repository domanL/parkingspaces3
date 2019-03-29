package com.recruitment.project.parking.parkingspaces.service;

import com.recruitment.project.parking.parkingspaces.domain.UserParkingVisit;
import com.recruitment.project.parking.parkingspaces.domain.UserVehicle;
import com.recruitment.project.parking.parkingspaces.repository.UserParkingVisitRepository;
import com.recruitment.project.parking.parkingspaces.repository.UserVehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.round;


@Service
public class UserParkingVisitServiceImpl implements UserParkingVisitService {

    private static final Logger logger = LoggerFactory.getLogger(UserParkingVisitServiceImpl.class);

    @Autowired
    private UserParkingVisitRepository userParkingVisitRepository;

    @Autowired
    private UserVehicleRepository userVehicleRepository;

    @Override
    public List<UserParkingVisit> getAllUserParkingVisit() {
        return userParkingVisitRepository.findAll();
    }

    @Override
    public void startUserParkingVisit(String registrationNumber, boolean isDisabled, String brand, String model) {
        UserVehicle userVehicle = userVehicleRepository.findByRegistration(registrationNumber);
        if (userVehicle == null) {
            userVehicle = new UserVehicle();
            userVehicle.setRegistration(registrationNumber);
            userVehicle.setBrand(brand);
            userVehicle.setModel(model);
            userVehicleRepository.save(userVehicle);
        }
        UserParkingVisit userParkingVisit = new UserParkingVisit();
        userParkingVisit.setUserVehicle(userVehicle);
        userParkingVisit.setDisabledUser(isDisabled);
        userParkingVisit.setStartParking(LocalDateTime.now());
        userParkingVisitRepository.save(userParkingVisit);
    }

    @Override
    public void endUserParkingVisit(String registrationNumber) {
        UserVehicle userVehicle = userVehicleRepository.findByRegistration(registrationNumber);
        if (userVehicle != null) {
            UserParkingVisit userParkingVisit = userParkingVisitRepository.findTopByUserVehicleOrderByStartParkingDesc(userVehicle);
            userParkingVisit.setFinishParking(LocalDateTime.now());
            Duration parkingVisitTime = Duration.between(userParkingVisit.getStartParking(), userParkingVisit.getFinishParking());
            userParkingVisit.setCostVisit(parkingCost(parkingVisitTime.toMinutes(), userParkingVisit.isDisabledUser()));
            userParkingVisitRepository.save(userParkingVisit);
            logger.info(userParkingVisit.getStartParking().toString());
        } else {
            logger.info("Vehicle with registration number: " + registrationNumber + "did not started park");
        }
    }

    @Override
    public UserParkingVisit isStartingUserParkingVisit(String registrationNumber) {
        UserVehicle userVehicle = userVehicleRepository.findByRegistration(registrationNumber);
        if (userVehicle != null) {
            UserParkingVisit userParkingVisit = userParkingVisitRepository.findTopByUserVehicleOrderByStartParkingDesc(userVehicle);
            return userParkingVisit;
        } else {
            logger.info("Vehicle with registration number: " + registrationNumber + " did not started park");
            return null;
        }

    }

    @Override
    public double getCostOneParkingVisit(String registrationNumber) {
        UserVehicle userVehicle = userVehicleRepository.findByRegistration(registrationNumber);
        if (userVehicle != null) {
            UserParkingVisit userParkingVisit = userParkingVisitRepository.findTopByUserVehicleOrderByFinishParkingDesc(userVehicle);
            return userParkingVisit.getCostVisit();
        } else {
            logger.info("Vehicle with registration number: " + registrationNumber + " did not started park");
            return -1;
        }
    }

    @Override
    public double getIncomeAllParkingVisit(LocalDate dayFrom) {
        List<UserParkingVisit> allParkingVisitFromDay = userParkingVisitRepository.findUserParkingVisitsByFinishParkingBetween(dayFrom.atStartOfDay(), dayFrom.atTime(23, 59, 59));
        double allIncomeFromDay = 0;
        for (UserParkingVisit userParkingVisit : allParkingVisitFromDay) {
            allIncomeFromDay += userParkingVisit.getCostVisit();
        }

        return round(allIncomeFromDay * 100.0) / 100.0;
    }

    double parkingCost(long parkingVisitTimeInMin, boolean isDisabled) {
        long parkingVisitTimeInHour = parkingVisitTimeInMin / 60;
        parkingVisitTimeInHour = parkingVisitTimeInHour * 60 < parkingVisitTimeInMin ? parkingVisitTimeInHour + 1 : parkingVisitTimeInHour;

        if (parkingVisitTimeInHour <= 1) {
            return isDisabled ? FIRST_HOUR_PARKING_PRICE_FOR_DISABLED : FIRST_HOUR_PARKING_PRICE_FOR_REGULAR;
        } else if (parkingVisitTimeInHour <= 2) {
            return (isDisabled ? FIRST_HOUR_PARKING_PRICE_FOR_DISABLED : FIRST_HOUR_PARKING_PRICE_FOR_REGULAR) + (isDisabled ? SECOND_HOUR_PARKING_PRICE_FOR_DISABLED : SECOND_HOUR_PARKING_PRICE_FOR_REGULAR);
        } else {
            double priceNextHour = isDisabled ? SECOND_HOUR_PARKING_PRICE_FOR_DISABLED : SECOND_HOUR_PARKING_PRICE_FOR_REGULAR;
            double allParkingCost = (isDisabled ? FIRST_HOUR_PARKING_PRICE_FOR_DISABLED : FIRST_HOUR_PARKING_PRICE_FOR_REGULAR) + (isDisabled ? SECOND_HOUR_PARKING_PRICE_FOR_DISABLED : SECOND_HOUR_PARKING_PRICE_FOR_REGULAR);
            for (int i = 3; i <= parkingVisitTimeInHour; i++) {
                priceNextHour = priceNextHour * (isDisabled ? THIRD_AND_NEXT_HOURS_PARKING_PRICE_FOR_DISABLED_MULTIPLIER : THIRD_AND_NEXT_HOURS_PARKING_PRICE_FOR_REGULAR_MULTIPLIER);
                allParkingCost += priceNextHour;
            }
            return round(allParkingCost * 100.0) / 100.0;
        }
    }


}
