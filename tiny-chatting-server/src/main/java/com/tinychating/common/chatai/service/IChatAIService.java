package com.tinychating.common.chatai.service;

import com.tinychating.common.chat.domain.entity.Message;

public interface IChatAIService {

    void chat(Message message);
}
