package com.feather.napo.domain;

import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2020-06-29 9:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpDevice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long deviceId;

    private String name;

    private Integer type;

    private Integer state;

    private String address;

    /**权属单位**/
    private String ownerUnit;

    private String telephone;

    private String typeVal;

    private String stateVal;
}
