package com.feather.cms.controller.frame;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;

/**
 * @author feather
 */
@RestController
@RequestMapping("/cms/frame/data")
public class CmsFrameDataController extends BaseController {

    @RequestMapping("/charts/{id}")
    public AjaxResult select(@PathVariable("id") String id) {

        return null;
    }
}
