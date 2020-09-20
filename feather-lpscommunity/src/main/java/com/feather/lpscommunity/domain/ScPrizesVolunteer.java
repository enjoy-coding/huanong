package com.feather.lpscommunity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 志愿者领奖对象 sc_prizes_volunteer
 * 
 * @author fancy
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScPrizesVolunteer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 志愿者领奖 */
    private Long pvId;
    /** 志愿者id */
    @Excel(name = "志愿者id")
    private Long volunteerId;
    /** 奖品id */
    @Excel(name = "奖品id")
    private Long prizesId;

    private ScPrizes prizes;

    private ScVolunteer volunteer;

    public ScPrizesVolunteer(Long volunteerId, Long prizesId) {
        this.volunteerId = volunteerId;
        this.prizesId = prizesId;
    }

    
}