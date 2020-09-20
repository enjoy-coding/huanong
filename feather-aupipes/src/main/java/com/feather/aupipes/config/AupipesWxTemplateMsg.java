package com.feather.aupipes.config;

import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;

import com.feather.prd.domain.PrdMsg;
import com.feather.system.domain.SysUser;

public interface AupipesWxTemplateMsg {
    String getTemplateType();

    String getTemplateId();

    TemplateMessageSendResult getTemplateMessage(PrdMsg prdMsg, SysUser user);
}
