package com.feather.napo.domain;

import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuli
 * @date 2020-07-29 10:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpInfoReadLog extends BaseEntity {
    /**
     * 主键
     */
    private Long readId;

    /**
     * 消息id
     */
    private Long infoId;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 已读标志 0为为默认值 代表未读
     */
    private Integer readFlag;
}
