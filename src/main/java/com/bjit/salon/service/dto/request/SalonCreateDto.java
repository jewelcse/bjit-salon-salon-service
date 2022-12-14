package com.bjit.salon.service.dto.request;


import lombok.*;

import java.sql.Timestamp;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SalonCreateDto {
    private String name;
    private String description;
    private String address;
    private long userId;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String contractNumber;
}
