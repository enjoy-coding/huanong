package com.feather.website.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author nothing
 * @date 2019-11-14 10:01
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlockDto implements Serializable {
    private String blockVal;

    private String useHtml;
}
