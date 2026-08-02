package br.com.atlas.ai.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversationMemory {

    private static final int MAX_MESSAGES = 20;

    private final List<ConversationMessage> messages =
            new ArrayList<>();


    public void addUserMessage(String content) {

        messages.add(
                new ConversationMessage(
                        ConversationRole.USER,
                        content
                )
        );

        verificarLimite();
    }


    public void addAssistantMessage(String content) {

        messages.add(
                new ConversationMessage(
                        ConversationRole.ASSISTANT,
                        content
                )
        );

        verificarLimite();
    }


    private void verificarLimite() {

        while (messages.size() > MAX_MESSAGES) {

            messages.remove(0);

        }

    }


    public List<ConversationMessage> getMessages() {

        return Collections.unmodifiableList(messages);

    }


    public void clear() {

        messages.clear();

    }

}