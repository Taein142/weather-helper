package com.icia.weatherhelper.dto;

import lombok.Data;

@Data
public class PlacesDTO {
    Long places_id;
    Long user_id;
    String places_name;
    String places_longitude;
    String places_latitude;
}
