package com.feather.hikvision.vo;

import lombok.Data;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-04 16:57
 */
@Data
public class FieldDetectionData {
    private String channelID;
    private String dataType;
    private String dateTime;
    private String eventDescription;
    private String eventType;
    private List<Fielddetection> fielddetection;
    private String ipAddress;
    private int portNo;
    private String recvTime;
    private String sendTime;
}
