package com.feather.aupipes.utils;


import com.feather.aupipes.domain.query.Jsgxhl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tql
 * @description 给水阀门关系处理实体类
 * @date 2020年3月25日21:38:48
 */
public class JsValveToMap {

    public static Map<String,List<Jsgxhl>> valveListToMap(List<Jsgxhl> list){
        Map<String,List<Jsgxhl>> map = new HashMap<>();
        for(int i=0,j = list.size();i < j;i++){
            Jsgxhl hl = list.get(i);
            List<Jsgxhl> list1 = new ArrayList<>();
            list1 = map.get(hl.getSyfmid());
            if(list1 != null){
                list1.add(hl);
                map.put(hl.getSyfmid(),list1);
            }else{
                list1 = new ArrayList<>();
                list1.add(hl);
                map.put(hl.getSyfmid(),list1);
            }
        }
        return map;
    }

}
