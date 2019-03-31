package com.recruitment.project.parking.parkingspaces.service;

import com.recruitment.project.parking.parkingspaces.controller.UserVisitParkingController;
import com.recruitment.project.parking.parkingspaces.domain.UserParkingVisit;
import com.recruitment.project.parking.parkingspaces.domain.UserVehicle;
import com.recruitment.project.parking.parkingspaces.repository.UserParkingVisitRepository;
import com.recruitment.project.parking.parkingspaces.repository.UserVehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class UserVisitServiceControllerTest {

    @TestConfiguration
    static class UserParkingVisitServiceTestContextConfiguration {

        @Bean
        public UserParkingVisitService userParkingVisitService() {
            return new UserParkingVisitServiceImpl();
        }
    }

    @Autowired
    private UserParkingVisitService userVisitParkingService;

    @MockBean
    private UserParkingVisitRepository userParkingVisitRepository;
    @MockBean
    private UserVehicleRepository userVehicleRepository;

    @Before
    public void setUp() {
        UserVehicle userVehicle = new UserVehicle();
        userVehicle.setRegistration("WA TEST");

        UserParkingVisit userParkingVisit = new UserParkingVisit();
        userParkingVisit.setUserVehicle(userVehicle);
        userParkingVisit.setDisabledUser(false);
        userParkingVisit.setStartParking(LocalDateTime.of(2019, Month.MARCH, 31, 10, 10, 00));
        userParkingVisit.setFinishParking(LocalDateTime.of(2019, Month.MARCH, 31, 14, 10, 00));
        userParkingVisit.setCostVisit(1 + 2 + 2 * 1.5 + 2 * 1.5 * 1.5);

        Mockito.when(userVehicleRepository.findByRegistration(userVehicle.getRegistration()))
                .thenReturn(userVehicle);

        Mockito.when(userParkingVisitRepository.findTopByUserVehicleOrderByFinishParkingDesc(userParkingVisit.getUserVehicle()))
                .thenReturn(userParkingVisit);

        Mockito.when(userParkingVisitRepository.findTopByUserVehicleOrderByStartParkingDesc(userParkingVisit.getUserVehicle()))
                .thenReturn(userParkingVisit);

    }

    @Test
    public void whenFindTheLatestParkingVisitByRegistration_ThenReturnCost() {

        String registration = "WA TEST";

        double foundCostParkingVisit = userVisitParkingService.getCostOneParkingVisit(registration);

        double cost4Hours = 1 + 2 + 2 * 1.5 + 2 * 1.5 * 1.5;
        assertThat(foundCostParkingVisit)
                .isEqualTo(cost4Hours);


    }

    @Test
    public void whenFindTheLatestParkedVehicleByRegistration_ThenReturnParkingVisit() {

        String registration = "WA TEST";

        UserParkingVisit foundVehicleParkingVisit = userVisitParkingService.isStartingUserParkingVisit(registration);

        assertThat(foundVehicleParkingVisit.getStartParking())
                .isNotNull();

    }

}
