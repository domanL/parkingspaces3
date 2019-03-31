package com.recruitment.project.parking.parkingspaces.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "UserParkingVisit")
public class UserParkingVisit {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startParking;
    private LocalDateTime finishParking;
    private double costVisit;
    private boolean disabledUser;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idUserVehicle")
    @JsonManagedReference
    private UserVehicle userVehicle;
}
