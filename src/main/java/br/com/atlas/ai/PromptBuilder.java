package br.com.atlas.ai;

import br.com.atlas.ai.memory.ConversationMemory;
import br.com.atlas.ai.memory.ConversationMessage;
import br.com.atlas.ai.memory.longterm.MemoryEntry;

import java.util.List;
import java.util.Map;

public class PromptBuilder {


    public static String build(
            String systemPrompt,
            ConversationMemory memory,
            List<MemoryEntry> memories,
            String currentMessage
    ) {

        StringBuilder prompt = new StringBuilder();

        prompt.append(systemPrompt);


        if (!memories.isEmpty()) {

            prompt.append("\n\nMemórias:\n");

            for (MemoryEntry entry : memories) {

                prompt.append("- ")
                        .append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append("\n");
            }
        }


        List<ConversationMessage> messages =
                memory.getMessages();


        if (!messages.isEmpty()) {

            int start =
                    Math.max(0, messages.size() - 6);

            prompt.append("\n\nConversa recente:\n");


            for (int i = start; i < messages.size(); i++) {

                ConversationMessage msg =
                        messages.get(i);

                prompt.append(msg.getRole())
                        .append(": ")
                        .append(msg.getContent())
                        .append("\n");
            }
        }


        // SEMPRE adiciona a mensagem atual
        prompt.append("\nUsuário: ")
                .append(currentMessage)
                .append("\n");


        System.out.println(
                "[PROMPT TAMANHO] "
                        + prompt.length()
                        + " caracteres"
        );


        return prompt.toString();
    }
}