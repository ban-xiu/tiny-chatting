package com.tinychating.common.chat.service;

import com.tinychating.common.chat.domain.dto.MsgReadInfoDTO;
import com.tinychating.common.chat.domain.entity.Contact;
import com.tinychating.common.chat.domain.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会话列表 服务类
 * </p>
 */
public interface ContactService {
    /**
     * 创建会话
     */
    Contact createContact(Long uid, Long roomId);

    Integer getMsgReadCount(Message message);

    Integer getMsgUnReadCount(Message message);

    Map<Long, MsgReadInfoDTO> getMsgReadInfo(List<Message> messages);
}
