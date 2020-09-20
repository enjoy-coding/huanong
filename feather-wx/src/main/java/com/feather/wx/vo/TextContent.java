package com.feather.wx.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-12-10 19:05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TextContent {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
