package com.tinychating.common.user.domain.enums;

import lombok.Data;

/**
 * Description: ws的基本返回信息体
 */
@Data
public class WSBaseResp<T> {
    /**
     * ws推送给前端的消息
     *
     * @see WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
