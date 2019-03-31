package com.recruitment.project.parking.parkingspaces.service;

import com.recruitment.project.parking.parkingspaces.domain.UserParkingVisit;

import java.time.LocalDate;
import java.util.List;

public interface UserParkingVisitService {

    final static long FIRST_HOUR_PARKING_PRICE_FOR_REGULAR = 1;
    final static long SECOND_HOUR_PARKING_PRICE_FOR_REGULAR = 2;
    final static double THIRD_AND_NEXT_HOURS_PARKING_PRICE_FOR_REGULAR_MULTIPLIER = 1.5;

    final static long FIRST_HOUR_PARKING_PRICE_FOR_DISABLED = 0;
    final static long SECOND_HOUR_PARKING_PRICE_FOR_DISABLED = 2;
    final static double THIRD_AND_NEXT_HOURS_PARKING_PRICE_FOR_DISABLED_MULTIPLIER = 1.2;

    public List<UserParkingVisit> getAllUserParkingVisit();

    void startUserParkingVisit(String registrationNumber, boolean isDisabled, String brand, String model);

    void endUserParkingVisit(String registrationNumber);

    UserParkingVisit isStartingUserParkingVisit(String registrationNumber);

    double getCostOneParkingVisit(String registrationNumber);

    double getIncomeAllParkingVisit(LocalDate dayFrom);
}
