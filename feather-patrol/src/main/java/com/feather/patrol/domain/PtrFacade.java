package com.feather.patrol.domain;

import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 巡检外观对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PtrFacade extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long facadeId;
    private Long entityId;
    private String facadeUrl;
}