/**
  * Copyright 2020 bejson.com 
  */
package com.feather.hikvision.vo;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-06-04 16:49:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Fielddetection {
    private String detectionTarget;
    private String duration;
    private String imageUrl;
    private String rate;
    private List<RegionCoordinatesList> regionCoordinatesList;
    private String sensitivityLevel;
    private TargetAttrs targetAttrs;
}