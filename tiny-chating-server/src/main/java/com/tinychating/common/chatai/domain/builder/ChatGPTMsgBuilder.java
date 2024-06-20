package com.tinychating.common.chatai.domain.builder;

import com.tinychating.common.chatai.domain.ChatGPTMsg;
import com.tinychating.common.chatai.enums.ChatGPTRoleEnum;

public class ChatGPTMsgBuilder {
    public static ChatGPTMsg SYSTEM_PROMPT;

    static {
        ChatGPTMsg chatGPTMsg = new ChatGPTMsg();
        chatGPTMsg.setRole(ChatGPTRoleEnum.SYSTEM.getRole());
        chatGPTMsg.setContent("你的名字叫 AI,当有人问你问题时你只能回答500字以内");
        SYSTEM_PROMPT = chatGPTMsg;
    }

    public static ChatGPTMsg systemPrompt() {
        return SYSTEM_PROMPT;
    }

    public static ChatGPTMsg userMsg(String content) {
        ChatGPTMsg chatGPTMsg = new ChatGPTMsg();
        chatGPTMsg.setRole(ChatGPTRoleEnum.USER.getRole());
        chatGPTMsg.setContent(content);
        return chatGPTMsg;
    }

    public static ChatGPTMsg assistantMsg(String content) {
        ChatGPTMsg chatGPTMsg = new ChatGPTMsg();
        chatGPTMsg.setRole(ChatGPTRoleEnum.ASSISTANT.getRole());
        chatGPTMsg.setContent(content);
        return chatGPTMsg;
    }
}
