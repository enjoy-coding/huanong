package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 智能安防
 * 
 * @author 李十通
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupZnaf {
    /** 门禁 */
    private int mj;
    /** 红外线报警 */
    private int hwxbj;
    /** 烟感 */
    private int yg;
    /** 摄像头 */
    private int sxt;
    /** 小动物入侵 */
    private int xdwrq;
}
