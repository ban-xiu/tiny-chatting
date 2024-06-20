package com.tinychating.common.chat.service.strategy.mark;

import com.tinychating.common.chat.domain.enums.MessageMarkTypeEnum;
import org.springframework.stereotype.Component;

/**
 * Description: 点踩标记策略类
 */
@Component
public class DisLikeStrategy extends AbstractMsgMarkStrategy {

    @Override
    protected MessageMarkTypeEnum getTypeEnum() {
        return MessageMarkTypeEnum.DISLIKE;
    }

    @Override
    public void doMark(Long uid, Long msgId) {
        super.doMark(uid, msgId);
        //同时取消点赞的动作
        MsgMarkFactory.getStrategyNoNull(MessageMarkTypeEnum.LIKE.getType()).unMark(uid, msgId);
    }

}
