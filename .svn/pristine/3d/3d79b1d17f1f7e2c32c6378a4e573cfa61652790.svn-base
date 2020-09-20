package com.feather.napo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuli
 * @date 2020-07-27 9:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpThumbsUpLog implements Serializable {


    /**
     * 主键
     */
    private Long thumbsUpId;

    /**
     * 信息id
     */
    private Long infoId;

    /**
     * 点赞的ip
     */
    private String ip;

    /**
     * 日志记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;

}
