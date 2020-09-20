package com.feather.patrol.domain.forexmobi;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Facility {
    private String facility_id;
    private String facility_category;
    private String facility_category_name;
    private String facility_mileage;
    private String facility_line;
    private String facility_direction;

    private List<Standard> standard;
}
