package com.feather.common.core.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlSeries implements Serializable {
    private static final long serialVersionUID = 1L;

    private String original;
    private String[] thumbnail;
}
