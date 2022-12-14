package com.bjit.salon.service.entity;


import lombok.*;

import javax.persistence.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "salons")
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;
    private String name;
    private String description;
    private String address;
    private long userId;
    private double reviews;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String contractNumber;
}
