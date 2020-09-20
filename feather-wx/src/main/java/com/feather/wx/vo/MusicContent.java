package com.feather.wx.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-12-10 19:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MusicContent {
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;
    private String thumbMediaId;
}
