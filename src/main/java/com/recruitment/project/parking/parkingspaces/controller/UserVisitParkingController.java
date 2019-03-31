package com.recruitment.project.parking.parkingspaces.controller;

import com.recruitment.project.parking.parkingspaces.domain.UserParkingVisit;
import com.recruitment.project.parking.parkingspaces.service.UserParkingVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/parking")
public class UserVisitParkingController {

    private UserParkingVisitService userParkingVisitService;

    @Autowired
    public UserVisitParkingController(UserParkingVisitService userParkingVisitService) {
        this.userParkingVisitService = userParkingVisitService;
    }

    @RequestMapping(value = "/start/{registration}/{isDisabled}", method = RequestMethod.GET)
    public ResponseEntity<?> startParkingVisit(@PathVariable("registration") String registrationNumber, @PathVariable("isDisabled") boolean isDisabled) {
        userParkingVisitService.startUserParkingVisit(registrationNumber, isDisabled,null, null);
        return ResponseEntity.ok().body("Started");
    }

    @RequestMapping(value = "/start/{registration}/{brand}/{model}/{isDisabled}", method = RequestMethod.GET)
    public ResponseEntity<?> startParkingVisit(@PathVariable("registration") String registrationNumber, @PathVariable("brand") String brand, @PathVariable("model") String model, @PathVariable("isDisabled") boolean isDisabled) {
        userParkingVisitService.startUserParkingVisit(registrationNumber, isDisabled, brand, model);
        return ResponseEntity.ok().body("Started");
    }

    @RequestMapping(value = "/stop/{registration}", method = RequestMethod.GET)
    public ResponseEntity<?> endParkingVisit(@PathVariable("registration") String registrationNumber) {
        userParkingVisitService.endUserParkingVisit(registrationNumber);
        return ResponseEntity.ok().body("Finished");
    }

    @RequestMapping(value = "/is-starting/{registration}", method = RequestMethod.GET)
    public ResponseEntity<?> isStartingParkingVisit(@PathVariable("registration") String registrationNumber) {
        UserParkingVisit userParkingVisit = userParkingVisitService.isStartingUserParkingVisit(registrationNumber);
        return ResponseEntity.ok().body(userParkingVisit);
    }

    @RequestMapping(value = "/costVisit/{registration}", method = RequestMethod.GET)
    public ResponseEntity<?> getCostAllParkingVisit(@PathVariable("registration") String registrationNumber) {
        double costAllParkingVisit = userParkingVisitService.getCostOneParkingVisit(registrationNumber);
        return ResponseEntity.ok().body(costAllParkingVisit);
    }

    @RequestMapping(value = "/incomeFromVisit/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getCostAllParkingVisit(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate dayFrom) {
        double incomeParkingVisit = userParkingVisitService.getIncomeAllParkingVisit(dayFrom);
        return ResponseEntity.ok().body(incomeParkingVisit);
    }

}
