package com.recruitment.project.parking.parkingspaces.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "UserVehicle")
public class UserVehicle {

    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String model;
    private String registration;
    @OneToMany(mappedBy = "userVehicle")
    @JsonBackReference
    private List<UserParkingVisit> userParkingVisitList;

}

