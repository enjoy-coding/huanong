package com.feather.website.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

/**
 * @author nothing
 * @date 2019-11-14 10:35
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ElementDto {
    private Element element;
    private String htmlPart;

}
