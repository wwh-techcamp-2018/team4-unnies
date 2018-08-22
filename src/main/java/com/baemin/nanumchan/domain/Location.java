package com.baemin.nanumchan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 50)
    private String address;

    @NotNull
    @Column(nullable = false, length = 50)
    private String addressDetail;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    public Location(String address, String addressDetail) {
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public Location(String address, String addressDetail, Double latitude, Double longitude) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
