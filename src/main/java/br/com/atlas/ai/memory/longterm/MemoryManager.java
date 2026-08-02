package br.com.atlas.ai.memory.longterm;

import br.com.atlas.ai.memory.ConversationMemory;


public class MemoryManager {

    private final ConversationMemory conversationMemory;

    private final LongTermMemory longTermMemory;

    private final MemoryStorage storage;


    public MemoryManager() {

        this.conversationMemory =
                new ConversationMemory();

        this.longTermMemory =
                new LongTermMemory();

        this.storage =
                new MemoryStorage();


        try {

            storage.load(longTermMemory);
            System.out.println(
                    "===== MEMÓRIA CARREGADA ====="
            );

            longTermMemory.getAll()
                    .forEach((key, value) -> {

                        System.out.println(
                                key + " = "
                                        + value.getValue()
                        );

                    });

            System.out.println(
                    "============================="
            );
        } catch (Exception e) {

            System.out.println(
                    "Não foi possível carregar memória permanente."
            );

        }

    }


    // =========================
    // MEMÓRIA CURTA
    // =========================


    public void addUserMessage(String message){

        conversationMemory.addUserMessage(message);

    }


    public void addAssistantMessage(String message){

        conversationMemory.addAssistantMessage(message);

    }


    public ConversationMemory getConversationMemory(){

        return conversationMemory;

    }



    // =========================
    // MEMÓRIA LONGA
    // =========================


    public void remember(
            String key,
            String value,
            MemoryCategory category,
            int importance
    ){

        longTermMemory.save(
                key,
                value,
                category,
                importance
        );


        salvar();

    }



    public String recall(String key){

        return longTermMemory.get(key);

    }



    public LongTermMemory getLongTermMemory(){

        return longTermMemory;

    }



    private void salvar(){

        try {

            storage.save(
                    longTermMemory
            );

        } catch (Exception e){

            e.printStackTrace();

        }

    }

}