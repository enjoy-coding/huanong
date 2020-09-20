package com.feather.lpscommunity.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 奖品对象 sc_prizes
 * 
 * @author fancy
 * @date 2019-11-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScPrizes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 奖品id */
    private Long prizesId;
    /** 奖品名称 */
    @Excel(name = "奖品名称")
    private String prizesName;
    /** 奖品数量 */
    @Excel(name = "奖品数量")
    private Long prizesNum;
    /** 奖品兑换分数 */
    @Excel(name = "奖品兑换分数")
    private Double prizesScore;

    private String prizesCover;

    private List<ScFileInfo> files;
}