package com.feather.meeting.data;

import com.deepoove.poi.data.DocxRenderData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-11-19 17:13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeetingInfoData {
    DocxRenderData segment;
}
