package com.feather.wx.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-12-10 19:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Message {
    private String touser;
    private String msgtype;
    private TextContent text;
    private MediaContent image;
    private MediaContent voice;
    private MediaContent video;
    private MusicContent music;
    private Articles news;

}