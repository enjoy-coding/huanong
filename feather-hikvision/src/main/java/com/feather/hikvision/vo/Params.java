/**
  * Copyright 2020 bejson.com 
  */
package com.feather.hikvision.vo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2020-02-07 9:25:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Params {

    private Date sendTime;
    private String ability;
    private String uids;
    private List<Events> events;
}