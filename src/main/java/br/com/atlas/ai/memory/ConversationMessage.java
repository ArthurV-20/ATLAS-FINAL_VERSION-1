package br.com.atlas.ai.memory;

import java.time.LocalDateTime;

public class ConversationMessage {

    private final ConversationRole role;
    private final String content;
    private final LocalDateTime timestamp;


    public ConversationMessage(
            ConversationRole role,
            String content
    ) {

        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();

    }


    public ConversationRole getRole() {
        return role;
    }


    public String getContent() {
        return content;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}