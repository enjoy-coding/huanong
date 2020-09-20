package com.feather.wx.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-12-10 19:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Article {
    private String title;
    private String description;
    private String url;
    private String picUrl;
    private String thumbMediaId;
    private String author;
    private String contentSourceUrl;
    private String content;
    private String digest;
    private Integer showCoverPic;
}
