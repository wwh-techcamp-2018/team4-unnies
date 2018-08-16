package com.baemin.nanumchan.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDate;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: User owner

    @Column(nullable = false)
    private String name;

    // TODO: Location address

    @Column(nullable = false)
    private String title;

    @DecimalMin("0")
    private Long price;

    @Column(nullable = false)
    private String description;

    @DecimalMin("1")
    @DecimalMax("6")
    private Integer maxParticipant;

    @DateTimeFormat
    private LocalDate expireDateTime;

    @DateTimeFormat
    private LocalDate shareDateTime;

    @Column(nullable = false)
    private boolean isBowlNeeded;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
}
