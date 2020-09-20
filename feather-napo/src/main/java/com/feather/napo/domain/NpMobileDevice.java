package com.feather.napo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liuli
 * @date 2020-07-29 9:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpMobileDevice {
    /**
     * 主键，设备的唯一编码，不可重复
     */
    private String deviceId;

    /**
     * 是否为该设备发送未读提醒 默认为0 ，代表都发送
     */
    private Integer sendFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
