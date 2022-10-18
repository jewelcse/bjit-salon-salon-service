package com.bjit.salon.service.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SalonResponseDto {
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
