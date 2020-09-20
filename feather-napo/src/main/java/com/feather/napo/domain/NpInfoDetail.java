package com.feather.napo.domain;

import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2020-06-22 17:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpInfoDetail extends BaseEntity {

    /**
     * 主键
     */
    private Long infoDetailId;

    /**
     * 消息id
     **/
    private Long infoId;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 内容
     */
    private String content;

    /**
     * 序号
     */
    private Long orderNum;

    /**
     * 额外信息1
     */
    private String addInfo1;

    /**
     * 额外信息2
     */
    private String addInfo2;

    /**
     * 额外信息3
     */
    private String addInfo3;

    /**
     * 额外信息4
     */
    private String addInfo4;
}
